package net.scrooby.SOA;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;

@Stateless
@LocalBean
public class AuctionEndTimer {
    
    @EJB 
    ListingSession listingSession;
    @EJB
    PaymentSession paymentSession;
    @EJB
    UserSession userSession;
    @EJB
    RoleSession roleSession;
    
    // This method is called on the first second of every minute
    @Schedule(hour = "*", minute = "*", second = "1")
    public void checkListingsEnded(){
        
        LocalDateTime now;
        now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        
        List<Listing> listings = listingSession.getAllActiveListings();
        
        if (listings == null) {
            return;
        }
        
        Iterator<Listing> it = listings.iterator();
        while (it.hasNext()) {
            Listing listing = it.next();
            LocalDateTime endTime = listing.getEndTime();
            if (endTime.equals(now) || endTime.isBefore(now)) {
                listing.setActive(Boolean.FALSE);
                listingSession.update(listing);
                System.out.println("Listing " + listing.getId() + " has Ended.");
                createPayment(listing);
            }
        }
    }
    
    @Schedule(hour = "*", minute = "*", second = "2")
    public void checkPayments() {
        
        LocalDateTime now;
        now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        
        List<Payment> payments = paymentSession.findAllUnpaidPayments();
        
        if (payments.isEmpty()) {
            return;
        }
        
        Iterator<Payment> it = payments.iterator();
        while(it.hasNext()) {
            Payment payment = it.next();
            LocalDateTime due = payment.getDue();
            if (due.equals(now) || due.isBefore(now)) {
                User user = userSession.find(payment.getUser().getId());
                if (user.getStrike() == 2) {
                    List<Role> roles = user.getRoles();
                    roles.add((Role) roleSession.findByName("Deactivated"));
                    user.setRoles(roles);
                    
                    userSession.update(user);
                }
            }
        }
    }
    
    public void createPayment(Listing listing){
        if (listing.getOwner().equals(listing.getHighestBidder())) {
            return;
        }
        
        Payment payment = new Payment();
        
        payment.setAmount(listing.getHighestBid());
        payment.setUser(listing.getHighestBidder());
        LocalDateTime due = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        due = due.plusDays(1);
        payment.setDue(due);
        payment.setPaid(Boolean.FALSE);
        
        paymentSession.create(payment);
    }
}

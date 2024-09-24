package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.util.List;

@Stateless
@LocalBean
public class ListingSession extends AbstractSession {
    
    @Override
    public Listing find(int id){
        return em.find(Listing.class, id);
    }
    
    public void delete(Listing listing) {
        Listing listingToDelete = em.getReference(
                Listing.class, listing.getId());
        em.remove(listingToDelete);
    }
    
    public List<Listing> getAllListings() {
        return em.createQuery("SELECT l FROM Listing l", Listing.class)
                .getResultList();
    }
    
    public Listing getSoonestListing() {
        List<Listing> listings = em.createQuery("SELECT l FROM Listing l WHERE l.active = true ORDER BY l.endTime")
                .getResultList();
        
        if (listings.isEmpty()) {
            Listing listing = new Listing();
            return listing;
        }
        
        return listings.get(0);
    }
    
    public List<Listing> getAllActiveListings() {
        return em.createQuery("SELECT l FROM Listing l WHERE l.active = true").getResultList();
    }
}

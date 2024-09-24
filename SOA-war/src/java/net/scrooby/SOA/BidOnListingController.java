package net.scrooby.SOA;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@WebServlet(name = "BidOnListingController", urlPatterns = {"/Bid"})
public class BidOnListingController extends HttpServlet {

    @Inject
    ListingSession listingSession;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Listing listing = listingSession.find(Integer.parseInt(
                request.getParameter("listingId")));
        
        request.setAttribute("listing", listing);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/bidOnListingjsp.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BigDecimal bid = new BigDecimal(request.getParameter("bidAmount"));
        
        Listing listing = listingSession.find(Integer.parseInt(
                request.getParameter("listingId")));
        
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null) {
            request.setAttribute("message", "You must login before bidding on an auction");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loginjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        if (user.isDeactivated() || user.isUnauthorised()) {
            request.setAttribute("message", "You must be authorised before bidding on a listing");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/viewListingjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        int decimalsCompared = listing.getHighestBid().compareTo(bid);
        
        // If the bid is smaller than the current highest bid on the listing
        if (decimalsCompared == 0 || decimalsCompared == 1) {
            request.setAttribute("message", "You must bid more than the current highest bid.");
            request.setAttribute("listing", listing);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/bidOnListingjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Set the bid as the new highest bid
        listing.setHighestBid(bid);
        listing.setHighestBidder(user);
        
        // Update the listing in the database
        listingSession.update(listing);
        
        request.setAttribute("listing", listing);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ViewListing");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

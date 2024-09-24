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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@WebServlet(name = "CreateListingController", urlPatterns = {"/CreateListing"})
public class CreateListingController extends HttpServlet {
    
    @Inject
    UserSession userSession;
    @Inject
    ListingSession listingSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null) {
            request.setAttribute("message", "Please login to create a new listing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loginjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        if (user.isUnauthorised()) {
            request.setAttribute("message", "Please request permisson to make a listing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboardjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/createListingjsp.jsp");
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
        if (request.getSession().getAttribute("user") == null) {
            processRequest(request, response);
        }
        
        User user = (User) request.getSession().getAttribute("user");
        
        System.out.println("Atempting to create listing...");
        
        Listing listing = new Listing();
        
        // Listing object populated from the request parameters
        listing.setTitle(request.getParameter("title"));
        listing.setCondition(request.getParameter("condition"));
        listing.setDescription(request.getParameter("description"));
        BigDecimal startPrice = new BigDecimal((String) request.getParameter("startPrice"));
        listing.setHighestBid(startPrice);
        
        // User end time 
        String duration = request.getParameter("duration");
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        duration += " " + now;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(duration, formatter);
        
        listing.setEndTime(dateTime);
        listing.setActive(Boolean.TRUE);
        listing.setOwner(user);
        listing.setHighestBidder(user);
        
        user.addListing(listing);
        
        userSession.update(user);
        
        System.out.println("Listing created!");
        
        request.setAttribute("listing", listingSession.getSoonestListing());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/indexjsp.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

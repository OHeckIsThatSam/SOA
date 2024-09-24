package net.scrooby.SOA;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@WebServlet(name = "ViewListingController", urlPatterns = {"/ViewListing"})
public class ViewListingController extends HttpServlet {
    
    @Inject
    ListingSession listingSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the listing with the id given by the request
        Listing listing = listingSession.find(Integer.parseInt(
                request.getParameter("listingId")));
        
        if (listing == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/viewListingjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Find the date time between now and the end of the listing
        Duration countdown = Duration.between(LocalDateTime.now(), listing.getEndTime());
        
        // Deconstruct the day, hour, minutes and seconds and pass them into the reuest
        long days = countdown.toDays();
        countdown = countdown.minusDays(days);
        request.setAttribute("days", days);
        
        long hours = countdown.toHours();
        countdown = countdown.minusHours(hours);
        request.setAttribute("hours", hours);
        
        long minutes = countdown.toMinutes();
        countdown = countdown.minusMinutes(minutes);
        request.setAttribute("minutes", minutes);
        
        long seconds = countdown.getSeconds();
        request.setAttribute("seconds", seconds);
        
        // Pass the listing into the request so it can be accsessed by the jsp
        request.setAttribute("listing", listing);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/viewListingjsp.jsp");
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
        
        Listing listing = listingSession.find(Integer.parseInt(request.getParameter("listingId")));
        
        // Ends the listing in one minute
        if (request.getParameter("end") != null) {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            now = now.plusMinutes(1);
            now = now.plusSeconds(1);
            listing.setEndTime(now);
            
            listingSession.update(listing);
        }
        
        // Deletes a the listing
        if (request.getParameter("remove") != null) {
            listingSession.delete(listing);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/indexjsp.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

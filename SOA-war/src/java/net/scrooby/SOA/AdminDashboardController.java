package net.scrooby.SOA;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import net.scrooby.util.Mail;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/AdminDashboard"})
public class AdminDashboardController extends HttpServlet {
    
    @Inject
    AuthenticationRequestSession authenticationSession;
    @Inject
    ReactivationRequestSession reactivationSession;
    @Inject
    UserSession userSession;
    @Inject
    RoleSession roleSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getSession().getAttribute("user") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loginjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // If the user is not an admin take them to the home page insted
        User user = (User) request.getSession().getAttribute("user");
        if (!(user.isAdmin())) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/indexjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Gets lists of all requests, passing them into request to be used in jsp
        request.setAttribute("authenticationRequests", authenticationSession.getAllRequests());
        request.setAttribute("reactivationRequests", reactivationSession.getAllRequests());
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminDashboardjsp.jsp");
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
        
        // Admin approves an authentication request
        if (request.getParameter("authId") != null) {
            int id = Integer.parseInt(request.getParameter("authId"));
            
            // Get the authentication request from the database with the id
            AuthenticationRequest authRequest;
            authRequest = (AuthenticationRequest) authenticationSession.find(id);
            
            if ("deny".equals(request.getParameter("form"))) {
                authenticationSession.delete(authRequest);
                
                processRequest(request, response);
                return;
            }
            
            // Get the user associated with that request
            User user = authRequest.getUser();
            
            //<editor-fold defaultstate="collapsed" desc="Creates mailer. Hidden to protect my email password">
            Mail mailer = new Mail("smtp.office365.com", "587", "sam@scrooby.net", "ironRive8#");// </editor-fold>
            
            String message = "http://localhost:8080/SOA-war/VerifyUser?userId="
                    + user.getId();
            
            mailer.send("sam@scrooby.net", user.getEmail(), "Account Verification", message);
            request.setAttribute("message", "Authentication approved, email sent.");
        }
        
        // Admin approves a reactivation request
        if (request.getParameter("reacId") != null) {
            int id = Integer.parseInt(request.getParameter("reacId"));
            
            ReactivationRequest reacRequest;
            reacRequest = (ReactivationRequest) reactivationSession.find(id);
            
            if ("deny".equals(request.getParameter("form"))) {
                reactivationSession.delete(reacRequest);
                
                processRequest(request, response);
                return;
            }
            
            User user = reacRequest.getUser();
            
            List<Role> roles = user.getRoles();
            roles.remove(roleSession.findByName("Deactivated").get(0));
            
            userSession.update(user);
            
            reactivationSession.delete(reacRequest);
        }
        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

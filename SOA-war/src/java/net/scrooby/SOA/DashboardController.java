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

@WebServlet(name = "UserDashboardController", urlPatterns = {"/Dashboard"})
public class DashboardController extends HttpServlet {
    
    @Inject
    UserSession userSession;
    @Inject
    ReactivationRequestSession reactivationSession;
    @Inject
    AuthenticationRequestSession authenticationSession;
    @Inject
    PaymentSession paymentSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("user");
        // If the user hasn't logged in then redirect them to the login page
        if (request.getSession().getAttribute("user") == null) {
            request.setAttribute("message", "Please login to access your dashboard.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loginjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        List<Payment> payments = paymentSession.findUsersUnpaidPayments(user);
        
        request.setAttribute("payments", payments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboardjsp.jsp");
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
        
        User user = (User) request.getSession().getAttribute("user");
        
        // User request to be authenticated
        if (request.getParameter("authentication") != null) {
            System.out.println("Authentication request...");
            
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            
            // Check if they have already sent a request
            if (authenticationSession.reactivationRequestExisits(user)) {
                System.out.println("Authentication request already exisits!");
                request.setAttribute("message", "You've already requested authentication.");
                processRequest(request, response);
                return;
            }
            
            authenticationRequest.setUser(user);
            user.setAuthenticationRequest(authenticationRequest);
            
            userSession.update(user);
            
            System.out.println("Authentication request sent!");
            request.setAttribute("message", "Authentication request sent!");
        }
        
        // User request to be reactiveated
        if (request.getParameter("reactivation") != null) {
            System.out.println("Reactivation request...");
            
            ReactivationRequest reactiationRequest = new ReactivationRequest();
            
            if (reactivationSession.reactivationRequestExisits(user)) {
                System.out.println("Reactivation request already exisits!");
                request.setAttribute("message", "You've already requested reactivation.");
                processRequest(request, response);
                return;
            }
            
            reactiationRequest.setUser(user);
            user.setReactivationRequest(reactiationRequest);
            
            userSession.update(user);
            
            System.out.println("Reactivation request sent!");
            request.setAttribute("message", "Reactivation request sent!");
        }
        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

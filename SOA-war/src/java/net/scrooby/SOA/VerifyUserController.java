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

@WebServlet(name = "VerifyAccountController", urlPatterns = {"/VerifyUser"})
public class VerifyUserController extends HttpServlet {
    
    @Inject
    UserSession userSession;
    @Inject
    RoleSession roleSession;
    @Inject
    AuthenticationRequestSession authenticationSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the user from the id in the request
        User user = userSession.find(Integer.parseInt(request.getParameter("userId")));
        
        // Return and inform the user that the user can't be authorised
        if (user == null || !user.isUnauthorised()) {
            request.setAttribute("message", "User doesn't exist or is already authorised.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/verifyUserjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Get the authenticationRequest asosiated with the user
        AuthenticationRequest authRequest = user.getAuthenticationRequest();
        
        // Change the roles of the user
        List<Role> roles = user.getRoles();
        roles.remove(roleSession.findByName("Unauthorised").get(0));
        roles.add(roleSession.findByName("Authorised").get(0));
        
        // Update the user in the database
        userSession.update(user);
        
        // Delete the now approved request from the database
        authenticationSession.delete(authRequest);
        
        request.setAttribute("message", "You are now authorised. Get started by logging in.");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/verifyUserjsp.jsp");
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

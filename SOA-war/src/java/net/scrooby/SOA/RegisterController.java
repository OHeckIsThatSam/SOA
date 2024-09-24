package net.scrooby.SOA;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = {"/Register"})
public class RegisterController extends HttpServlet {

    @Inject
    LoginDetailsSession loginSession;
    @Inject
    UserSession userSession;
    @Inject
    RoleSession roleSession;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/registerjsp.jsp");
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
        
        User user = new User();
        LoginDetails loginDetails = new LoginDetails();
        
        if (loginSession.usernameExists(request.getParameter("username"))) {
            request.setAttribute("message", "Username already exists.");
            processRequest(request, response);
            return;
        }
        
        loginDetails.setUsername(request.getParameter("username"));
        loginDetails.setPassword(request.getParameter("password"));
        
        if (userSession.emailExists(request.getParameter("email"))) {
            request.setAttribute("message", "Email is already in use.");
            processRequest(request, response);
            return;
        }
        
        user.setEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setAddress(request.getParameter("address"));
        user.setStrike(0);
        user.setLoginDetails(loginDetails);
        user.setRoles(roleSession.findByName("Unauthorised"));
        
        System.out.println("Ready to create user");
        
        userSession.create(user);
        
        System.out.println("User created");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

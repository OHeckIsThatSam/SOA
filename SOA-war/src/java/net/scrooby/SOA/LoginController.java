package net.scrooby.SOA;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/Login"})
public class LoginController extends HttpServlet {
    
    @Inject
    LoginDetailsSession loginDetailsSession;
    @Inject
    UserSession userSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/indexjsp.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginjsp.jsp");
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
        
        System.out.println("Atempting login...");
        
        // Creates a loginDetails object and assigns its variables from the request
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(request.getParameter("username"));
        loginDetails.setPassword(request.getParameter("password"));
        
        /* If the loginDetails from the request exist in the database then the
        then it is retrived and id used to get assosiated user.*/
        if (loginDetailsSession.loginDetailsCorrect(loginDetails)) {
            loginDetails = loginDetailsSession.getLoginDetailsByUsername(loginDetails.getUsername());
            User user = userSession.getByLoginDetails(loginDetails);
            
            System.out.println("Login sucsessfull! First name: " + user.getFirstName());
            
            // User object passed into the session (esstially loging the user in)
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            // User is forwaded back to Home page
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Home");
            dispatcher.forward(request, response);
            return;
        }
            
        System.out.println("Login details incorrect");
        request.setAttribute("message", "Username or Password is incorrect.");
        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

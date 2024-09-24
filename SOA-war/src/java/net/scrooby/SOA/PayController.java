package net.scrooby.SOA;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PayController", urlPatterns = {"/Pay"})
public class PayController extends HttpServlet {
    
    @Inject
    PaymentSession paymentSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Payment payment = (Payment) paymentSession.find(
                Integer.parseInt(request.getParameter("paymentId")));
        
        request.setAttribute("payment", payment);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/payPaymentjsp.jsp");
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
        Payment payment = (Payment) paymentSession.find(
                Integer.parseInt(request.getParameter("paymentId")));
        
        payment.setPaid(Boolean.TRUE);
        
        paymentSession.update(payment);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Dashboard");
        dispatcher.forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

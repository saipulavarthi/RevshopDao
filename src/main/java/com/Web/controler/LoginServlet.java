package com.Web.controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.example.DAOO.BuyerDAOImpl;
import com.example.Entity.Buyer;

@WebServlet("/buyerLogin")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.println("<html><body><p>Email and password must not be empty.</p></body></html>");
            return;
        }

        try {
            BuyerDAOImpl buyerDAO = new BuyerDAOImpl();
            Buyer buyer = buyerDAO.loginBuyer(email, password); // This should return the buyer object

            // Debugging output
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("Buyer Object: " + buyer);

            if (buyer != null) { // Check if the buyer is not null
                // Assuming your Buyer class has a method getPassword()
                if (buyer.getPassword().equals(password)) {
                    HttpSession session = request.getSession(true);
                   
                    session.setAttribute("buyerId", buyer.getBuyerId()); // Store the buyerId in the session
                    session.setAttribute("buyer", buyer); // Store the Buyer object in the session
                    // Redirect to the welcome page upon successful login
                    response.sendRedirect("welcome.html");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><p>Invalid email or password. Please try again.</p></body></html>");
                    out.println("<html><body><p>New user: <a href=\"BuyerRegistration.html\">Register</a></p></body></html>");
                }
            } else {
                PrintWriter out = response.getWriter();
                out.println("<html><body><p>Invalid email or password. Please try again.</p></body></html>");
                out.println("<html><body><p>New user: <a href=\"BuyerRegistration.html\">Register</a></p></body></html>");
            }

            buyerDAO.closeConnection(); // Ensure connection is closed
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A server error occurred while processing your request.");
        }
    }
}

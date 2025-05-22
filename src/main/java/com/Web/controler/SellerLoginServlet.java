package com.Web.controler;

import java.io.IOException;
import java.sql.SQLException;

import com.example.DAOO.SellerDAOImpl;
import com.example.Entity.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sellerLogin")
public class SellerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        SellerDAOImpl sellerDAO = new SellerDAOImpl();
        try {
            Seller seller = sellerDAO.loginSeller(email, password); // Assuming this method retrieves the seller based on email and password

            if (seller != null) {
                if (seller.getPassword().equals(password)) {
                    // Store seller ID in the session
                    HttpSession session = request.getSession();
                    session.setAttribute("sellerId", seller.getSellerId());

                    // Redirect to ProductServlet to retrieve seller's products and display the welcome page
                    response.sendRedirect("SellerWelcome.jsp");
                } else {
                    displayErrorMessage(response, "Invalid email or password. Please try again.");
                }
            } else {
                displayErrorMessage(response, "Invalid email or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A server error occurred while processing your request.");
        } finally {
            try {
                sellerDAO.closeConnection(); // Ensure the connection is closed
            } catch (SQLException e) {
                e.printStackTrace(); // Log the exception while closing the connection
            }
        }
    }

    private void displayErrorMessage(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<html><body><p>" + message + "</p>");
        response.getWriter().println("<p>New user: <a href=\"SellerRegistration.html\">Register</a></p></body></html>");
    }
}

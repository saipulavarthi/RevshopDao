package com.Web.controler;

import java.io.IOException;
import java.sql.SQLException;

import com.example.DAOO.WishListDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/removeFromWishList")
public class RemoveFromWishListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int buyerId = (int) session.getAttribute("buyerId"); // Retrieve buyerId from session
        int productId = Integer.parseInt(request.getParameter("productId"));

        WishListDAO wishListDAO = new WishListDAO();
        try {
            boolean removed = wishListDAO.removeProductFromWishList(buyerId, productId);
            if (removed) {
                // Optionally, you can set a success message in the session
                session.setAttribute("message", "Product removed from wishlist successfully.");
            } else {
                session.setAttribute("error", "Failed to remove product from wishlist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred while removing the product.");
        }

        // Redirect back to the wishlist page
        response.sendRedirect("wishlist.jsp");
    }
}
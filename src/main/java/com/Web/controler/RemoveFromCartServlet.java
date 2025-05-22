package com.Web.controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.example.DAOO.CartItemDAOImpl;
import com.example.Entity.CartItem;
import com.example.Entity.Buyer;

/**
 * Servlet implementation class RemoveFromCart
 */
@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");

        // Validate and parse the productId
        if (productIdStr == null || productIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Product ID is missing.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID format.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        Buyer buyer = (Buyer) session.getAttribute("buyer");

        if (cart != null && buyer != null) {
            // Remove item from session cart
            cart.removeIf(item -> item.getProduct().getProductId() == productId);
            
            // Remove item from database
            CartItemDAOImpl cartItemDAO = new CartItemDAOImpl();
            cartItemDAO.removeCartItem(productId, buyer.getBuyerId());  // Remove from DB using buyer ID and product ID
        }

        // Redirect to the cart page after removal
        response.sendRedirect("cart.jsp");
    }
}

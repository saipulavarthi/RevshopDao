package com.Web.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.DAOO.CartItemDAOImpl;
import com.example.DAOO.ProductDAOImpl;
import com.example.Entity.Buyer;
import com.example.Entity.CartItem;
import com.example.Entity.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve product ID and quantity
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");
        
        System.out.println("Product ID received: " + productIdStr);
        System.out.println("Quantity received: " + quantityStr);

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid quantity format.");
            request.getRequestDispatcher("men.jsp").forward(request, response);
            return;
        }

        if (productIdStr == null || productIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Product ID is missing.");
            request.getRequestDispatcher("men.jsp").forward(request, response);
            return;
        }

        ProductDAOImpl productDAO = new ProductDAOImpl();
        Product product;
        
        try {
            product = productDAO.getProductById(Integer.parseInt(productIdStr));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID format.");
            request.getRequestDispatcher("men.jsp").forward(request, response);
            return;
        }

        if (product == null) {
            request.setAttribute("errorMessage", "Product not found.");
            request.getRequestDispatcher("men.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        Buyer buyer = (Buyer) session.getAttribute("buyer");

        if (buyer == null) {
            response.sendRedirect("BuyerLogin.html");
            return;
        }

        int buyerId = buyer.getBuyerId();

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                if (item.getQuantity() + quantity > product.getQuantity()) {
                    request.setAttribute("errorMessage", "Insufficient stock available.");
                    request.getRequestDispatcher("men.jsp").forward(request, response);
                    return;
                }
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        // If not found, add the product to the cart
        if (!found) {
            if (quantity > product.getQuantity()) {
                request.setAttribute("errorMessage", "Insufficient stock available.");
                request.getRequestDispatcher("men.jsp").forward(request, response);
                return;
            }
            CartItem cartItem = new CartItem(product, quantity, buyerId);
            cart.add(cartItem);

            // Add new item to the database
            CartItemDAOImpl cartItemDAO = new CartItemDAOImpl();
            cartItemDAO.addCartItem(cartItem);  // Save the cart item to the database
        } else {
            // Update existing item in the database
            CartItemDAOImpl cartItemDAO = new CartItemDAOImpl();
            cartItemDAO.updateCartItemQuantity(product.getProductId(), buyerId, quantity);
        }

        session.setAttribute("cart", cart);

        response.sendRedirect("cart.jsp");
    }
}

package com.Web.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.dbconnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/buyNow")
public class BuyNowServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve product ID from request
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        // Retrieve buyer ID from session
        Integer buyerId = (Integer) request.getSession().getAttribute("buyerId");
        
        // Check if buyerId is null
        if (buyerId == null) {
            // Redirect to login or show error message
            request.setAttribute("errorMessage", "You need to log in to make a purchase.");
            request.getRequestDispatcher("BuyerLogin.html").forward(request, response); // Redirect to login page
            return; // Exit the method
        }

        // Logic to handle buying the product (e.g., updating inventory, creating an order)
        try (Connection connection = dbconnection.getConnection()) {
            // Check available quantity for the product
            String checkQuantitySql = "SELECT quantity FROM products WHERE product_id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuantitySql)) {
                checkStatement.setInt(1, productId);
                ResultSet resultSet = checkStatement.executeQuery();
                
                if (resultSet.next()) {
                    int availableQuantity = resultSet.getInt("quantity");
                    
                    if (availableQuantity <= 0) {
                        // Product is out of stock
                        request.setAttribute("errorMessage", "Sorry, this product is currently out of stock.");
                        request.getRequestDispatcher("men.jsp").forward(request, response); // Redirect back to the product page
                        return; // Exit the method
                    } else {
                        // Place the order
                        String sql = "INSERT INTO orders (buyer_id, product_id, quantity, status) VALUES (?, ?, 1, 'Processing')";
                        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                            statement.setInt(1, buyerId);
                            statement.setInt(2, productId);
                            statement.executeUpdate();
                        }
                        response.getWriter().println("Order placed successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to place order.");
        }
    }
}

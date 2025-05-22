package com.example.DAOO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Entity.OrderItem;

import DBConnection.dbconnection;

public class OrderItemDAO {
    private Connection connection;

    public OrderItemDAO() {
        this.connection = dbconnection.getConnection();
    }

    // Method to add an order item
    public void addOrderItem(OrderItem orderItem) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getPrice());
            stmt.executeUpdate();
        }
    }

    // Method to retrieve all order items for a specific order
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int orderItemId = rs.getInt("order_item_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                orderItems.add(new OrderItem(orderItemId, orderId, productId, quantity, price));
            }
        }
        return orderItems;
    }

    // Optional: Method to delete an order item by ID
    public boolean deleteOrderItem(int orderItemId) throws SQLException {
        String query = "DELETE FROM order_items WHERE order_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderItemId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Returns true if a row was deleted
        }
    }
}
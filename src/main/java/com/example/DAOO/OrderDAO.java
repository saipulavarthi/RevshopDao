package com.example.DAOO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Entity.Order;
import com.example.Entity.OrderItem;
import DBConnection.dbconnection;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() {
        this.connection = dbconnection.getConnection();
    }

    // Method to create a new order
    public int createOrder(Order order) throws SQLException {
        // Corrected SQL: remove duplicate 'status' column and fix placeholders
    	String orderQuery = "INSERT INTO orders (buyer_id, shipping_address, billing_address, status, contact_number, total_amount, payment_method) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            orderStmt.setInt(1, order.getBuyerId());
            orderStmt.setString(2, order.getShippingAddress());
            orderStmt.setString(3, order.getBillingAddress());
            orderStmt.setString(4, order.getStatus());              // e.g. "pending"
            orderStmt.setString(5, order.getContactNumber());       // phone number as String
            orderStmt.setDouble(6, order.getTotalAmount());
            orderStmt.setString(7, order.getPaymentMethod());       // e.g. "credit card"
                 // e.g. "unpaid", "paid"

            int affectedRows = orderStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    // Method to add order items to an order
    public void addOrderItems(List<OrderItem> orderItems) throws SQLException {
        String itemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
            for (OrderItem item : orderItems) {
                itemStmt.setInt(1, item.getOrderId());
                itemStmt.setInt(2, item.getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getPrice());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();
        }
    }

    // Method to get orders for a specific buyer
    public List<Order> getOrdersByBuyerId(int buyerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE buyer_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buyerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();

                order.setOrderId(rs.getInt("order_id"));
                order.setBuyerId(rs.getInt("buyer_id"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setBillingAddress(rs.getString("billing_address"));
                order.setContactNumber(rs.getString("contact_number"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setCreatedAt(rs.getTimestamp("created_at"));

                orders.add(order);
            }
        }

        return orders;
    }

    // Method to get order items by order ID
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

    // Optional: delete order
    public boolean deleteOrder(int orderId) throws SQLException {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}

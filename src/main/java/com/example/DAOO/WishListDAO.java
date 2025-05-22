package com.example.DAOO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Entity.WishListItem;

import DBConnection.dbconnection;

public class WishListDAO {
    private Connection connection;

    public WishListDAO() {
        this.connection = dbconnection.getConnection();
    }

    // Method to add a product to the wish list
    public boolean addToWishList(WishListItem item) throws SQLException {
        // Check if the item is already in the wish list
        String checkQuery = "SELECT * FROM wishlist WHERE product_id = ? AND buyer_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, item.getProductId());
            checkStmt.setInt(2, item.getBuyerId());

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false; // Product already exists in wish list
            }
        }

        // If not present, add the product to the wish list
        String query = "INSERT INTO wishlist (product_id, buyer_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, item.getProductId());
            stmt.setInt(2, item.getBuyerId());
            stmt.executeUpdate();
        }
        return true; // Successfully added
    }

    // Method to get all products from the wish list for a specific buyer
    public List<WishListItem> getWishListForBuyer(int buyerId) throws SQLException {
        List<WishListItem> wishList = new ArrayList<>();
        String query = "SELECT * FROM wishlist WHERE buyer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buyerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                wishList.add(new WishListItem(productId, buyerId));
            }
        }
        return wishList;
    }

    // Method to remove a product from the wish list
    public boolean removeProductFromWishList(int buyerId, int productId) throws SQLException {
        String query = "DELETE FROM wishlist WHERE buyer_id = ? AND product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buyerId);
            stmt.setInt(2, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Returns true if a row was deleted
        }
    }
}

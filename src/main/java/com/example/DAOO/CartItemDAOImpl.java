package com.example.DAOO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Entity.CartItem;
import com.example.Entity.Product; // Import Product for creating cart items
import DBConnection.dbconnection;

public class CartItemDAOImpl implements CartItemDAO {
    private Connection connection;

    public CartItemDAOImpl() {
        connection = dbconnection.getConnection(); 
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO cart (buyer_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cartItem.getBuyerId());
            pstmt.setInt(2, cartItem.getProductId());
            pstmt.setInt(3, cartItem.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeCartItem(int productId,int buyerId) {
    	String query = "DELETE FROM cart WHERE product_id=? AND buyer_id =?";
    	try(PreparedStatement stmt = connection.prepareStatement(query)){
    		stmt.setInt(1, productId);
    		stmt.setInt(2, buyerId);
    		stmt.executeUpdate();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        String sql = "UPDATE cart SET quantity = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cartItem.getQuantity());
            pstmt.setInt(2, cartItem.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 

    @Override
    public List<CartItem> getCartItemsByBuyerId(int buyerId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE buyer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, buyerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Assume a ProductDAO is used to fetch product details
                ProductDAOImpl productDAO = new ProductDAOImpl();
                Product product = productDAO.getProductById(rs.getInt("product_id"));

                CartItem item = new CartItem(product, rs.getInt("quantity"), buyerId);
                item.setId(rs.getInt("id"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    @Override
    public void clearCart(int buyerId) {
        String sql = "DELETE FROM cart WHERE buyer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, buyerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCartItemQuantity(int productId, int buyerId, int newQuantity) {
        String query = "UPDATE cart SET quantity = ? WHERE product_id = ? AND buyer_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productId);
            stmt.setInt(3, buyerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
     
    }
}

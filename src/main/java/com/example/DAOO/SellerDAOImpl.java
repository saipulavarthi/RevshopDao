package com.example.DAOO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.Entity.Seller;

import DBConnection.dbconnection;

public class SellerDAOImpl implements SellerDAO {
	private Connection connection;

    // Constructor to initialize the database connection
    public SellerDAOImpl() {
        connection = dbconnection.getConnection(); // Assuming getConnection() returns a Connection object
    }

    @Override
    public boolean registerSeller(Seller seller) throws SQLException {
        String sql = "INSERT INTO sellers (name, email, password,business_details) VALUES (?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setString(3, seller.getPassword());
            statement.setString(4, seller.getBusinessDetails());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public Seller loginSeller(String email, String password) throws SQLException {
        String sql = "SELECT * FROM sellers WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Seller seller = new Seller();
                seller.setSellerId(resultSet.getInt("seller_id"));
                seller.setName(resultSet.getString("name"));
                seller.setEmail(resultSet.getString("email"));
                seller.setPassword(resultSet.getString("password"));
                return seller;
            }
        }
        return null; // Return null if no buyer found
    }

    @Override
    public Seller getSellerById(int sellerId) throws SQLException {
        String sql = "SELECT * FROM sellers WHERE seller_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sellerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Seller seller = new Seller();
                seller.setSellerId(resultSet.getInt("buyer_id"));
                seller.setName(resultSet.getString("name"));
                seller.setEmail(resultSet.getString("email"));
                seller.setPassword(resultSet.getString("password"));
                seller.setBusinessDetails(resultSet.getString("businessdetails"));
                return seller;
            }
        }
        return null; // Return null if no buyer found
    }

    // Close the database connection if needed
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}




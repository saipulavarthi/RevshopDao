package com.example.DAOO;

import com.example.Entity.Buyer;
import DBConnection.dbconnection;  // Make sure this is the correct import for your database connection

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyerDAOImpl implements BuyerDAO {
    private Connection connection;

    // Constructor to initialize the database connection
    public BuyerDAOImpl() {
        connection = dbconnection.getConnection(); // Assuming getConnection() returns a Connection object
    }

    @Override
    public boolean registerBuyer(Buyer buyer) throws SQLException {
        String sql = "INSERT INTO buyers (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, buyer.getName());
            statement.setString(2, buyer.getEmail());
            statement.setString(3, buyer.getPassword());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public Buyer loginBuyer(String email, String password) throws SQLException {
        Buyer buyer = null;
        String sql = "SELECT * FROM buyers WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                buyer = new Buyer();
                buyer.setBuyerId(rs.getInt("buyer_id")); // Adjust according to your column name
                buyer.setEmail(rs.getString("email"));
                buyer.setPassword(rs.getString("password"));
                // Set other Buyer attributes as necessary
            }
        }
        return buyer;
    }

    @Override
    public Buyer getBuyerById(int buyerId) throws SQLException {
        Buyer buyer = null; // Declare the Buyer variable
        String sql = "SELECT * FROM buyers WHERE buyer_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, buyerId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                buyer = new Buyer(); // Instantiate the Buyer object only if a record is found
                buyer.setBuyerId(resultSet.getInt("buyer_id"));
                buyer.setName(resultSet.getString("name"));
                buyer.setEmail(resultSet.getString("email"));
                buyer.setPassword(resultSet.getString("password"));
                // Set other Buyer attributes as necessary
            }
        }
        
        return buyer; // Return the Buyer object or null if not found
    }


    // Close the database connection if needed
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

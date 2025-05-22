package com.example.DAOO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Entity.Product;
import DBConnection.dbconnection;

public class ProductDAOImpl implements ProductDAO {
    private Connection connection;

    public ProductDAOImpl() {
        connection = dbconnection.getConnection();
    }
    public Product getProductById(int productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        Product product = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String productName = rs.getString("product_name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    double discountedPrice = rs.getDouble("discounted_price");
                    int quantity = rs.getInt("quantity");
                    String imageUrl = rs.getString("image_url");
                    String category = rs.getString("category");

                    // Create a Product object
                    product = new Product(productId, productName, description, quantity, imageUrl, category, price, discountedPrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exception properly
        }
        return product;
    }


    @Override
    public boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products(product_name, seller_id, description, price, discounted_price, quantity, threshold_quantity, category, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getSellerId());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setDouble(5, product.getDiscountedPrice());
            statement.setInt(6, product.getQuantity());
            statement.setInt(7, product.getThresholdQuantity());
            statement.setString(8, product.getCategory());
            statement.setString(9, product.getImageUrl());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the error or re-throwing the exception
        }
        return false;
    }
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category = ?";

        try (Connection connection = dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                double discountedPrice = resultSet.getDouble("discounted_price");
                String description = resultSet.getString("description");
                String imageUrl = resultSet.getString("image_url");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(productId, productName, description,  quantity,  imageUrl,  category,  price, discountedPrice);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

   

    @Override
    public List<Product> getProductsBySellerId(int sellerId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setSellerId(rs.getInt("seller_id"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price")); // Change to float if applicable
                product.setDiscountedPrice(rs.getFloat("discounted_price")); // Change to float if applicable
                product.setQuantity(rs.getInt("quantity"));
                product.setThresholdQuantity(rs.getInt("threshold_quantity"));
                product.setCategory(rs.getString("category"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }



    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products"; // Query to fetch all products

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Retrieve all necessary fields from the result set
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                double discountedPrice = rs.getDouble("discounted_price");
                int quantity = rs.getInt("quantity");
                String imageUrl = rs.getString("image_url");
                String category = rs.getString("category");
                int thresholdQuantity = rs.getInt("threshold_quantity");

                // Debugging: Print values retrieved from the database
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productName);
                System.out.println("Price: " + price);
                // Print all other fields similarly...

                // Create a new Product instance using the constructor with full details
                Product product = new Product(productId, productName, description, quantity, imageUrl, category, price, discountedPrice, thresholdQuantity);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly
        }
        return products;
    }
    public boolean updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET product_name = ?, description = ?, quantity = ?, image_url = ?, category = ?, price = ?, discounted_price = ?, threshold_quantity = ? WHERE product_id = ? AND seller_id = ?";
        try (Connection connection = dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getImageUrl());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setDouble(6, product.getPrice());
            preparedStatement.setDouble(7, product.getDiscountedPrice());
            preparedStatement.setInt(8, product.getThresholdQuantity());
            preparedStatement.setInt(9, product.getProductId());
            preparedStatement.setInt(10, product.getSellerId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean removeProduct(int productId, int sellerId) throws SQLException {
        String query = "DELETE FROM products WHERE product_id = ? AND seller_id = ?";
        try (Connection connection = dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, sellerId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }




    }


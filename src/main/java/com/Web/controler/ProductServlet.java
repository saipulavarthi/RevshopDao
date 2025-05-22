package com.Web.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.DAOO.ProductDAO;
import com.example.DAOO.ProductDAOImpl;
import com.example.Entity.Product;

import DBConnection.dbconnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); // New parameter to determine action

        if ("add".equals(action)) {
            // Handle adding a product
            addProduct(request, response);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        int sellerId = Integer.parseInt(request.getParameter("sellerId"));
        String description = request.getParameter("description");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageUrl = request.getParameter("imageUrl");
        String category = request.getParameter("category");

        // New parameters for price, discounted price, and threshold quantity
        float price = Float.parseFloat(request.getParameter("price"));
        float discountedPrice = Float.parseFloat(request.getParameter("discountedPrice"));
        int thresholdQuantity = Integer.parseInt(request.getParameter("thresholdQuantity"));

        Product product = new Product();
        product.setProductName(productName);
        product.setSellerId(sellerId);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setPrice(price);                      
        product.setDiscountedPrice(discountedPrice);  
        product.setThresholdQuantity(thresholdQuantity); 

        try (Connection connection = dbconnection.getConnection()) {
            if (connection != null) {
                ProductDAOImpl productDAO = new ProductDAOImpl();
                boolean result = productDAO.addProduct(product);

                if (result) {
                    response.getWriter().println("Product Added Successfully");
                } else {
                    response.getWriter().println("Failed to add product");
                }
            } else {
                response.getWriter().println("Database connection failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("SQL Error occurred while adding product");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sellerId = (Integer) request.getSession().getAttribute("sellerId"); // Assuming seller ID is stored in session

        ProductDAOImpl productDAO = new ProductDAOImpl();
        List<Product> products = productDAO.getProductsBySellerId(sellerId);

        request.setAttribute("products", products); // Set products as a request attribute
        RequestDispatcher dispatcher = request.getRequestDispatcher("sellerDashboard.jsp"); // Forward to JSP
        dispatcher.forward(request, response);
    }
}

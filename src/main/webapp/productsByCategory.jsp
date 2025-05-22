<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.Product" %>
<%@ page import="com.example.DAOO.ProductDAOImpl" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products by Category</title>
<style>
    body {
        font-family: Arial, sans-serif;
    }
    .container {
        margin: 20px;
    }
    .product {
        display: inline-block;
        width: 30%;
        margin: 15px;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        text-align: center;
    }
    .product img {
        width: 100%;
        height: auto;
        border-radius: 8px;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Products in Category: <%= request.getAttribute("category") %></h2>

        <div class="products">
            <% 
            String cat=(String)session.getAttribute("category");
                ProductDAOImpl productDAO = new ProductDAOImpl();
                List<Product> products = productDAO.getProductsByCategory(cat);
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
            %>
                <div class="product">
                    <h3><%= product.getProductName() %></h3>
                    <img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>">
                    <p>Price: $<%= product.getPrice() %></p>
                    <p>Discounted Price: $<%= product.getDiscountedPrice() %></p>
                    <p>Description: <%= product.getDescription() %></p>
                    <p>Available Quantity: <%= product.getQuantity() %></p>
                </div>
            <% 
                    } 
                } else { 
            %>
                <p>No products available in this category.</p>
            <% 
                } 
            %>
        </div>
    </div>
</body>
</html>

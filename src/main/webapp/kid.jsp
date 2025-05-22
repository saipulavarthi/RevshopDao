<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.Product" %>
<%@ page import="com.example.Entity.WishListItem" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kids' Products</title>
   <style>
    body {
        font-family: 'Arial', sans-serif;
        margin: 0;
        padding: 0;
        background: linear-gradient(135deg, #ff7e5f, #feb47b);
        display: flex;
        flex-direction: column;
        align-items: center;
        min-height: 100vh;
    }

    .navbar {
        background-color: #333;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        width: 100%;
        position: fixed;
        top: 0;
        display: flex;
        justify-content: space-between;
        padding: 10px 20px;
    }

    .navbar a {
        color: white;
        padding: 14px 20px;
        text-decoration: none;
        transition: background-color 0.3s, transform 0.3s;
    }

    .navbar a:hover {
        background-color: #45a049;
        transform: scale(1.1);
    }

    .navbar .logout-btn,
    .navbar .cart-btn,
    .navbar .wishlist-btn,
    .navbar .welcome-btn {
        margin-left: auto;
    }

    .container {
        margin: 80px 20px 20px;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 15px;
        box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
        width: 90%;
        max-width: 1200px;
        animation: fadeIn 1s ease-in-out;
    }

    @keyframes fadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }

    h2 {
        color: #333;
        text-align: center;
    }

    .products {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 20px;
        padding: 20px;
    }

    .product {
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 15px;
        box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
        text-align: center;
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .product img {
        width: 100%;
        height: 200px; /* Ensuring all images are the same size */
        object-fit: cover; /* Ensure the image covers the area without distorting */
        border-radius: 10px;
        margin-bottom: 15px;
    }

    .product h3 {
        color: #555;
        margin: 10px 0;
    }

    .product p {
        color: #666;
        margin-bottom: 15px;
    }

    .product form {
        margin-bottom: 10px;
    }

    .product button {
        padding: 10px 15px;
        background-color: #64b5f6; /* Light blue for kids' products */
        color: white;
        border: none;
        border-radius: 5px;
        transition: background-color 0.3s;
        cursor: pointer;
    }

    .product button:hover {
        background-color: #2196f3; /* Darker blue for hover effect */
    }

    .error {
        color: red;
        text-align: center;
    }

    .success {
        color: green;
        text-align: center;
    }
</style>

</head>
<body>
    <div class="navbar">
         <a href="getProductsByCategory?category=Men">Men</a>
        <a href="getProductsByCategory?category=Women">Women</a>
        <a href="getProductsByCategory?category=Kid">Kids</a>
        <a href="index.html" class="logout-btn">Logout</a>
        <a href="wishlist.jsp" class=wishlist-btn">WishList</a>
        <a href="welcome.html" class= welcome-btn">Home</a>
    </div>
    <div class="container">
        <h2>Products in Category: Kids</h2>
        
        <!-- Display success or error message -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            String successMessage = (String) request.getAttribute("successMessage");

            if (errorMessage != null) {
        %>
            <p style="color:red;"><%= errorMessage %></p>
        <%
            } else if (successMessage != null) {
        %>
            <p style="color:green;"><%= successMessage %></p>
        <%
            }
        %>

       <div class="products">
    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
    %>
        <div class="product">
            <h3><%= product.getProductName() %></h3>
            <img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>" style="max-width:100%; height:auto;">
            <p>Price: $<%= product.getPrice() %></p>
            <p>Discounted Price: $<%= product.getDiscountedPrice() %></p>
            <p>Description: <%= product.getDescription() %></p>
            <p>Available Quantity: <%= product.getQuantity() %></p>

            <!-- Add to Wish List Form -->
            <form action="addToWishList" method="post">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <button type="submit">Add to Wish List</button>
            </form>

            <!-- Add to Cart Form -->
            <form action="addToCart" method="post">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <input type="hidden" name="quantity" value="1">
                <button type="submit" class="button">Add to Cart</button>
            </form>
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
</body>
</html>

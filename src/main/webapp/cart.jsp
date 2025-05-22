<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.Product" %>
<%@ page import="com.example.Entity.CartItem" %>
<%@ page import="com.example.Entity.Buyer" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
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
            animation: fadeIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        .navbar {
            background-color: #333;
            width: 100%;
            padding: 10px 0;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: center;
        }

        .navbar a {
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            transition: background-color 0.3s, transform 0.3s;
        }

        .navbar a:hover {
            background-color: #45a049;
            transform: scale(1.1);
        }

        h2 {
            color: #333;
            margin: 20px 0;
            display: flex;
            align-items: center;
        }

        h2::before {
            content: '🛒';
            margin-right: 10px;
        }

        .cart {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 800px;
        }

        .cart-item {
            display: flex;
            flex-direction: column;
            margin-bottom: 20px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 20px;
        }

        .cart-item h3 {
            color: #555;
            margin-bottom: 10px;
        }

        .cart-item img {
            width: 100%;
            max-width: 150px;
            height: auto;
            border-radius: 10px;
            margin-bottom: 10px;
        }

        .cart-item p {
            color: #666;
            margin-bottom: 10px;
        }

        .cart-item form {
            display: inline-block;
        }

        .cart-item form input[type="number"] {
            width: 60px;
            padding: 5px;
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .cart-item form button {
            padding: 8px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            cursor: pointer;
        }

        .cart-item form button:hover {
            background-color: #45a049;
        }

        .cart-item .remove-button {
            background-color: #dc3545;
        }

        .cart-item .remove-button:hover {
            background-color: #c82333;
        }

        hr {
            border: none;
            border-top: 1px solid #ddd;
            margin: 20px 0;
        }

        .total-amount {
            color: #333;
            font-weight: bold;
            text-align: right;
        }

        .checkout-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            cursor: pointer;
        }

        .checkout-button:hover {
            background-color: #45a049;
        }

        .empty-cart {
            color: #666;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="welcome.html" class="welcome-btn">Home</a>
        <a href="getProductsByCategory?category=Men">Men</a>
        <a href="getProductsByCategory?category=Women">Women</a>
        <a href="getProductsByCategory?category=Kid">Kids</a>
        <a href="index.html" class="logout-btn">Logout</a>
    </div>
    
    <h2>Your Cart</h2>

    <div class="cart">
        <%
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            Buyer buyer = (Buyer) session.getAttribute("buyer");

            if (cart != null && !cart.isEmpty()) {
                double totalAmount = 0;
                for (CartItem cartItem : cart) {
                    Product product = cartItem.getProduct();
                    totalAmount += cartItem.getTotalPrice();
        %>
        <div class="cart-item">
            <h3><%= product.getProductName() %></h3>
            <img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>">
            <p>Price: $<%= product.getDiscountedPrice() %></p>
            <p>Quantity: 
                <form action="updateCart" method="post">
                    <input type="hidden" name="buyerId" value="<%= buyer.getBuyerId() %>">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="number" name="quantity" value="<%= cartItem.getQuantity() %>" min="1">
                    <button type="submit">Update</button>
                </form>
            </p>
            <p>Total Price: $<%= cartItem.getTotalPrice() %></p>
            
            <!-- Remove Item Form -->
            <form action="removeFromCart" method="post">
                <input type="hidden" name="buyerId" value="<%= buyer.getBuyerId() %>">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <button type="submit" class="remove-button">Remove</button>
            </form>
        </div>
        <hr>
        <%
                }
        %>
        <h3>Total Amount: $<%= totalAmount %></h3>
        <button class="checkout-button" onclick="window.location.href='checkout.jsp?totalAmount=<%= totalAmount %>&buyerId=<%= buyer.getBuyerId() %>';">
            Checkout
        </button>
        <% } else { %>
        <p class="empty-cart">Your cart is empty.</p>
        <% } %>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #ff7e5f, #feb47b);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
            text-align: center;
            animation: fadeIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        h1 {
            color: #333;
            margin-bottom: 20px;
        }

        .success {
            color: green;
            margin: 20px 0;
        }

        h2 {
            color: #333;
            margin-bottom: 10px;
        }

        p {
            color: #666;
        }

        .gif-container {
            margin: 20px 0;
        }

        .button-container {
            margin-top: 20px;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Order Confirmation</h1>
    
    <% 
        String successMessage = (String) session.getAttribute("message");
        if (successMessage != null) {
    %>
        <div class="success"><%= successMessage %></div>
        <% session.removeAttribute("message"); %>
    <% } %>
    
    <h2>Thank you for your order!</h2>
    <p>Your order has been placed successfully.</p>

    <div class="gif-container">
        <img src="https://cdn.dribbble.com/users/1751799/screenshots/5512482/media/1cbd3594bb5e8d90924a105d4aae924c.gif" alt="Order Placed" width="300">
    </div>
    
    <div class="button-container">
        <a href="welcome.html">Go back to Home</a>
    </div>
</body>
</html>


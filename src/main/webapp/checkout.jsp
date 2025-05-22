<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.CartItem" %>
<%@ page import="com.example.Entity.Buyer" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
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

        h1 {
            color: #333;
            margin: 20px 0;
        }

        .error {
            color: red;
            margin: 20px 0;
        }

        .form-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 500px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 8px;
            color: #555;
            font-weight: bold;
        }

        input[type="text"] {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        a {
            display: inline-block;
            margin-top: 20px;
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
    <h1>Checkout</h1>
    
    <% 
        // Retrieve Buyer object from session
        Buyer buyer = (Buyer) session.getAttribute("buyer");
        
        // Check if buyer is null (not logged in)
        if (buyer == null) { 
    %>
        <div class="error">You must be logged in to checkout.</div>
        <a href="BuyerLogin.html">Login</a>
    <% 
        return; // Stop processing if the user is not logged in 
    } %>
    
    <% 
        // Retrieve the cart from the session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    Double totalAmount = 0.0;
    String totalAmountStr = request.getParameter("totalAmount");
    if (totalAmountStr != null) {
        try {
            totalAmount = Double.parseDouble(totalAmountStr);
        } catch (NumberFormatException e) {
            totalAmount = 0.0;
        }
    }
    out.print("total amount: " + totalAmount);
         
        // Display error message if it exists
        String errorMessage = (String) session.getAttribute("error");
        if (errorMessage != null) { 
    %>
        <div class="error"><%= errorMessage %></div>
    <% 
        session.removeAttribute("error"); 
    } %>

    <div class="form-container">
    <form id="checkoutForm" action="CheckoutServlet" method="post">
        <input type="hidden" name="buyerId" value="<%= buyer.getBuyerId() %>">
        <input type="hidden" name="totalAmount" value="<%= totalAmount %>">
        
        <label for="shippingAddress">Shipping Address:</label>
        <input type="text" id="shippingAddress" name="shippingAddress" value="<%= request.getParameter("shippingAddress") %>" required><br><br>
        
        <label for="billingAddress">Billing Address:</label>
        <input type="text" id="billingAddress" name="billingAddress" value="<%= request.getParameter("billingAddress") %>" required><br><br>
        
        <label for="contactNumber">Contact Number:</label>
        <input type="text" id="contactNumber" name="contactNumber" value="<%= request.getParameter("contactNumber") %>" required><br><br>
        
        <!-- Payment method will be dynamically set -->
        <input type="hidden" id="paymentMethod" name="paymentMethod" value="Credit Card">
        <input type="hidden" name="paymentStatus" value="Pending">

        <% 
            if (cart != null) {
                for (CartItem cartItem : cart) { 
        %>
                <input type="hidden" name="productId" value="<%= cartItem.getProduct().getProductId() %>">
                <input type="hidden" name="quantity" value="<%= cartItem.getQuantity() %>">
        <% 
                } 
            } else { 
        %>
            <div class="error">Your cart is empty.</div>
        <% 
            } 
        %>
        <input type="hidden" id="paymentType" name="paymentType" value="Online">
        
        <!-- Buttons -->
        <input type="submit" value="Online Payment" onclick="document.getElementById('paymentType').value='Online'">
        
        <!-- Cash on Delivery button submits the form normally -->
<button type="submit" onclick="document.getElementById('paymentType').value='COD'">Cash on Delivery</button>

    </form>
</div>

    <a href="cart.jsp">Go back to Cart</a>
        <script>
        function setCOD() {
            document.getElementById('paymentMethod').value = 'Cash on Delivery';
        }

        function startOnlinePayment() {
            // Set paymentMethod to Cash on Delivery before submitting form
            document.getElementById('paymentMethod').value = 'Cash on Delivery';
        }

        function startOnlinePayment() {
            // Validate form fields first
            const shippingAddress = document.getElementById('shippingAddress').value.trim();
            const billingAddress = document.getElementById('billingAddress').value.trim();
            const contactNumber = document.getElementById('contactNumber').value.trim();

            if (!shippingAddress || !billingAddress || !contactNumber) {
                alert("Please fill in all required fields.");
                return;
            }

            // Set paymentMethod to Credit Card
            document.getElementById('paymentMethod').value = 'Credit Card';

            const totalAmount = <%= (int)(totalAmount * 100) %>; // amount in paise

            var options = {
                "key": "rzp_test_37MbLXJMCUd3yL",
                "amount": totalAmount,
                "currency": "INR",
                "name": "Revshop",
                "description": "Order Payment",
                "handler": function (response) {
                    // On successful payment, submit the form including paymentId
                    // Append paymentId in a hidden input and submit form
                    let form = document.getElementById('checkoutForm');

                    // Create hidden input for razorpay_payment_id
                    let input = document.createElement("input");
                    input.type = "hidden";
                    input.name = "razorpay_payment_id";
                    input.value = response.razorpay_payment_id;
                    form.appendChild(input);

                    form.submit();
                },
                "prefill": {
                    "name": "<%= buyer.getName() %>",
                    "email": "<%= buyer.getEmail() %>"
                },
                "theme": {
                    "color": "#3399cc"
                }
            };

            var rzp1 = new Razorpay(options);
            rzp1.open();
        }

    </script>
</body>
</html>
</html>
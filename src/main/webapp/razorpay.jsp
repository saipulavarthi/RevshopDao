<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment</title>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <script>
        window.onload = function() {
            const options = {
                "key": "<%= request.getAttribute("razorpayKey") %>",
                "amount": <%= request.getAttribute("totalAmount") %> *100, // Amount in paise
                "currency": "INR",
                "name": "sai",
                "description": "Order Payment",
                "handler": function (response) {
                    // Logic after successful payment
                    console.log("Payment successful, response:", response);
                    
                    // Optionally, send payment details to your server to confirm the order
                    // You can use AJAX or redirect to another servlet to process the payment confirmation
                    window.location.href = "<%= request.getContextPath() %>/orderConfirmation.jsp";
                },
                "prefill": {
                    "name": "John Doe", // Replace with actual buyer details if available
                    "email": "john.doe@example.com",
                    "contact": "9999999999"
                },
                "theme": {
                    "color": "#3399cc"
                }
            };
            
            const rzp1 = new Razorpay(options);
            rzp1.open();
        };
    </script>
</head>
<body>
</body>
</html>
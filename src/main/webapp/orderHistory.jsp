<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 40px 20px;
            color: #333;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
            font-weight: 700;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            max-width: 900px;
            margin: 0 auto 40px auto;
            background: #fff;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 15px 20px;
            text-align: left;
        }

        th {
            background-color: #2980b9;
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        tr:nth-child(even) {
            background-color: #f9fbfc;
        }

        tr:hover {
            background-color: #dceefc;
            cursor: default;
        }

        p {
            text-align: center;
            font-size: 1.1em;
            color: #7f8c8d;
        }

        a {
            display: inline-block;
            margin: 0 auto;
            text-decoration: none;
            background-color: #2980b9;
            color: white;
            padding: 12px 25px;
            border-radius: 6px;
            font-weight: 600;
            transition: background-color 0.3s ease;
            text-align: center;
        }

        a:hover {
            background-color: #1f618d;
        }
    </style>
    <title>Order History</title>
</head>
<body>
<h2>Order History</h2>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
%>
    <table border="1" cellpadding="10">
        <tr>
            <th>Order ID</th>
            <th>Status</th>
            <th>Total</th>
            <th>Payment</th>
            <th>Shipping Address</th>
            <th>Order Date</th>
        </tr>
        <% for (Order o : orders) { %>
        <tr>
            <td><%= o.getOrderId() %></td>
            <td><%= o.getStatus() %></td>
            <td><%= o.getTotalAmount() %></td>
            <td><%= o.getPaymentMethod() %></td>
            <td><%= o.getShippingAddress() %></td>
            <td><%= o.getCreatedAt() %></td>
        </tr>
        <% } %>
    </table>
<%
    } else {
%>
    <p>No orders found.</p>
<%
    }
%>

<a href="welcome.html">Back to Home</a>
</body>
</html>

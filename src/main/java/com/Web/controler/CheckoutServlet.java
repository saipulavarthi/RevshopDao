package com.Web.controler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.DAOO.CartItemDAOImpl;
import com.example.DAOO.OrderDAO;
import com.example.DAOO.OrderItemDAO;
import com.example.Entity.CartItem;
import com.example.Entity.Order;
import com.example.Entity.OrderItem;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String paymentType = request.getParameter("paymentType");
        HttpSession session = request.getSession();
        int buyerId = (int) session.getAttribute("buyerId"); // Retrieve buyerId from session
        String contactNumber = request.getParameter("contactNumber");
        String paymentStatus = request.getParameter("paymentStatus");
		/* String paymentMethod = request.getParameter("paymentMethod"); */
        String shippingAddress = request.getParameter("shippingAddress"); // Get shipping address from request
        String billingAddress = request.getParameter("billingAddress"); // Get billing address from request
        Double totalAmount = Double.parseDouble(request.getParameter("totalAmount")); // Retrieve total amount
        System.out.print( "see"+billingAddress);
        System.out.print("Amount" +totalAmount);
        // Initialize DAOs
        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        CartItemDAOImpl cartItemDAO = new CartItemDAOImpl(); // Assuming you have a CartDAO for managing the cart

        try {
            // Decide paymentMethod based on paymentType
            String paymentMethod;
            if ("COD".equals(paymentType)) {
                paymentMethod = "Cash on Delivery";
            } else {
                paymentMethod = request.getParameter("paymentMethod"); // e.g. "Credit Card"
            }

            // Create Order object with paymentMethod decided above
            Order order = new Order(
                buyerId,
                shippingAddress,
                billingAddress,
                contactNumber,
                totalAmount,
                paymentMethod,
                "Pending"
            );

            // Create order and get orderId
            int orderId = orderDAO.createOrder(order);

            // Retrieve cart items
            List<CartItem> cartItems = cartItemDAO.getCartItemsByBuyerId(buyerId);

            // Add order items
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem(orderId, cartItem.getProductId(), cartItem.getQuantity(), orderId, cartItem.getPrice());
                orderItemDAO.addOrderItem(orderItem);
            }

            // Clear cart
            cartItemDAO.clearCart(buyerId);

            if ("COD".equals(paymentType)) {
                // For COD, redirect to success page immediately after saving order
                response.sendRedirect("orderConfirmation.jsp"); // Adjust the page as per your app
                return;
            } else {
                // For Online payment, forward to Razorpay page
                int amountInPaise = (int) (totalAmount * 100);
                String razorpayKey = "rzp_test_37MbLXJMCUd3yL"; // Your Razorpay key

                request.setAttribute("razorpayKey", razorpayKey);
                request.setAttribute("amount", amountInPaise);
                request.setAttribute("orderId", orderId);
                request.setAttribute("shippingAddress", shippingAddress);

                RequestDispatcher dispatcher = request.getRequestDispatcher("razorpay.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred during checkout. Please try again.");
            response.sendRedirect("checkout.jsp");
        }
    }
}
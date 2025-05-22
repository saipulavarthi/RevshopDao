package com.example.Entity;

import java.sql.Timestamp;

import java.util.Date;

public class Order {
    private int orderId;
    private int buyerId;
    private String status;
    private String shippingAddress;
    private String billingAddress;
    private double totalAmount;
    private String paymentMethod;
    private String Status;
    private String contactNumber;     // ✅ Changed from int to String
    private Timestamp createdAt;           // ✅ Changed from String to Date

    // ✅ Remove productId and quantity — they belong to OrderItem

    // Constructors
    public Order() {}

    public Order(int buyerId, String shippingAddress, String billingAddress, String status,
                 double totalAmount, String contactNumber, String paymentMethod, String paymentStatus) {
        this.buyerId = buyerId;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.status = status;
        this.contactNumber = contactNumber;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.Status = paymentStatus;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return Status; }
    public void setPaymentStatus(String paymentStatus) { this.Status = paymentStatus; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Order(int buyerId, String shippingAddress, String billingAddress, String contactNumber, double totalAmount, String paymentMethod, String status) {
        this.buyerId = buyerId;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.contactNumber = contactNumber;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

}

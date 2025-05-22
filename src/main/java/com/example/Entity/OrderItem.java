package com.example.Entity;

public class OrderItem {
    private int orderItemId;       // Unique order item ID
    private int orderId;           // ID of the associated order
    private int productId;         // ID of the product
    private int quantity;          // Quantity of the product in the order
    private double price;          // Price of the product at the time of the order

    // Constructor
    public OrderItem(int orderItemId, int orderId, int productId, int quantity, double price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItem(int orderId, int productId, int quantity, int price) {
    	this.orderId=orderId;
    	this.productId=productId;
    	this.quantity=quantity;
    	this.price=price;
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

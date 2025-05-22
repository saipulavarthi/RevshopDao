package com.example.Entity;

public class CartItem {
    private int id; // Added for cart item ID
    private Product product;
    private int quantity;
    private int buyerId; // Added buyerId to track the buyer

    public CartItem(Product product, int quantity, int buyerId) {
        this.product = product;
        this.quantity = quantity;
        this.buyerId = buyerId;
    }

    public CartItem() {
        // Empty constructor
    }

    // Getters and setters for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter and setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and setter for buyerId
    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    // Added method for productId to be used in CartItemDAOImpl
    public int getProductId() {
        return product.getProductId();
    }
    public double getPrice() {
    	return product.getPrice();
    }

    // Calculate total price using the getPrice() method
    public double getTotalPrice() {
        return product.getPrice() * quantity; // Use getPrice() instead of getDiscountedPrice()
    }
}

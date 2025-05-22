package com.example.Entity;

public class Product {
    private int productId;
    private String productName;
    private int sellerId;
    private String sellerName;
    private String description;
    private int quantity;
    private String imageUrl;
    private String category;
    private String createdAt;
    private String updatedAt;
    private double price;
    private double discountedPrice;
    private int thresholdQuantity;

    // Default Constructor
    public Product() {
    }
 
    	// Constructor to include all fields
    	public Product(int productId, String productName, String description, int quantity, String imageUrl, String category, double price, double discountedPrice, int thresholdQuantity) {
    	    this.productId = productId;
    	    this.productName = productName;
    	    this.description = description;
    	    this.quantity = quantity;
    	    this.imageUrl = imageUrl;
    	    this.category = category;
    	    this.price = price;
    	    this.discountedPrice = discountedPrice;
    	    this.thresholdQuantity = thresholdQuantity;
    	}

    

    // Constructor to initialize product details
    public Product(String productName, int sellerId, String sellerName, String description, int quantity, String imageUrl, String category, double price, double discountedPrice, int thresholdQuantity) {
        this.productName = productName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.description = description;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.thresholdQuantity = thresholdQuantity;
    }

    // Constructor to initialize selected fields (for use in AddToCartServlet or other simple use cases)
    public Product(int productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    // Constructor to initialize basic product details
    public Product(String productName, String description, int quantity, String imageUrl, String category, double price, double discountedPrice) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    // Constructor with full product details
    public Product(int productId, String productName, String description, int quantity, String imageUrl, String category, double price, double discountedPrice) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getThresholdQuantity() {
        return thresholdQuantity;
    }

    public void setThresholdQuantity(int thresholdQuantity) {
        this.thresholdQuantity = thresholdQuantity;
    }

    // Override equals() and hashCode() for correct comparison in collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product product = (Product) obj;

        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(productId);
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", thresholdQuantity=" + thresholdQuantity +
                '}';
    }

    
}

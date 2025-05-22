package com.example.Entity;
import java.sql.Timestamp;

public class Seller {
	

	

	    private int sellerId;
	    private String name;
	    private String email;
	    private String password;
	    private String businessDetails;
	    private Timestamp createdAt;

	    // Default constructor
	    public Seller() {
	    }

	    // Parameterized constructor
	    public Seller(int sellerId, String name, String email, String password, String businessDetails, Timestamp createdAt) {
	        this.sellerId = sellerId;
	        this.name = name;
	        this.email = email;
	        this.password = password;
	        this.businessDetails = businessDetails;
	        this.createdAt = createdAt;
	    }

	    // Getters and Setters
	    public int getSellerId() {
	        return sellerId;
	    }

	    public void setSellerId(int sellerId) {
	        this.sellerId = sellerId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getBusinessDetails() {
	        return businessDetails;
	    }

	    public void setBusinessDetails(String businessDetails) {
	        this.businessDetails = businessDetails;
	    }

	    public Timestamp getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(Timestamp createdAt) {
	        this.createdAt = createdAt;
	    }
	}


	


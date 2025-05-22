<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.Product" %>
<%@ page import="com.example.DAOO.ProductDAOImpl" %>
<%@ page import="DBConnection.dbconnection" %>
<%
    // Get the product ID from the request parameter
    int productId = Integer.parseInt(request.getParameter("productId"));
    
    // Initialize ProductDAO
    ProductDAOImpl productDAO = new ProductDAOImpl();
    
    // Get the product details based on product ID
    Product product = productDAO.getProductById(productId);
    
    // Check if product exists
    if (product == null) {
        response.sendRedirect("SellerWelcome.jsp?error=productnotfound");
        return;
    }
    
    // Handle form submission
    if (request.getMethod().equalsIgnoreCase("POST")) {
        // Get form data
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double discountedPrice = Double.parseDouble(request.getParameter("discountedPrice"));
        int thresholdQuantity = Integer.parseInt(request.getParameter("thresholdQuantity"));
        String category = request.getParameter("category");
        String imageUrl = request.getParameter("imageUrl");
        
        // Update product in database
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        Integer sellerId = (Integer) session.getAttribute("sellerId");
        product.setSellerId(sellerId);
        
        // Save the updated product
        boolean updated = productDAO.updateProduct(product);
        
        if (updated) {
            // Redirect back to seller dashboard with success message
            response.sendRedirect("SellerWelcome.jsp?success=updated");
        } else {
            // Redirect back with error message
            response.sendRedirect("editProduct.jsp?productId=" + productId + "&error=failed");
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Product</title>
    <style>
        /* Global Styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
        }
        
        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 20px;
        }
        
        /* Form Section */
        .form-section {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-bottom: 30px;
        }
        
        .form-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        
        .form-header h2 {
            color: #4a6ee0;
            font-size: 24px;
            font-weight: 600;
        }
        
        /* Form Elements */
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #555;
        }
        
        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 15px;
            color: #333;
            transition: border 0.3s ease;
        }
        
        input[type="text"]:focus,
        input[type="number"]:focus,
        textarea:focus,
        select:focus {
            border-color: #4a6ee0;
            outline: none;
            box-shadow: 0 0 0 2px rgba(74, 110, 224, 0.2);
        }
        
        textarea {
            min-height: 120px;
            resize: vertical;
        }
        
        /* Buttons */
        .btn-group {
            display: flex;
            justify-content: flex-start;
            gap: 15px;
            margin-top: 30px;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 500;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            border: none;
        }
        
        .save-btn {
            background-color: #4a6ee0;
            color: white;
        }
        
        .save-btn:hover {
            background-color: #3a5dc9;
            transform: translateY(-2px);
        }
        
        .cancel-btn {
            background-color: #f2f2f2;
            color: #666;
        }
        
        .cancel-btn:hover {
            background-color: #e6e6e6;
        }
        
        /* Product Image Preview */
        .image-preview {
            width: 100%;
            margin-top: 10px;
            border-radius: 5px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        
        .image-preview img {
            width: 100%;
            max-height: 200px;
            object-fit: contain;
            background-color: #f9f9f9;
            display: block;
        }
        
        /* Error/Success Messages */
        .alert {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .alert-error {
            background-color: #ffebee;
            color: #c62828;
            border-left: 4px solid #c62828;
        }
        
        .alert-success {
            background-color: #e8f5e9;
            color: #2e7d32;
            border-left: 4px solid #2e7d32;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-section">
            <div class="form-header">
                <h2>Edit Product</h2>
            </div>
            
            <% if(request.getParameter("error") != null) { %>
                <div class="alert alert-error">
                    There was a problem updating the product. Please try again.
                </div>
            <% } %>
            
            <form action="editProduct.jsp?productId=<%= productId %>" method="post">
                <div class="form-group">
                    <label for="productName">Product Name</label>
                    <input type="text" id="productName" name="productName" value="<%= product.getProductName() %>" required>
                </div>
                
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" required><%= product.getDescription() %></textarea>
                </div>
                
                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="number" id="price" name="price" value="<%= product.getPrice() %>" step="0.01" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="discountedPrice">DiscountedPrice</label>
                    <input type="number" id="discountedPrice" name="discountedPrice" value="<%= product.getDiscountedPrice() %>" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="thresholdQuantity">ThersholdQuantity</label>
                    <input type="number" id="thresholdQuantity" name="thresholdQuantity" value="<%= product.getThresholdQuantity() %>" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="quantity">Quantity</label>
                    <input type="number" id="quantity" name="quantity" value="<%= product.getQuantity() %>" min="0" required>
                </div>
                
                
                <div class="form-group">
                    <label for="category">Category</label>
                    <input type="text" id="category" name="category" value="<%= product.getCategory() %>" required>
                </div>
                
                <div class="form-group">
                    <label for="imageUrl">Image URL</label>
                    <input type="text" id="imageUrl" name="imageUrl" value="<%= product.getImageUrl() %>" required>
                    <div class="image-preview">
                        <img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>">
                    </div>
                </div>
                
                <div class="btn-group">
                    <button type="submit" class="btn save-btn">Save Changes</button>
                    <a href="SellerWelcome.jsp" class="btn cancel-btn">Cancel</a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        // Update image preview when imageUrl changes
        document.getElementById('imageUrl').addEventListener('input', function() {
            const imageUrl = this.value;
            const previewImg = document.querySelector('.image-preview img');
            previewImg.src = imageUrl;
        });
    </script>
</body>
</html>
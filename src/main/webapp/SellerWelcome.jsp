<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.Product" %>
<%@ page import="com.example.DAOO.ProductDAOImpl" %>
<%@ page import="DBConnection.dbconnection" %>
<%
ProductDAOImpl productDAO = new ProductDAOImpl();
int sellerId = (Integer) session.getAttribute("sellerId"); // Get sellerId from session
List<Product> products = productDAO.getProductsBySellerId(sellerId); // Fetch products
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Seller Dashboard</title>
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
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
    }
    
    /* Header Section */
    .header {
        background-color: #ffffff;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        margin-bottom: 30px;
        border-radius: 8px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    
    .header h2 {
        color: #4a6ee0;
        font-size: 28px;
        font-weight: 600;
    }
    
    /* Navigation */
    .navbar {
        display: flex;
        align-items: center;
    }
    
    .navbar a {
        display: inline-block;
        margin-left: 15px;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 5px;
        font-weight: 500;
        transition: all 0.3s ease;
    }
    
    .add-product {
        background-color: #4a6ee0;
        color: white;
    }
    
    .add-product:hover {
        background-color: #3a5dc9;
        transform: translateY(-2px);
    }
    
    .logout {
        background-color: #f2f2f2;
        color: #666;
    }
    
    .logout:hover {
        background-color: #e6e6e6;
    }
    
    /* Product Section */
    .products-section {
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        padding: 25px;
        margin-bottom: 30px;
    }
    
    .products-section h3 {
        color: #333;
        font-size: 22px;
        font-weight: 600;
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid #eee;
    }
    
    /* Table Styling */
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background-color: #fff;
        border-radius: 5px;
        overflow: hidden;
    }
    
    th {
        background-color: #f6f8ff;
        color: #4a6ee0;
        padding: 15px;
        text-align: left;
        font-weight: 600;
        border-bottom: 2px solid #eaeaea;
    }
    
    td {
        padding: 15px;
        border-bottom: 1px solid #eaeaea;
        color: #555;
        vertical-align: middle;
    }
    
    tr:hover {
        background-color: #f9faff;
    }
    
    /* Product Image */
    td img {
        width: 70px;
        height: 70px;
        object-fit: cover;
        border-radius: 4px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease;
    }
    
    td img:hover {
        transform: scale(1.1);
    }
    
    /* Responsive Design */
    @media (max-width: 768px) {
        .header {
            flex-direction: column;
            text-align: center;
        }
        
        .navbar {
            margin-top: 15px;
        }
        
        table {
            display: block;
            overflow-x: auto;
        }
        
        th, td {
            min-width: 120px;
        }
    }
    
    /* Status Indicator */
    .stock-status {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 10px;
        font-size: 12px;
        font-weight: bold;
    }
    
    .in-stock {
        background-color: #e8f5e9;
        color: #2e7d32;
    }
    
    .low-stock {
        background-color: #fff3e0;
        color: #ef6c00;
    }
    
    .out-of-stock {
        background-color: #ffebee;
        color: #c62828;
    }
    
    /* Price Styling */
    .price {
        font-weight: 600;
        color: #4a6ee0;
    }
    
    /* Action Buttons */
    .actions {
        white-space: nowrap;
    }
    
    .btn {
        display: inline-block;
        padding: 8px 12px;
        border-radius: 4px;
        font-size: 13px;
        font-weight: 500;
        text-decoration: none;
        text-align: center;
        cursor: pointer;
        transition: all 0.2s ease;
        margin: 0 3px;
    }
    
    .edit-btn {
        background-color: #4a6ee0;
        color: white;
    }
    
    .edit-btn:hover {
        background-color: #3a5dc9;
        transform: translateY(-2px);
    }
    
    .delete-btn {
        background-color: #f44336;
        color: white;
    }
    
    .delete-btn:hover {
        background-color: #d32f2f;
        transform: translateY(-2px);
    }
    
    /* Modal Styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
    }
    
    .modal-content {
        background-color: white;
        margin: 15% auto;
        padding: 25px;
        width: 400px;
        border-radius: 8px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        text-align: center;
    }
    
    .modal h4 {
        margin-bottom: 20px;
        color: #333;
    }
    
    .modal-buttons {
        margin-top: 25px;
        display: flex;
        justify-content: center;
        gap: 15px;
    }
    
    .cancel-btn {
        background-color: #e0e0e0;
        color: #333;
    }
    
    .confirm-delete-btn {
        background-color: #f44336;
        color: white;
    }
    
    /* Dashboard Stats */
    .stats-container {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
    }
    
    .stat-box {
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        text-align: center;
    }
    
    .stat-box h4 {
        color: #666;
        font-size: 16px;
        margin-bottom: 10px;
    }
    
    .stat-box .number {
        color: #4a6ee0;
        font-size: 28px;
        font-weight: 600;
    }
</style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2>Welcome, Seller!</h2>
        <div class="navbar">
            <a href="ProductAdd.html" class="add-product">Add Product</a>
            <a href="index.html" class="logout">Logout</a>
        </div>
    </div>
    
    <div class="stats-container">
        <div class="stat-box">
            <h4>Total Products</h4>
            <div class="number"><%= products.size() %></div>
        </div>
        <div class="stat-box">
            <h4>Active Listings</h4>
            <div class="number"><%= products.size() %></div>
        </div>
        <div class="stat-box">
            <h4>Categories</h4>
            <div class="number">
                <%
                    // Count unique categories
                    java.util.Set<String> uniqueCategories = new java.util.HashSet<>();
                    for (Product product : products) {
                        uniqueCategories.add(product.getCategory());
                    }
                %>
                <%= uniqueCategories.size() %>
            </div>
        </div>
    </div>

    <div class="products-section">
        <h3>Your Products</h3>
        
        <table>
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Image</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Product product : products) { %>
                <tr>
                    <td><%= product.getProductName() %></td>
                    <td><%= product.getDescription() %></td>
                    <td class="price">$<%= String.format("%.2f", product.getPrice()) %></td>
                    <td><%= product.getQuantity() %></td>
                    <td><%= product.getCategory() %></td>
                    <td><img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>"></td>
                    <td>
                        <% if (product.getQuantity() > 10) { %>
                            <span class="stock-status in-stock">In Stock</span>
                        <% } else if (product.getQuantity() > 0) { %>
                            <span class="stock-status low-stock">Low Stock</span>
                        <% } else { %>
                            <span class="stock-status out-of-stock">Out of Stock</span>
                        <% } %>
                    </td>
                    <td class="actions">
                        <a href="editProduct.jsp?productId=<%= product.getProductId() %>" class="btn edit-btn">Edit</a>
                        <a href="#" onclick="confirmDelete(<%= product.getProductId() %>)" class="btn delete-btn">Delete</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    
    <!-- Delete Confirmation Modal -->
    <div id="deleteModal" class="modal">
        <div class="modal-content">
            <h4>Confirm Deletion</h4>
            <p>Are you sure you want to delete this product? This action cannot be undone.</p>
            <div class="modal-buttons">
                <a href="#" onclick="closeModal()" class="btn cancel-btn">Cancel</a>
                <a href="#" id="confirmDeleteBtn" class="btn confirm-delete-btn">Delete</a>
            </div>
        </div>
    </div>
    
    <script>
        // Delete confirmation functionality
        function confirmDelete(productId) {
            const modal = document.getElementById('deleteModal');
            const confirmBtn = document.getElementById('confirmDeleteBtn');
            
            // Set the confirm button's href to the delete URL with the product ID
            confirmBtn.href = "deleteProduct.jsp?productId=" + productId;
            
            // Show the modal
            modal.style.display = "block";
        }
        
        function closeModal() {
            const modal = document.getElementById('deleteModal');
            modal.style.display = "none";
        }
        
        // Close modal if user clicks outside of it
        window.onclick = function(event) {
            const modal = document.getElementById('deleteModal');
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</div>
</body>
</html>
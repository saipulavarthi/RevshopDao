<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Entity.WishListItem" %>
<%@ page import="com.example.DAOO.WishListDAO" %>
<%@ page import="com.example.DAOO.ProductDAOImpl" %> <!-- Import ProductDAOImpl -->
<%@ page import="com.example.Entity.Product" %>
<%@ page import="com.example.Entity.Buyer" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Wishlist</title>
 <style>
    body {
    font-family: 'Roboto', sans-serif;
    margin: 0;
    padding: 0;
    background: linear-gradient(135deg, #f5af19, #f12711);
    display: flex;
    flex-direction: column;
    align-items: center;
    min-height: 100vh;
    color: #333;
    animation: fadeIn 1s ease-in-out;
}

@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

.navbar {
    background-color: #222;
    width: 100%;
    padding: 15px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: space-around;
    position: sticky;
    top: 0;
}

.navbar a {
    color: #f5f5f5;
    padding: 12px 18px;
    text-decoration: none;
    font-weight: bold;
    transition: all 0.3s ease;
    border-radius: 25px;
}

.navbar a:hover {
    background-color: #e67e22;
    transform: scale(1.05);
}

.container {
    max-width: 1100px;
    width: 100%;
    padding: 20px;
    margin: 20px;
    background-color: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

h2 {
    text-align: center;
    font-size: 2rem;
    color: #333;
    margin-bottom: 30px;
    position: relative;
}

h2::before {
    content: '⭐';
    position: absolute;
    left: 0;
    top: 0;
    transform: translateY(-100%);
    font-size: 1.5rem;
}

.products {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 30px;
}

.product {
    background-color: #fff;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
    text-align: center;
}

.product:hover {
    transform: translateY(-5px);
}

.product img {
    width: 250px; /* Fixed width */
    height: 250px; /* Fixed height */
    object-fit: cover; /* Ensures the image covers the box without distortion */
    border-radius: 10px;
    margin-bottom: 15px;
}

.product h3 {
    font-size: 1.5rem;
    color: #222;
    margin-bottom: 10px;
}

.product p {
    color: #555;
    margin-bottom: 15px;
}

button, .button {
    padding: 10px 20px;
    background-color: #27ae60;
    color: white;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover, .button:hover {
    background-color: #2ecc71;
}

button.remove {
    background-color: #e74c3c;
}

button.remove:hover {
    background-color: #c0392b;
}

.empty-cart {
    text-align: center;
    color: #555;
    font-size: 1.2rem;
    margin-top: 30px;
}

.checkout-button {
    padding: 12px 25px;
    background-color: #27ae60;
    color: #fff;
    font-weight: bold;
    text-align: center;
    text-decoration: none;
    border-radius: 25px;
    display: inline-block;
    transition: background-color 0.3s ease;
    cursor: pointer;
    margin-top: 20px;
}

.checkout-button:hover {
    background-color: #2ecc71;
}
</style>


</head>
<body>
    <div class="navbar">
         <a href="getProductsByCategory?category=Men">Men</a>
        <a href="getProductsByCategory?category=Women">Women</a>
        <a href="getProductsByCategory?category=Kid">Kids</a>
        <a href="index.html" class="logout-btn">Logout</a>
        <a href="cart.jsp" class="cart-btn">Cart</a>
        <a href="welcome.html" class="welcome-btn">Home</a>
    </div>
    
    <div class="container">
        <h2>Your Wishlist</h2>

        <div class="products">
            <%
                int buyerId = (int) session.getAttribute("buyerId"); // Ensure buyerId is stored in session during login
                WishListDAO wishListDAO = new WishListDAO();
                ProductDAOImpl productDAO = new ProductDAOImpl(); // Create an instance of ProductDAOImpl
                List<WishListItem> wishListItems = wishListDAO.getWishListForBuyer(buyerId); // Fetch wishlist items

                if (wishListItems != null && !wishListItems.isEmpty()) {
                    for (WishListItem wishListItem : wishListItems) {
                        Product product = productDAO.getProductById(wishListItem.getProductId()); // Use productDAO to retrieve product by ID
            %>
                <div class="product">
                    <h3><%= product.getProductName() %></h3>
                    <img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>" style="max-width:100%; height:auto;">
                    <p>Price: $<%= product.getPrice() %></p>
                    <p>Description: <%= product.getDescription() %></p>

                    <!-- Add to Cart Form -->
                    <form action="addToCart" method="post">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <input type="hidden" name="quantity" value="1"> <!-- Change as necessary -->
                        <button type="submit" class="button">Add to Cart</button>
                    </form>

                    <!-- Remove from Wishlist Form -->
                  <%--   <form action="${pageContext.request.contextPath}/removeFromWishList" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <input type="hidden" name="buyerId" value="<%= buyerId %>"> <!-- Assuming you have buyerId in scope -->
                        <button type="submit" class="button">Remove from Wishlist</button>
                    </form> --%>
                </div>
            <%
                    }
                } else {
            %>
                <p>No items in your wishlist.</p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>

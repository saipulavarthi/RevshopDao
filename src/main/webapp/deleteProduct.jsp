<%@ page import="com.example.DAOO.ProductDAOImpl" %>
<%@ page import="DBConnection.dbconnection" %>
<%
    // Get the product ID from the request parameter
    int productId = Integer.parseInt(request.getParameter("productId"));
Integer sellerId = (Integer) session.getAttribute("sellerId"); // get sellerId from session
    // Initialize ProductDAO
    ProductDAOImpl productDAO = new ProductDAOImpl();
    
    // Delete the product
    boolean deleted = false;
   /* boolean deleted = productDAO.removeProduct(productId,sellerId);
     */
     if(sellerId != null){
    	 deleted = productDAO.removeProduct(productId,sellerId);
     }
     
    // Redirect back to the seller dashboard with appropriate message
    if (deleted) {
        response.sendRedirect("SellerWelcome.jsp?success=deleted");
    } else {
        response.sendRedirect("SellerWelcome.jsp?error=deletefailed");
    }
%>
package com.Web.controler;

import java.io.IOException;
import java.sql.SQLException;

import com.example.DAOO.WishListDAO;
import com.example.Entity.Buyer;
import com.example.Entity.WishListItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addToWishList")
public class AddToWishListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //HttpSession session = request.getSession();
        //Buyer buyer = (Buyer) session.getAttribute("buyer");

       // int buyerId = buyer.getBuyerId();
    	 HttpSession session = request.getSession();
         Buyer buyer = (Buyer) session.getAttribute("buyer");
         int buyerId = buyer.getBuyerId();
         System.out.println("buyer id is" + buyerId);
        String productIdStr = request.getParameter("productId");
        System.out.println("Product Id is recived"+ productIdStr);

        // Parse product ID
        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID.");
            request.getRequestDispatcher("men.jsp").forward(request, response);
            return;
        }
       
        
        

        // Create WishListItem object
        WishListItem wishListItem = new WishListItem(productId, buyerId);
       

        // Call DAO to add the product to the wish list
        WishListDAO wishListDAO = new WishListDAO();
        boolean isAdded;
        try {
            isAdded = wishListDAO.addToWishList(wishListItem);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred.");
            request.getRequestDispatcher("men.jsp").forward(request, response);
            return;
        }

        if (!isAdded) {
            request.setAttribute("errorMessage", "Product is already in your wish list.");
        } else {
            request.setAttribute("successMessage", "Product added to your wish list.");
        }

        // Forward to the same page or wish list page
        request.getRequestDispatcher("men.jsp").forward(request, response);
    }
}

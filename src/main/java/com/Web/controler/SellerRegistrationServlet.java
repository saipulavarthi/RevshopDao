package com.Web.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.DAOO.SellerDAOImpl;
import com.example.Entity.Seller;

import DBConnection.dbconnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/Sellerregister")
public class SellerRegistrationServlet extends HttpServlet {
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String businessdetails = request.getParameter("businessdetails");
        System.out.print("name");
        

        Seller seller = new Seller();
        seller.setEmail(email);
        seller.setPassword(password);
        seller.setName(name);
        seller.setBusinessDetails(businessdetails);

        try (Connection connection = dbconnection.getConnection()) {
            if (connection != null) {
                SellerDAOImpl sellerDAO = new SellerDAOImpl();
                sellerDAO.registerSeller(seller);
                response.getWriter().println("Seller Added Successfully");
            } else {
                response.getWriter().println("Database connection failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("SQL Error occurred while adding buyer");
        } 
    }
	

}

package com.Web.controler;

import com.example.DAOO.ProductDAOImpl;
import com.example.Entity.Product;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/getProductsByCategory")
public class ProductCategoryServlet extends HttpServlet {
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String category = request.getParameter("category"); // Get the category from request parameter

	        ProductDAOImpl productDAO = new ProductDAOImpl();
	        List<Product> products = productDAO.getProductsByCategory(category);
			request.setAttribute("category", category);
			request.setAttribute("products", products);
			
			// Forward to the corresponding JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher(category.toLowerCase() + ".jsp");
			dispatcher.forward(request, response);
	    }
	}
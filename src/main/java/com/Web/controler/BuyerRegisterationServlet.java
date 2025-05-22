package com.Web.controler;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.example.DAOO.BuyerDAOImpl;
import com.example.Entity.Buyer;
import DBConnection.dbconnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Buyerregister")
public class BuyerRegisterationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        Buyer buyer = new Buyer();
        buyer.setEmail(email);
        buyer.setPassword(password);
        buyer.setName(name);

        try (Connection connection = dbconnection.getConnection()) {
            if (connection != null) {
                BuyerDAOImpl buyerDAO = new BuyerDAOImpl();
                buyerDAO.registerBuyer(buyer);
                response.getWriter().println("Buyer Added Successfully");
            } else {
                response.getWriter().println("Database connection failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("SQL Error occurred while adding buyer");
        } 
    }
}

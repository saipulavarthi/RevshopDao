package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
	 private static final String JDBC_URL = System.getenv("JDBC_URL");/*"jdbc:mysql://localhost:3306/re";*/
	    private static final String JDBC_USER = System.getenv("JDBC_USER");/*"root";*/
	    private static final String JDBC_PASSWORD = System.getenv("JDBC_PASSWORD");/*"root";*/
		//System.out.println("DB URL: " + System.getenv("JDBC_URL"));
//System.out.println("DB USER: " + System.getenv("JDBC_USER"));


	    public static Connection getConnection() {
	        Connection connection = null;
	        try {
	            // Load MySQL JDBC Driver
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            // Establish connection to the database
	            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	        } catch (ClassNotFoundException e) {
	            System.out.println("MySQL JDBC Driver not found. Include it in your library path");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("Connection failed. Check output console");
	            e.printStackTrace();
	        }
	        return connection;
	    }
	}


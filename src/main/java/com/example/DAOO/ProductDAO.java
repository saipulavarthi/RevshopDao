package com.example.DAOO;
import java.sql.SQLException;
import java.util.List;

import com.example.Entity.Product;

public interface ProductDAO {
	boolean addProduct(Product product) throws SQLException;


	List<Product> getProductsBySellerId(int sellerId);


	

}

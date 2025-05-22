package com.example.DAOO;
import java.sql.SQLException;

import com.example.Entity.Buyer;
import com.example.Entity.Seller;

public interface SellerDAO {
    boolean registerSeller(Seller seller) throws SQLException;
    Seller loginSeller(String email, String password) throws SQLException;
    Seller getSellerById(int sellerId) throws SQLException;

}

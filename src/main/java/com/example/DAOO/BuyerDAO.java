package com.example.DAOO;

import com.example.Entity.Buyer;
import java.sql.SQLException;

public interface BuyerDAO {
    boolean registerBuyer(Buyer buyer) throws SQLException;
    Buyer loginBuyer(String email, String password) throws SQLException;
    Buyer getBuyerById(int buyerId) throws SQLException;
}

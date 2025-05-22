package com.example.DAOO;

import java.util.List;

import com.example.Entity.CartItem;

public interface CartItemDAO {
    void addCartItem(CartItem cartItem);
    void updateCartItem(CartItem cartItem);
    void removeCartItem(int buyerId,int sellerId);
    List<CartItem> getCartItemsByBuyerId(int buyerId);
    void clearCart(int buyerId);
    public void updateCartItemQuantity(int productId, int buyerId, int newQuantity);
}

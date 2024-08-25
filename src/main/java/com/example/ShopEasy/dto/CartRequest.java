package com.example.ShopEasy.dto;



import java.util.List;

public class CartRequest {
    private Long userId;
    private List<CartItemRequest> cartItems;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemRequest> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }
}


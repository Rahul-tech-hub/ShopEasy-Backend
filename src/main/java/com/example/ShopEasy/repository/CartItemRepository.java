package com.example.ShopEasy.repository;

import com.example.ShopEasy.model.Cart;
import com.example.ShopEasy.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}


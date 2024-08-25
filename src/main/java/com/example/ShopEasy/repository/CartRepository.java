package com.example.ShopEasy.repository;


import com.example.ShopEasy.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart , Long> {


    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c JOIN c.cartItems ci WHERE ci.product.id = :productId")
    List<Cart> findByProductId(@Param("productId") Long productId);

    @Query("SELECT c FROM Cart c WHERE c.totalPrice > :amount")
    List<Cart> findCartsWithTotalPriceGreaterThan(@Param("amount") Double amount);

    @Query("SELECT c FROM Cart c WHERE c.createdDate BETWEEN :startDate AND :endDate")
    List<Cart> findCartsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Query("SELECT COUNT(c) FROM Cart c JOIN c.cartItems ci WHERE ci.product.id = :productId")
    Long countCartsByProductId(@Param("productId") Long productId);
}

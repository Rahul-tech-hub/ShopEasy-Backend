package com.example.ShopEasy.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CartItem> cartItems = new HashSet<>();


    @Column(name = "total_price")
    private Double totalPrice;


    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Cart(){
        this.createdDate = LocalDateTime.now();
    }
    //implementing parameterised constructor
    public Cart(Set<CartItem> cartItems, Double totalPrice, LocalDateTime createdDate){
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.createdDate = createdDate;
    }

    //implementing getters and setters

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public Set<CartItem> getCartItems(){
        return cartItems;
    }
    public void setCartItems(Set<CartItem> cartItems){
        this.cartItems = cartItems;
    }



    public Double getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }


    public LocalDateTime getCreatedDate(){
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate){
        this.createdDate = createdDate;
    }
}

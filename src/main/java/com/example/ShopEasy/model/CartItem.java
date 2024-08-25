package com.example.ShopEasy.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "price")
    private Double price;

    public CartItem(){
        //default constructor
    }

    public CartItem(Cart cart, Product product , Integer quantity, Double price){
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    //getters and setter
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }


    public Cart getCart(){
        return cart;
    }
    public void setCart(Cart cart){
        this.cart = cart;
    }


    public Product getProduct(){
        return product;
    }
    public void setProduct(Product product){
        this.product = product;
    }


    public Integer getQuantity(){
        return quantity;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }


    public Double getPrice(){
        return price;
    }
    public void setPrice(Double price){
        this.price = price;
    }


}

package com.example.ShopEasy.model;


import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private String category;

    @Column(nullable = true)
    private Double ratings;

    //getters and setters fro these fields


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Double getPrice(){
        return price;
    }
    public void setPrice(Double price){
        this.price = price;
    }


    public Integer getStockQuantity(){
        return stockQuantity;
    }
    public void setStockQuantity(Integer stockQuantity){
        this.stockQuantity = stockQuantity;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public double getRatings(){
        return ratings;
    }
    public void setRatings(double ratings){
        this.ratings = ratings;
    }


}

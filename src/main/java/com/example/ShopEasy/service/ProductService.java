package com.example.ShopEasy.service;


import com.example.ShopEasy.model.Product;
import com.example.ShopEasy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //to add new product\
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    //to get all product
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }


    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, Product updatedProduct){
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setRatings(updatedProduct.getRatings());
    }

}

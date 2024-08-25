package com.example.ShopEasy.controller;


import com.example.ShopEasy.model.Product;
import com.example.ShopEasy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product createdProduct = productService.addProduct(product);
        return new ResponseEntity<> (createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        if(products.isEmpty()){
            return new ResponseEntity<>("No products available.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<> (products, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        Optional<Product> product = Optional.ofNullable(productService.getProductById(id));
        if(product.isPresent()){
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found with id: "+id, HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        Optional<Product> productOptional = Optional.ofNullable(productService.getProductById(id));


        if(productOptional.isPresent()){
            productService.updateProduct(id, updatedProduct);
            return new ResponseEntity<>("Product updated Successfully.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found with id: "+id, HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Optional<Product> product = Optional.ofNullable(productService.getProductById(id));
        if(product.isPresent()){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully. ", HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("Product not found. No action taken.", HttpStatus.NOT_FOUND);
        }

    }


}

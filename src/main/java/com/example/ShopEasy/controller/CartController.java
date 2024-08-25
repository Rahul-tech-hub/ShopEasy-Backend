package com.example.ShopEasy.controller;


import com.example.ShopEasy.dto.CartItemRequest;
import com.example.ShopEasy.dto.CartRequest;
import com.example.ShopEasy.dto.DateRangeRequest;
import com.example.ShopEasy.exception.CartItemNotFoundException;
import com.example.ShopEasy.exception.CartNotFoundException;
import com.example.ShopEasy.model.ApiResponse;
import com.example.ShopEasy.model.Cart;
import com.example.ShopEasy.model.CartItem;
import com.example.ShopEasy.model.Product;
import com.example.ShopEasy.service.CartService;
import com.example.ShopEasy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCart(@RequestBody CartRequest cartRequest) {
        try {
            Cart newCart = cartService.createCart(cartRequest.getUserId(), cartRequest.getCartItems());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Cart created successfully", newCart));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error creating cart", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCartById(id);
            return ResponseEntity.ok(new ApiResponse("Cart retrieved successfully", cart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Cart not found", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving cart", e.getMessage()));
        }
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse> addCartItem(@PathVariable Long cartId, @RequestBody CartItemRequest cartItemRequest) {
        try {
            Product product = productService.getProductById(cartItemRequest.getProductId());
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());

            Cart updatedCart = cartService.addCartItem(cartId, cartItem);
            return ResponseEntity.ok(new ApiResponse("Cart item added successfully", updatedCart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Cart not found", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error adding cart item", e.getMessage()));
        }
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        try {
            Cart updatedCart = cartService.removeItemFromCart(cartId, cartItemId);
            return ResponseEntity.ok(new ApiResponse("Cart item removed successfully", updatedCart));
        } catch (CartItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Cart item not found", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Cart item not found", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error removing cart item", e.getMessage()));
        }
    }

    @PostMapping("/date-range")
    public ResponseEntity<ApiResponse> findCartsByDateRange(@RequestBody DateRangeRequest dateRangeRequest) {
        try {
            LocalDateTime startDate = dateRangeRequest.getStartDate();
            LocalDateTime endDate = dateRangeRequest.getEndDate();
            List<Cart> carts = cartService.findCartsByDateRange(startDate, endDate);
            return ResponseEntity.ok(new ApiResponse("Carts retrieved successfully", carts));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error finding carts by date range", e.getMessage()));
        }
    }


    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable Long cartId) {
        try {
            String message = cartService.deleteCart(cartId);
            return ResponseEntity.ok(new ApiResponse(message));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Cart not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error deleting cart", null));
        }
    }
}
package com.example.ShopEasy.service;


import com.example.ShopEasy.dto.CartItemRequest;
import com.example.ShopEasy.exception.CartItemNotFoundException;
import com.example.ShopEasy.exception.CartNotFoundException;
import com.example.ShopEasy.exception.ProductNotFoundException;
import com.example.ShopEasy.exception.UserNotFoundException;
import com.example.ShopEasy.model.Cart;
import com.example.ShopEasy.model.CartItem;
import com.example.ShopEasy.model.Product;
import com.example.ShopEasy.model.User;
import com.example.ShopEasy.repository.CartItemRepository;
import com.example.ShopEasy.repository.CartRepository;
import com.example.ShopEasy.repository.ProductRepository;
import com.example.ShopEasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Set;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    public Cart createCart(Long userId, List<CartItemRequest> cartItems) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)); // Updated exception

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        cart.setCreatedDate(LocalDateTime.now());

        if (cartItems != null) {
            for (CartItemRequest itemRequest : cartItems) {
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + itemRequest.getProductId()));

                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(itemRequest.getQuantity());
                cartItem.setCart(cart);
                cartItem.setPrice(product.getPrice() * itemRequest.getQuantity());

                cart.getCartItems().add(cartItem);
                cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
            }
        }

        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + id));
    }

    public Cart addCartItem(Long cartId, CartItem cartItem) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + cartItem.getProduct().getId()));

        cartItem.setCart(cart);
        cartItem.setPrice(product.getPrice());

        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(calculateTotalPrice(cartId));

        return cartRepository.save(cart);
    }

    public Cart updateCartItem(Long cartId, Long cartItemId, CartItem updatedCartItem) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        CartItem itemToUpdate = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem with ID: " + cartItemId + " not found in cart"));

        itemToUpdate.setQuantity(updatedCartItem.getQuantity());
        itemToUpdate.setPrice(updatedCartItem.getProduct().getPrice() * updatedCartItem.getQuantity());

        cart.setTotalPrice(calculateTotalPrice(cartId));

        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.getCartItems().remove(cartItem);

        // Recalculate total price
        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);
        return cart;
    }

    public Double calculateTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public List<Cart> findCartsByProduct(Long productId) {
        return cartRepository.findByProductId(productId);
    }

    public List<Cart> findCartsWithTotalPriceGreaterThan(Double amount) {
        return cartRepository.findCartsWithTotalPriceGreaterThan(amount);
    }

    public Long countCartsByProduct(Long productId) {
        return cartRepository.countCartsByProductId(productId);
    }

    public List<Cart> findCartsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return cartRepository.findCartsByDateRange(startDate, endDate);
    }

    public String deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        cartRepository.delete(cart);
        return "Cart with id " + cartId + " has been successfully deleted.";
    }
}
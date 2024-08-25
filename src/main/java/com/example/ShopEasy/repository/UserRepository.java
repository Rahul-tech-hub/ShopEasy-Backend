package com.example.ShopEasy.repository;

import com.example.ShopEasy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method to find a user by email
    User findByEmail(String email);
    Optional<User> findByUsername(String username);
}

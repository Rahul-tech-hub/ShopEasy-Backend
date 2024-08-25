package com.example.ShopEasy.service;

import com.example.ShopEasy.model.User;
import com.example.ShopEasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User registerUser(String username, String email, String plainPassword) {
        String encodedPassword = passwordEncoder.encode(plainPassword);
        System.out.println("Encoded Password: " + encodedPassword);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // User authenticated successfully
        }
        return null; // Authentication failed
    }
}

package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for User business operations.
 * Demonstrates constructor-based dependency injection.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Constructor-based dependency injection
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User createUser(User user) {
        // Validate user data
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be empty");
        }

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Validate updated data
        if (user.getName() != null && !user.getName().trim().isEmpty()) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            // Check if email is being changed and if new email already exists
            if (!existingUser.getEmail().equals(user.getEmail())) {
                if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                    throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
                }
                existingUser.setEmail(user.getEmail());
            }
        }
        if (user.getRole() != null && !user.getRole().trim().isEmpty()) {
            existingUser.setRole(user.getRole());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        return userRepository.deleteById(id);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}

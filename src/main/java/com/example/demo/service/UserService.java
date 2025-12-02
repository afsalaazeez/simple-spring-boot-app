package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for User business operations.
 */
public interface UserService {

    /**
     * Get all users.
     *
     * @return List of all users
     */
    List<User> getAllUsers();

    /**
     * Get user by ID.
     *
     * @param id User ID
     * @return Optional containing the user if found
     */
    Optional<User> getUserById(Long id);

    /**
     * Get user by email.
     *
     * @param email User email
     * @return Optional containing the user if found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Get users by role.
     *
     * @param role User role
     * @return List of users with the specified role
     */
    List<User> getUsersByRole(String role);

    /**
     * Create a new user.
     *
     * @param user User to create
     * @return Created user
     * @throws IllegalArgumentException if user with email already exists
     */
    User createUser(User user);

    /**
     * Update an existing user.
     *
     * @param id   User ID
     * @param user Updated user data
     * @return Updated user
     * @throws IllegalArgumentException if user not found
     */
    User updateUser(Long id, User user);

    /**
     * Delete a user.
     *
     * @param id User ID
     * @return true if deleted
     * @throws IllegalArgumentException if user not found
     */
    boolean deleteUser(Long id);

    /**
     * Get total count of users.
     *
     * @return Number of users
     */
    long getUserCount();
}

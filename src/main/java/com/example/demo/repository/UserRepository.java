package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository for managing User entities with in-memory storage.
 */
@Repository
public class UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserRepository() {
        // Initialize with some sample data
        save(new User("Alice Johnson", "alice@example.com", "ADMIN"));
        save(new User("Bob Smith", "bob@example.com", "USER"));
        save(new User("Charlie Brown", "charlie@example.com", "USER"));
    }

    /**
     * Find all users.
     *
     * @return List of all users
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Find user by ID.
     *
     * @param id User ID
     * @return Optional containing the user if found
     */
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Find user by email.
     *
     * @param email User email
     * @return Optional containing the user if found
     */
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Find users by role.
     *
     * @param role User role
     * @return List of users with the specified role
     */
    public List<User> findByRole(String role) {
        return users.values().stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .toList();
    }

    /**
     * Save or update a user.
     *
     * @param user User to save
     * @return Saved user with ID assigned
     */
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Delete user by ID.
     *
     * @param id User ID
     * @return true if deleted, false if not found
     */
    public boolean deleteById(Long id) {
        return users.remove(id) != null;
    }

    /**
     * Check if user exists by ID.
     *
     * @param id User ID
     * @return true if exists
     */
    public boolean existsById(Long id) {
        return users.containsKey(id);
    }

    /**
     * Get total count of users.
     *
     * @return Number of users
     */
    public long count() {
        return users.size();
    }

    /**
     * Find all active users (both USER and ADMIN roles).
     * This method demonstrates an internal call to findByRole().
     *
     * @return List of all active users
     */
    public List<User> findAllActiveUsers() {
        List<User> activeUsers = new ArrayList<>();
        // Internal call to findByRole for USER role
        activeUsers.addAll(findByRole("USER"));
        // Internal call to findByRole for ADMIN role
        activeUsers.addAll(findByRole("ADMIN"));
        return activeUsers;
    }

    /**
     * Save user with existence check.
     * This method demonstrates an internal call to existsById().
     *
     * @param user        User to save
     * @param allowUpdate Whether to allow updating existing user
     * @return Saved user
     * @throws IllegalStateException if user exists and allowUpdate is false
     */
    public User saveWithValidation(User user, boolean allowUpdate) {
        if (user.getId() != null && existsById(user.getId()) && !allowUpdate) {
            throw new IllegalStateException("User already exists with id: " + user.getId());
        }
        // Internal call to save method
        return save(user);
    }

    /**
     * Delete user by email.
     * This method demonstrates internal calls to findByEmail() and deleteById().
     *
     * @param email User email
     * @return true if deleted, false if not found
     */
    public boolean deleteByEmail(String email) {
        // Internal call to findByEmail
        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            // Internal call to deleteById
            return deleteById(user.get().getId());
        }
        return false;
    }
}

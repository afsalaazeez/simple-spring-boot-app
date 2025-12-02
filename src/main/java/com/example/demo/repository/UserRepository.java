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
}

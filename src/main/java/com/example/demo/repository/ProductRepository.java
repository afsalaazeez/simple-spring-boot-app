package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository for managing Product entities with in-memory storage.
 */
@Repository
public class ProductRepository {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductRepository() {
        // Initialize with some sample data
        save(new Product("Laptop", "High-performance laptop", new BigDecimal("999.99"), 15));
        save(new Product("Mouse", "Wireless mouse", new BigDecimal("29.99"), 50));
        save(new Product("Keyboard", "Mechanical keyboard", new BigDecimal("89.99"), 30));
        save(new Product("Monitor", "27-inch 4K monitor", new BigDecimal("399.99"), 20));
        save(new Product("Headphones", "Noise-canceling headphones", new BigDecimal("199.99"), 25));
    }

    /**
     * Find all products.
     *
     * @return List of all products
     */
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    /**
     * Find product by ID.
     *
     * @param id Product ID
     * @return Optional containing the product if found
     */
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Find products by name (case-insensitive partial match).
     *
     * @param name Product name to search
     * @return List of matching products
     */
    public List<Product> findByNameContaining(String name) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Find products within a price range.
     *
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of products in the price range
     */
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return products.values().stream()
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0
                        && product.getPrice().compareTo(maxPrice) <= 0)
                .toList();
    }

    /**
     * Find products that are in stock.
     *
     * @return List of products with stock > 0
     */
    public List<Product> findInStock() {
        return products.values().stream()
                .filter(Product::isInStock)
                .toList();
    }

    /**
     * Find products that are out of stock.
     *
     * @return List of products with stock = 0
     */
    public List<Product> findOutOfStock() {
        return products.values().stream()
                .filter(product -> !product.isInStock())
                .toList();
    }

    /**
     * Save or update a product.
     *
     * @param product Product to save
     * @return Saved product with ID assigned
     */
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
        }
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Delete product by ID.
     *
     * @param id Product ID
     * @return true if deleted, false if not found
     */
    public boolean deleteById(Long id) {
        return products.remove(id) != null;
    }

    /**
     * Check if product exists by ID.
     *
     * @param id Product ID
     * @return true if exists
     */
    public boolean existsById(Long id) {
        return products.containsKey(id);
    }

    /**
     * Get total count of products.
     *
     * @return Number of products
     */
    public long count() {
        return products.size();
    }
}

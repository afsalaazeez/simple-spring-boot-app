package com.example.demo.service;

import com.example.demo.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Product business operations.
 */
public interface ProductService {

    /**
     * Get all products.
     *
     * @return List of all products
     */
    List<Product> getAllProducts();

    /**
     * Get product by ID.
     *
     * @param id Product ID
     * @return Optional containing the product if found
     */
    Optional<Product> getProductById(Long id);

    /**
     * Search products by name.
     *
     * @param name Product name to search
     * @return List of matching products
     */
    List<Product> searchProductsByName(String name);

    /**
     * Get products within a price range.
     *
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of products in the price range
     */
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Get products that are in stock.
     *
     * @return List of products with stock > 0
     */
    List<Product> getInStockProducts();

    /**
     * Create a new product.
     *
     * @param product Product to create
     * @return Created product
     */
    Product createProduct(Product product);

    /**
     * Update an existing product.
     *
     * @param id      Product ID
     * @param product Updated product data
     * @return Updated product
     * @throws IllegalArgumentException if product not found
     */
    Product updateProduct(Long id, Product product);

    /**
     * Delete a product.
     *
     * @param id Product ID
     * @return true if deleted
     * @throws IllegalArgumentException if product not found
     */
    boolean deleteProduct(Long id);

    /**
     * Update product stock.
     *
     * @param id       Product ID
     * @param quantity Quantity to add (positive) or remove (negative)
     * @return Updated product
     * @throws IllegalArgumentException if product not found or insufficient stock
     */
    Product updateStock(Long id, int quantity);

    /**
     * Get total count of products.
     *
     * @return Number of products
     */
    long getProductCount();
}

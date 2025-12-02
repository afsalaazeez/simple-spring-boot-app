package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for Product business operations.
 * Demonstrates constructor-based dependency injection.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor-based dependency injection
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null) {
            minPrice = BigDecimal.ZERO;
        }
        if (maxPrice == null) {
            maxPrice = new BigDecimal("999999.99");
        }
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Min price cannot be greater than max price");
        }
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Product> getInStockProducts() {
        return productRepository.findInStock();
    }

    @Override
    public Product createProduct(Product product) {
        // Validate product data
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be a positive value");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        // Validate and update fields
        if (product.getName() != null && !product.getName().trim().isEmpty()) {
            existingProduct.setName(product.getName());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Product price must be a positive value");
            }
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getStock() != null) {
            if (product.getStock() < 0) {
                throw new IllegalArgumentException("Product stock cannot be negative");
            }
            existingProduct.setStock(product.getStock());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        return productRepository.deleteById(id);
    }

    @Override
    public Product updateStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        if (quantity > 0) {
            product.increaseStock(quantity);
        } else if (quantity < 0) {
            product.decreaseStock(Math.abs(quantity));
        }

        return productRepository.save(product);
    }

    @Override
    public long getProductCount() {
        return productRepository.count();
    }
}

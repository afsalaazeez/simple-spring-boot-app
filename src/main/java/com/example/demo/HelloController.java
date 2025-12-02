package com.example.demo;

import com.example.demo.service.UserService;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Main controller demonstrating dependency injection.
 * Services are injected via constructor injection.
 */
@RestController
public class HelloController {

    private final UserService userService;
    private final ProductService productService;

    // Constructor-based dependency injection
    public HelloController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/api/hello")
    public String apiHello() {
        return "Welcome to Spring Boot REST API";
    }

    /**
     * New endpoint demonstrating use of injected services.
     * Returns statistics about users and products.
     */
    @GetMapping("/api/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getUserCount());
        stats.put("totalProducts", productService.getProductCount());
        stats.put("productsInStock", productService.getInStockProducts().size());
        stats.put("message", "Application statistics retrieved successfully");
        return stats;
    }
}

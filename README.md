# Spring Boot Application - User & Product Management API

A Spring Boot REST API demonstrating clean layered architecture with dependency injection, featuring user and product management with in-memory storage.

## Overview

This application showcases a complete Spring Boot implementation with:
- **Model Layer**: User and Product domain objects
- **Repository Layer**: In-memory data storage with CRUD operations
- **Service Layer**: Business logic with validation
- **Controller Layer**: RESTful endpoints with proper error handling
- **Configuration**: CORS and application settings

All components use **constructor-based dependency injection** following Spring Boot best practices.

## Architecture

### Layered Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                        REST CLIENT                               │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    CONTROLLER LAYER                              │
├─────────────────────────────────────────────────────────────────┤
│  • HelloController    - Basic endpoints + stats                 │
│  • UserController     - User CRUD operations                     │
│  • ProductController  - Product CRUD operations                  │
│                                                                   │
│  All use @RestController + Constructor Injection                │
└────────────────────────┬────────────────────────────────────────┘
                         │ (Dependency Injection)
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                                │
├─────────────────────────────────────────────────────────────────┤
│  • UserServiceImpl    - User business logic & validation         │
│  • ProductServiceImpl - Product business logic & validation      │
│                                                                   │
│  All use @Service + Constructor Injection                       │
└────────────────────────┬────────────────────────────────────────┘
                         │ (Dependency Injection)
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                   REPOSITORY LAYER                               │
├─────────────────────────────────────────────────────────────────┤
│  • UserRepository    - User data access (in-memory)              │
│  • ProductRepository - Product data access (in-memory)           │
│                                                                   │
│  All use @Repository + ConcurrentHashMap                        │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                     DOMAIN MODELS                                │
├─────────────────────────────────────────────────────────────────┤
│  • User    - id, name, email, role                               │
│  • Product - id, name, description, price, stock                 │
└─────────────────────────────────────────────────────────────────┘
```

### Dependency Injection Flow

```
HelloController
    ├── injects → UserServiceImpl → UserRepository → User Model
    └── injects → ProductServiceImpl → ProductRepository → Product Model

UserController
    └── injects → UserServiceImpl → UserRepository → User Model

ProductController
    └── injects → ProductServiceImpl → ProductRepository → Product Model
```

### Key Design Patterns

- **Constructor-based Dependency Injection** - All dependencies injected via constructors
- **Interface-based Services** - Services implement interfaces for loose coupling
- **Repository Pattern** - Data access abstraction with in-memory storage
- **Layered Architecture** - Clear separation between controllers, services, and repositories

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Running the Application

1. **Build the project:**
   ```bash
   mvn clean compile
   ```

2. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application:**
   - Base URL: `http://localhost:8080`
   - API endpoints: `http://localhost:8080/api/*`

## API Endpoints

### Basic Endpoints

- `GET /` - Returns "Hello, Spring Boot!"
- `GET /api/hello` - Returns "Welcome to Spring Boot REST API"
- `GET /api/stats` - Returns application statistics (demonstrates dependency injection)

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/search/email?email={email}` | Search by email |
| GET | `/api/users/search/role?role={role}` | Filter by role |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Product Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?name={name}` | Search by name |
| GET | `/api/products/search?minPrice={min}&maxPrice={max}` | Price range search |
| GET | `/api/products/instock` | Get in-stock products |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| PATCH | `/api/products/{id}/stock?quantity={qty}` | Update stock |
| DELETE | `/api/products/{id}` | Delete product |

## Example Usage

### Get Statistics
```bash
curl http://localhost:8080/api/stats
```

Response:
```json
{
  "totalUsers": 3,
  "totalProducts": 5,
  "message": "Application statistics retrieved successfully",
  "productsInStock": 5
}
```

### Get All Users
```bash
curl http://localhost:8080/api/users
```

Response:
```json
[
  {
    "id": 1,
    "name": "Alice Johnson",
    "email": "alice@example.com",
    "role": "ADMIN"
  },
  {
    "id": 2,
    "name": "Bob Smith",
    "email": "bob@example.com",
    "role": "USER"
  },
  {
    "id": 3,
    "name": "Charlie Brown",
    "email": "charlie@example.com",
    "role": "USER"
  }
]
```

### Create New User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","role":"USER"}'
```

### Get Products in Price Range
```bash
curl "http://localhost:8080/api/products/search?minPrice=100&maxPrice=500"
```

## Features

✅ **Layered Architecture** - Clear separation of concerns  
✅ **Constructor Injection** - Immutable dependencies, easier testing  
✅ **Interface-Based Services** - Loose coupling, flexibility  
✅ **Thread-Safe Repositories** - ConcurrentHashMap for concurrent access  
✅ **Business Validation** - Email uniqueness, price validation, stock management  
✅ **Proper Error Handling** - Meaningful error messages with appropriate HTTP status codes  
✅ **RESTful Design** - Standard HTTP methods and status codes  
✅ **Sample Data** - Pre-populated data for immediate testing  
✅ **CORS Configuration** - Ready for frontend integration  

## Project Structure

```
src/main/java/com/example/demo/
├── DemoApplication.java          # Main application class
├── HelloController.java          # Basic controller with stats endpoint
├── config/
│   └── AppConfig.java           # Application configuration (CORS)
├── controller/
│   ├── UserController.java      # User REST endpoints
│   └── ProductController.java   # Product REST endpoints
├── model/
│   ├── User.java               # User domain model
│   └── Product.java            # Product domain model
├── repository/
│   ├── UserRepository.java     # User data access layer
│   └── ProductRepository.java  # Product data access layer
└── service/
    ├── UserService.java        # User service interface
    ├── UserServiceImpl.java    # User service implementation
    ├── ProductService.java     # Product service interface
    └── ProductServiceImpl.java # Product service implementation
```

## Dependency Injection Flow

The application demonstrates **constructor-based dependency injection** throughout:

### Controller → Service
- `HelloController` injects `UserService` and `ProductService`
- `UserController` injects `UserService`
- `ProductController` injects `ProductService`

### Service → Repository
- `UserServiceImpl` injects `UserRepository`
- `ProductServiceImpl` injects `ProductRepository`

All dependencies are resolved automatically by Spring's IoC container at startup.

## Sample Data

The application comes with pre-populated sample data:

**Users (3):**
- Alice Johnson (ADMIN)
- Bob Smith (USER)
- Charlie Brown (USER)

**Products (5):**
- Laptop ($999.99)
- Mouse ($29.99)
- Keyboard ($89.99)
- Monitor ($399.99)
- Headphones ($199.99)

## Technologies Used

- **Spring Boot 3.2.0**
- **Java 17**
- **Maven**
- **Spring Web** - REST API support
- **Spring Boot Starter Test** - Testing framework

## License

This is a sample project for learning and demonstration purposes.

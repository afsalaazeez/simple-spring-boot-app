# Simple Spring Boot Application

A simple Spring Boot Maven project with a REST controller.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Running the Application

1. Build the project:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

   Or run the JAR file:
   ```bash
   java -jar target/simple-spring-boot-app-1.0.0.jar
   ```

## Endpoints

- `GET /` - Returns "Hello, Spring Boot!"
- `GET /api/hello` - Returns "Welcome to Spring Boot REST API"

The application will start on `http://localhost:8080`



# Warehouse Manager

This project serves as a test run for utilizing JWT token based authentication and TDD


## Tech Stack
 **Core:** 

Java 17, Spring Boot 3, Spring Security, Spring Data JPA, 
Hibernate, PostgreSQL

**Extra Dependencies:**

Lombok, BCrypt, OpenAPI (Swagger), Apache Commons Validator





## Features

- User Authentication - based on JWT token
- Product Database - authenticated users are able to add new stock to the product database.


## API Documentation

You can find all available endpoints using Swagger. After running the application, access the Swagger UI at http://localhost:8080/swagger-ui.html

**Product Controller**

```bash
    POST /api/v1/products - Add a new product
```
```bash
    GET /api/v1/products - Fetch all products from the database
```
```bash
    DELETE /api/v1/products/{id} - Delete products by their id
```
```bash
     PUT /api/v1/products/{id} - Update products by their id
```

**Auth Controller**

```bash
    POST /api/v1/auth/register - Register a new user
```
```bash
    POST /api/v1/auth/authenticate - Authenticate user (log in)
```






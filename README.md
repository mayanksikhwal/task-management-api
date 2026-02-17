# task-management-api

## Task Management REST API

 Production-ready RESTful API with JWT authentication built with Spring Boot.

## Features
-  JWT Authentication & Authorization
-  User Registration & Login
-  Complete Task CRUD Operations
-  Filter by Status, Search, Statistics
-  Secure endpoints with token validation
-  MySQL Database Integration

## Tech Stack
Java 17 | Spring Boot 4.0.2 | Spring Security | JWT | MySQL | JPA/Hibernate | Maven

## Quick Start
```bash
# Clone
git clone https://github.com/mayanksikhwal/task-management-api.git

# Configure MySQL in application.properties
# Create database: CREATE DATABASE taskdb;

# Run
mvn spring-boot:run
```

## API Endpoints

### Authentication (Public)
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login (returns JWT token)

### Tasks (Protected - Requires JWT)
- `POST /api/tasks` - Create task
- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task
- `GET /api/tasks/status/{status}` - Filter by status
- `GET /api/tasks/search?keyword={text}` - Search
- `GET /api/tasks/statistics` - Dashboard stats

## Usage Example

### 1. Login
```
POST /api/auth/login
Body: {"username": "user", "password": "pass"}
Response: {"token": "eyJhbG..."}
```

### 2. Use Token
```
GET /api/tasks
Headers: Authorization: Bearer eyJhbG...
```

## Project Status
🚀 **90% Complete** - Production-ready with JWT security

## Author
**Mayank Sikhwal**
- GitHub: [@mayanksikhwal](https://github.com/mayanksikhwal)
- LinkedIn: [Mayank Sikhwal](https://linkedin.com/in/mayanksikhwal)

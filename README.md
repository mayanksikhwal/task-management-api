# Task Management REST API

Production-ready RESTful API for task management with JWT authentication and interactive Swagger documentation.

## 🚀 Features

- **JWT Authentication** - Secure token-based authentication with BCrypt password encryption
- **Complete CRUD Operations** - Create, read, update, delete tasks
- **Advanced Filtering** - Filter by status, search by keyword, get statistics
- **Input Validation** - Comprehensive request validation with custom error messages
- **Exception Handling** - Professional error responses with proper HTTP status codes
- **Interactive Documentation** - Auto-generated Swagger UI for API testing
- **Security** - Stateless authentication, password hashing, protected endpoints

## 🛠️ Tech Stack

- **Backend:** Java 17, Spring Boot 3.2.2
- **Security:** Spring Security, JWT (JSON Web Tokens)
- **Database:** MySQL 8.0
- **ORM:** Spring Data JPA (Hibernate)
- **Documentation:** SpringDoc OpenAPI (Swagger)
- **Build Tool:** Maven
- **Password Encryption:** BCrypt

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0
- Maven 3.6+

## ⚡ Quick Start

### 1. Clone the repository
```bash
git clone https://github.com/mayanksikhwal/task-management-api.git
cd task-management-api
```

### 2. Configure MySQL
```sql
CREATE DATABASE taskdb;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build and run
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

Application runs on: **http://localhost:8080**

## 📚 API Documentation

**Swagger UI:** http://localhost:8080/swagger-ui.html

**OpenAPI JSON:** http://localhost:8080/v3/api-docs

### How to use Swagger:
1. Navigate to `/swagger-ui.html`
2. Test login endpoint and copy JWT token
3. Click **"Authorize"** button (🔒)
4. Enter: `Bearer YOUR_TOKEN`
5. Test all endpoints interactively!

## 🔐 Authentication Endpoints (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and receive JWT token |

## 📝 Task Endpoints (Protected - Requires JWT)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create new task |
| GET | `/api/tasks` | Get all tasks for authenticated user |
| GET | `/api/tasks/{id}` | Get specific task |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| PATCH | `/api/tasks/{id}/status` | Update task status |
| GET | `/api/tasks/status/{status}` | Filter tasks by status (TODO, IN_PROGRESS, COMPLETED) |
| GET | `/api/tasks/search?keyword={text}` | Search tasks by keyword |
| GET | `/api/tasks/statistics` | Get task statistics (total, by status) |

## 📊 Database Schema

### Users Table
```sql
- id (PK)
- username (UNIQUE)
- email (UNIQUE)
- password (BCrypt hashed)
- created_at
```

### Tasks Table
```sql
- id (PK)
- title
- description
- status (TODO, IN_PROGRESS, COMPLETED)
- priority (LOW, MEDIUM, HIGH)
- due_date
- user_id (FK)
- created_at
- updated_at
```

## 💡 Usage Examples

### Register a user
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "john",
  "email": "john@example.com",
  "password": "password123"
}
```

### Login
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "password123"
}

# Response includes JWT token
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 1,
  "username": "john",
  "email": "john@example.com"
}
```

### Create a task (with JWT)
```bash
POST http://localhost:8080/api/tasks
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "title": "Complete project documentation",
  "description": "Write comprehensive README",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "dueDate": "2026-02-25T18:00:00"
}
```

### Get all tasks
```bash
GET http://localhost:8080/api/tasks
Authorization: Bearer YOUR_JWT_TOKEN
```

## 🔒 Security Features

- **Password Encryption:** BCrypt with salt
- **JWT Tokens:** 24-hour validity
- **Stateless Sessions:** No server-side session storage
- **Protected Endpoints:** All task operations require valid JWT
- **Input Validation:** Request data validation with error messages
- **Custom Exception Handling:** Professional error responses

## 🏗️ Project Structure
```
src/main/java/com/mayank/taskmanagement/
├── config/          # Security & Swagger configuration
├── controller/      # REST API endpoints
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── enums/          # Task status & priority enums
├── exception/      # Custom exceptions & global handler
├── filter/         # JWT authentication filter
├── repository/     # JPA repositories
├── service/        # Business logic
└── util/           # JWT utility class
```

## 📈 Project Status

**Current Version:** 1.0 (Production Ready)

**Completion:** 98%

**What's included:**
-  Complete CRUD functionality
-  JWT authentication
-  Password encryption
-  Input validation
-  Exception handling
-  Swagger documentation
-  MySQL integration

## 👨‍💻 Author

**Mayank Sikhwal**
- GitHub: [@mayanksikhwal](https://github.com/mayanksikhwal)
- LinkedIn: [Mayank Sikhwal](https://linkedin.com/in/mayanksikhwal)
- Email: sikhwalmayank251@gmail.com

## 📝 License

This project is open source and available for learning purposes.

## 🙏 Acknowledgments

Built with Spring Boot, secured with JWT, documented with Swagger.

---

**⭐ If you found this project helpful, please star this repository!**
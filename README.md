# Task Management REST API

A production-ready RESTful API for task management featuring JWT authentication, BCrypt encryption, interactive Swagger documentation, and Docker containerization.

## 🚀 Live Demo

- **API Base URL:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **API Documentation:** `http://localhost:8080/v3/api-docs`

## ✨ Key Features

- **🔐 JWT Authentication** - Secure token-based authentication with 15 mins token validity
- **🔒 Password Encryption** - BCrypt hashing with salt for secure password storage
- **📝 Complete CRUD Operations** - Create, read, update, delete tasks with full validation
- **🔍 Advanced Filtering** - Filter by status, search by keyword, get statistics
- **✅ Input Validation** - Comprehensive request validation with custom error messages
- **🚨 Exception Handling** - Professional error responses with proper HTTP status codes
- **📚 Interactive Documentation** - Auto-generated Swagger UI for API testing
- **🐳 Docker Ready** - One-command deployment with docker-compose
- **🔄 Stateless Architecture** - No server-side sessions, fully scalable

## 🛠️ Tech Stack

| Category | Technologies |
|----------|-------------|
| **Backend** | Java 17, Spring Boot 3.2.2 |
| **Security** | Spring Security, JWT, BCrypt |
| **Database** | MySQL 8.0 |
| **ORM** | Spring Data JPA (Hibernate) |
| **Documentation** | SpringDoc OpenAPI 2.3.0 (Swagger) |
| **Containerization** | Docker, Docker Compose |
| **Build Tool** | Maven 3.6+ |
| **Utilities** | Lombok |

## 📋 Prerequisites

**Option 1: Docker (Recommended)**
- Docker Desktop
- Git

**Option 2: Manual Setup**
- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+
- Git

## 🚀 Quick Start

### Method 1: Docker Deployment (Recommended)
```bash
# Clone repository
git clone https://github.com/mayanksikhwal/task-management-api.git
cd task-management-api

# Build JAR file
mvn clean package -DskipTests

# Start with Docker Compose (one command!)
docker-compose up --build
```

**That's it!** 🎉

- **Application:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **MySQL:** localhost:3306 (auto-configured)

### Method 2: Manual Setup

**1. Clone and navigate:**
```bash
git clone https://github.com/mayanksikhwal/task-management-api.git
cd task-management-api
```

**2. Configure MySQL:**
```sql
CREATE DATABASE taskdb;
```

**3. Update application.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

**4. Run application:**
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

**Application runs on:** http://localhost:8080

## 📚 API Documentation

### Swagger UI (Interactive)
Navigate to: **http://localhost:8080/swagger-ui.html**

**How to use:**
1. Go to Swagger UI
2. Click "POST /api/auth/login"
3. Click "Try it out"
4. Enter credentials and execute
5. Copy the JWT token from response
6. Click "🔒 Authorize" button (top right)
7. Enter: `Bearer YOUR_TOKEN`
8. Click "Authorize" → "Close"
9. Now test all protected endpoints!

### API Endpoints

#### Authentication (Public)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | Login and receive JWT token | ❌ |

**Register Request:**
```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "password123"
}
```

**Login Request:**
```json
{
  "username": "john",
  "password": "password123"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 1,
  "username": "john",
  "email": "john@example.com",
  "message": "Login successful"
}
```

#### Tasks (Protected - Requires JWT)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create new task |
| GET | `/api/tasks` | Get all tasks for authenticated user |
| GET | `/api/tasks/{id}` | Get specific task |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| PATCH | `/api/tasks/{id}/status` | Update task status |
| GET | `/api/tasks/status/{status}` | Filter by status (TODO, IN_PROGRESS, COMPLETED) |
| GET | `/api/tasks/search?keyword={text}` | Search tasks by keyword |
| GET | `/api/tasks/statistics` | Get task statistics |

**Create Task Request:**
```json
{
  "title": "Complete documentation",
  "description": "Write comprehensive README",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "dueDate": "2026-02-25T18:00:00"
}
```

**All task requests require JWT token in header:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tasks Table
```sql
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    status ENUM('TODO', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'TODO',
    priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    due_date TIMESTAMP,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

## 🐳 Docker Configuration

### Docker Compose Services

**MySQL Container:**
- Image: mysql:8.0
- Port: 3306
- Database: taskdb
- User: admin
- Password: admin123

**App Container:**
- Base: eclipse-temurin:17-jdk-alpine
- Port: 8080
- Auto-connects to MySQL

### Docker Commands
```bash
# Start containers
docker-compose up

# Start in background
docker-compose up -d

# Stop containers
docker-compose down

# Stop and remove all data
docker-compose down -v

# View running containers
docker ps

# View logs
docker-compose logs -f

# Restart after code changes
mvn clean package -DskipTests
docker-compose up --build

# Access MySQL container
docker exec -it taskmanagement-mysql mysql -uadmin -padmin123 taskdb
```

## 🔒 Security Features

- **Password Encryption:** BCrypt with salt (irreversible hashing)
- **JWT Tokens:** 15-mins validity, signed with HS256
- **Stateless Sessions:** No server-side session storage
- **Protected Endpoints:** All task operations require valid JWT
- **Input Validation:** Request data validation with error messages
- **CORS Enabled:** Configurable for frontend integration
- **SQL Injection Prevention:** JPA/Hibernate parameterized queries

## 🏗️ Project Structure
```
task-management-api/
├── src/main/java/com/mayank/taskmanagement/
│   ├── config/              # Security & Swagger configuration
│   │   ├── SecurityConfig.java
│   │   └── SwaggerConfig.java
│   ├── controller/          # REST API endpoints
│   │   ├── AuthController.java
│   │   └── TaskController.java
│   ├── dto/                 # Data Transfer Objects
│   │   ├── AuthResponse.java
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── TaskRequest.java
│   │   └── TaskResponse.java
│   ├── entity/              # JPA entities
│   │   ├── Task.java
│   │   └── User.java
│   ├── enums/               # Enumerations
│   │   ├── TaskPriority.java
│   │   └── TaskStatus.java
│   ├── exception/           # Custom exceptions & handler
│   │   ├── BadRequestException.java
│   │   ├── ResourceNotFoundException.java
│   │   ├── UnauthorizedException.java
│   │   ├── ErrorResponse.java
│   │   └── GlobalExceptionHandler.java
│   ├── filter/              # JWT authentication filter
│   │   └── JwtAuthenticationFilter.java
│   ├── repository/          # JPA repositories
│   │   ├── TaskRepository.java
│   │   └── UserRepository.java
│   ├── service/             # Business logic
│   │   ├── TaskService.java
│   │   └── UserService.java
│   ├── util/                # Utility classes
│   │   └── JwtUtil.java
│   └── TaskManagementApiApplication.java
├── src/main/resources/
│   └── application.properties
├── src/test/
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── .gitignore
├── pom.xml
└── README.md
```

## 🧪 Testing

**Run tests:**
```bash
mvn test
```

**Skip tests during build:**
```bash
mvn clean install -DskipTests
```

**Test with Postman:**
1. Import endpoints from Swagger
2. Set up environment variables
3. Test authentication flow
4. Test CRUD operations

## 📈 Project Metrics

- **API Endpoints:** 11
- **Database Tables:** 2
- **Docker Containers:** 2
- **Security Layers:** 3 (JWT + BCrypt + Spring Security)
- **Development Time:** 4 days
- **Test Coverage:** Unit tests with JUnit

## 🎯 Future Enhancements

- [ ] Email notifications on task events
- [ ] File attachments for tasks
- [ ] Task comments and activity log
- [ ] User profile management
- [ ] Task categories and tags
- [ ] React/Angular frontend
- [ ] Cloud deployment (AWS/Railway)
- [ ] Comprehensive JUnit test suite
- [ ] CI/CD pipeline with GitHub Actions

## 👨‍💻 Author

**Mayank Sikhwal**

- **GitHub:** [@mayanksikhwal](https://github.com/mayanksikhwal)
- **LinkedIn:** [Mayank Sikhwal](https://linkedin.com/in/mayanksikhwal)
- **Email:** sikhwalmayank251@gmail.com
- **Location:** Hyderabad, India

## 📝 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

Built with Spring Boot ecosystem, secured with industry-standard practices, documented with Swagger, and containerized with Docker.

---

**⭐ If you find this project useful, please star this repository!**

**🐛 Found a bug? Open an issue!**

**💡 Have suggestions? Pull requests are welcome!**
```
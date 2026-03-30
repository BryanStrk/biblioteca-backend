# 📚 Biblioteca App

Full-stack library management system built with **Spring Boot** (backend) and **React** (frontend).

---

## 🧰 Tech Stack

### Backend
![Java](https://img.shields.io/badge/Java-25-orange?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-green?style=flat&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-brightgreen?style=flat&logo=swagger)

### Frontend
![React](https://img.shields.io/badge/React-19-blue?style=flat&logo=react)
![Vite](https://img.shields.io/badge/Vite-7-purple?style=flat&logo=vite)
![TailwindCSS](https://img.shields.io/badge/TailwindCSS-4-cyan?style=flat&logo=tailwindcss)
![Axios](https://img.shields.io/badge/Axios-1.x-blueviolet?style=flat)

---

## 📋 Features

- 📖 Full **CRUD** for Books, Users and Reservations
- ⭐ Business rule: **one active reservation per user** (enforced in backend)
- 📊 Dashboard with real-time stats
- 🔍 Filter active reservations
- ⚠️ Global error handling with descriptive messages
- 📄 API documentation with Swagger UI

---

## 🏗️ Architecture
```
Controller → Service → Repository → Database
```

| Layer | Responsibility |
|---|---|
| **Controller** | HTTP request/response handling |
| **Service** | Business logic and validations |
| **Repository** | Database queries via Spring Data JPA |
| **Model** | JPA entities mapped to MySQL tables |
| **DTO** | Data transfer objects (input/output) |
| **Exception** | Centralized error handling |

---

## 🗄️ Data Model
```
User (1) ──────── (N) Reservation
Book (1) ──────── (N) Reservation
```

### User
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| name | String | Required |
| email | String | Required, unique |
| phone | String | Optional |
| registrationDate | LocalDate | Auto-assigned |

### Book
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| title | String | Required |
| author | String | Required |
| isbn | String | Unique |
| available | boolean | Default: true |

### Reservation
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| user | User | ManyToOne |
| book | Book | ManyToOne |
| reservationDate | LocalDate | Auto-assigned |
| returnDate | LocalDate | Nullable |
| active | boolean | Default: true |

---

## 🚀 Getting Started

### Prerequisites
- Java 25
- Maven
- MySQL 8
- Node.js 18+

### Backend Setup

**1. Clone the repository**
```bash
git clone https://github.com/https://github.com/BryanStrk/biblioteca-backend.git
cd biblioteca-backend
```

**2. Create the database**
```sql
CREATE DATABASE IF NOT EXISTS biblioteca_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

**3. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
```

**4. Run the application**
```bash
./mvnw spring-boot:run
```

Backend running at: `http://localhost:8080`
Swagger UI at: `http://localhost:8080/swagger-ui.html`

---

### Frontend Setup

**1. Clone the repository**
```bash
git clone https://github.com/TU_USUARIO/biblioteca-frontend.git
cd biblioteca-frontend
```

**2. Install dependencies**
```bash
npm install
```

**3. Run the application**
```bash
npm run dev
```

Frontend running at: `http://localhost:5173`

---

## 📡 API Endpoints

### Users
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Books
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/books` | Get all books |
| GET | `/api/books/available` | Get available books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Create book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |

### Reservations
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/reservations` | Get all reservations |
| GET | `/api/reservations/active` | Get active reservations |
| GET | `/api/reservations/user/{userId}` | Get reservations by user |
| POST | `/api/reservations?userId=&bookId=` | Create reservation |
| PATCH | `/api/reservations/{id}/cancel` | Cancel reservation |

---

## ⚠️ Error Handling

All errors return a consistent JSON format:
```json
{
  "timestamp": "2026-03-29T14:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 5"
}
```

| Status | Meaning |
|---|---|
| 400 | Bad Request (validation failed or business rule violated) |
| 404 | Resource not found |
| 500 | Unexpected server error |

---

## 🌿 Branch Strategy
```
main         ← stable production-ready code
└── develop  ← main development branch
    ├── feature/entidades
    ├── feature/repositories
    ├── feature/services
    ├── feature/controllers
    └── feature/frontend-base
```

---

## 📝 Commit Convention

This project follows [Conventional Commits](https://www.conventionalcommits.org/):

| Prefix | Usage |
|---|---|
| `feat:` | New feature |
| `fix:` | Bug fix |
| `chore:` | Config, dependencies |
| `refactor:` | Code restructure |
| `docs:` | Documentation |

---

## 👨‍💻 Author

**Bryan** — DAW Student  
[GitHub](https://github.com/https://github.com/BryanStrk)

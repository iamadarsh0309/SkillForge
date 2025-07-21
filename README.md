# SkillForge Backend

SkillForge is an online learning platform backend built with Spring Boot and MongoDB. It provides RESTful APIs for user authentication, course management, enrollments, and course reviews, supporting different user roles (Student, Mentor, Admin).

## Features
- User authentication with JWT (signup, login, profile management)
- Role-based access: Student, Mentor, Admin
- Course creation, listing, search, and management (Mentors only)
- Student enrollments and progress tracking
- Course reviews and ratings (only by enrolled students)
- Secure RESTful API with Spring Security

## Tech Stack
- Java 17
- Spring Boot 3
- MongoDB
- Spring Data MongoDB
- Spring Security (JWT-based)
- Lombok
- Maven

## Getting Started

### Prerequisites
- Java 17+
- Maven
- MongoDB (running locally on default port or update `application.properties`)

### Setup
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd skillforge-backend
   ```
2. **Configure MongoDB:**
   - By default, the backend connects to MongoDB at `mongodb://localhost:27017/skillforge`.
   - To change, edit `src/main/resources/application.properties`:
     ```properties
     spring.data.mongodb.uri=mongodb://localhost:27017/skillforge
     spring.data.mongodb.database=skillforge
     ```
3. **Build and run the application:**
   ```bash
   ./mvnw spring-boot:run
   # or
   mvn spring-boot:run
   ```
   The backend will start on `http://localhost:8080` by default.

## API Overview

### Authentication
- `POST /api/auth/signup` — Register a new user
- `POST /api/auth/login` — Login and receive JWT
- `GET /api/auth/me` — Get current user profile (JWT required)
- `PUT /api/auth/me` — Update current user profile (JWT required)

### Users
- `GET /api/users/mentors` — List all mentors
- `GET /api/users/students` — List all students
- `GET /api/users/{id}` — Get public profile by user ID

### Courses
- `POST /api/courses` — Create a new course (Mentor only)
- `GET /api/courses` — List all courses
- `GET /api/courses/my` — List mentor's own courses (Mentor only)
- `GET /api/courses/{id}` — Get course details
- `GET /api/courses/search?keyword=...` — Search courses by title

### Enrollments
- `POST /api/enrollments` — Enroll a student in a course
- `GET /api/enrollments` — List all enrollments
- `GET /api/enrollments/{id}` — Get enrollment by ID
- `PUT /api/enrollments/{id}` — Update enrollment progress
- `DELETE /api/enrollments/{id}` — Delete enrollment
- `GET /api/enrollments/user/{userId}` — List enrollments for a user

### Reviews
- `POST /api/reviews` — Post a review for a course (enrolled students only)
- `GET /api/reviews/course/{courseId}` — List reviews for a course

## Development
- Run tests: `mvn test`
- Build JAR: `mvn clean package`

## Useful References
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Spring Security](https://spring.io/projects/spring-security)

## License
This project is for educational/demo purposes.

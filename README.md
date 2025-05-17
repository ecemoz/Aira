# Aira - Backend Engineer Intern Case

## Overview
Aira is a robust backend application developed as a case study for the Backend Engineer Intern position. It integrates with the OpenWeather API to provide weather data, features a secure JWT-based authentication and authorization system with role-based access control, and utilizes Redis for efficient weather data caching. The project is implemented in Java using Spring Boot 3.2.5, with PostgreSQL as the relational database.

## 🚀 Features
- ✅ **User Authentication** with JWT
- ✅ **Role-based Access Control** (Admin/User)
- ✅ **Weather Querying System** integrated with OpenWeather API
- ✅ **Redis Caching** to minimize external API calls
- ✅ **Query Persistence** with metadata in PostgreSQL
- ✅ **Admin Panel API Endpoints** to manage and view user data
- ✅ **Robust Architecture** following domain-service-controller layering
- ✅ **Environment Configurable** via `application.properties`

## 🛠️ Tech Stack
- **Backend Framework:** Spring Boot (Java 17)
- **Database:** PostgreSQL
- **Authentication:** JWT (Spring Security)
- **Caching:** Redis
- **API Integration:** OpenWeatherMap API
- **Build Tool:** Maven (Multi-module)
- **Containerization:** Docker
- **Documentation:** Swagger (Optional Postman support)

## 🏗️ Architecture
```
com.aira.backend
│
├── domain           # Entities and Enums
├── repository       # JPA Repositories
├── logic            # Service Layer
├── delivery         # REST Controllers
├── infrastructure   # Security, Configuration, Filters
├── shared           # External API clients, caching helpers
└── config           # Beans, Redis, Security Config
```

## 📋 User Roles
- **Admin**
  - Create Users
  - View all weather queries
- **User**
  - View only their own weather queries

## 🔐 Authentication & Authorization
JWT token-based authentication is implemented. A filter extracts and validates the token on each request. Role-based access is enforced via Spring Security annotations.

## 🌦️ OpenWeather API Integration
- API Key is stored securely in `application.properties`
- Results are cached with Redis using `@Cacheable`
- Only successful responses are stored and persisted in DB

## 🧠 Domain Model
```java
WeatherQuery {
  UUID id;
  String city;
  String responseJson;
  LocalDateTime queryTime;
  Double temperature;
  String weatherDescription;
  Integer humidity;
  Long durationMs;
  String source;
  QueryStatus status; // Enum: SUCCESS, ERROR
  User user;
}
```

## 🧪 Testing
- Unit tests and integration test hooks are prepared (e.g., Testcontainers, Mockito optional)
- JWT validation tested manually with mock users

## 🧰 Setup Instructions
1. Clone the repo:
```bash
git clone https://github.com/your-username/aira.git
cd aira
```
2. Configure `.env` or `application.properties` with:
```properties
openweather.api.key=YOUR_API_KEY
spring.datasource.username=postgres
spring.datasource.password=your_password
```
3. Run Redis locally (default port 6379)
```bash
docker run -p 6379:6379 redis
```
4. Start the application:
```bash
./mvnw spring-boot:run
```

## 📦 API Examples
### `POST /api/auth/login`
Returns JWT token for authenticated user.

### `GET /api/weather?city=Istanbul`
Fetches weather data (cached if available).

### `GET /api/admin/queries`
(Admin only) Returns all user queries.

## 🧠 Design Rationale
- Clean layered architecture → easy maintainability
- Use of caching to reduce API cost & latency
- Swagger available for API testing
- Modular, testable, and secure

## 📄 License
MIT

---
Developed with 💻 for AppNation Intern Case Study

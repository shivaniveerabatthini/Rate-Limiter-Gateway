# API Gateway with Rate Limiting and Circuit Breaker

A Spring Boot Microservices project demonstrating an API Gateway with JWT Authentication, Rate Limiting, Circuit Breaker, Retry, Bulkhead, Logging, Actuator Monitoring, and Product Service integration.

---

## Features

- API Gateway
- Product Microservice
- JWT Authentication
- Fixed Window Rate Limiter
- Circuit Breaker (Resilience4j)
- Retry Mechanism
- Bulkhead Pattern
- Request Logging
- Spring Boot Actuator
- Prometheus Metrics
- REST APIs
- Docker Support
- Cloud Deployment on Render

---

## Technologies Used

- Java 21
- Spring Boot 3
- Maven
- Resilience4j
- Spring Security (JWT)
- Spring Actuator
- Prometheus
- Docker
- Git & GitHub
- Render

---

## Project Structure

```
Rate-Limiter-Gateway
│
├── gateway
│   ├── JWT Authentication
│   ├── Rate Limiter
│   ├── Circuit Breaker
│   ├── Retry
│   ├── Bulkhead
│   ├── Logging Filter
│   └── REST Controllers
│
└── product-service
    ├── Product Controller
    ├── Product Service
    └── Product Model
```

---

## Architecture

```
Client
   │
   ▼
API Gateway
   │
   ├── JWT Authentication
   ├── Rate Limiter
   ├── Logging Filter
   ├── Circuit Breaker
   ├── Retry
   └── Bulkhead
   │
   ▼
Product Service
```

---

## APIs

### Login

POST

```
/auth/login
```

Request

```json
{
  "username":"shivani",
  "password":"12345"
}
```

---

### Get Products

GET

```
/gateway/products
```

Authorization

```
Bearer <JWT Token>
```

---

### Product Service

GET

```
/products
```

---

## Sample Response

```json
[
  {
    "id":1,
    "name":"Laptop",
    "price":75000.0
  },
  {
    "id":2,
    "name":"Mouse",
    "price":1200.0
  }
]
```

---

## Deployment

### Product Service

https://product-service-bd11.onrender.com/products

### Gateway

https://gateway-ujzl.onrender.com/gateway/products

### Health Check

Gateway

https://gateway-ujzl.onrender.com/actuator/health

Product Service

https://product-service-bd11.onrender.com/actuator/health

---

## How to Run

Clone the repository

```bash
git clone https://github.com/shivaniveerabathini/Rate-Limiter-Gateway.git
```

Go to gateway

```bash
cd gateway
```

Build

```bash
mvn clean install
```

Run

```bash
mvn spring-boot:run
```

---

## Future Improvements

- Redis Cloud Integration
- API Gateway Routing
- Service Discovery
- Spring Cloud Gateway
- Docker Compose Deployment
- Kubernetes Deployment
- CI/CD using GitHub Actions

---

## Author

**Shivani Veerabathini**

B.Tech Computer Science & Cyber Security

2026 Graduate

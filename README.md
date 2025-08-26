# LittleShop Backend 🛍️

LittleShop is a robust, modular backend application for an e-commerce platform, built with Java and the Spring Boot framework. It leverages a Clean Architecture design, ensuring a clear separation of concerns, high maintainability, and ease of testing.
## 🏗️ Architecture

This project is structured around the principles of Clean Architecture, also known as the Hexagonal Architecture or Ports and Adapters.  The core idea is to keep the business logic (the "domain") independent of external frameworks, databases, and user interfaces.

The project is organized into the following main layers:

    domain: The innermost layer, containing the core business logic. This includes entities (Customer, Order, Product), value objects, and business rules. It has no dependencies on any external framework or data persistence mechanism.

    application: This layer contains the use cases (or interactors) that orchestrate the flow of data to and from the domain. It defines ports (interfaces) for what the application needs (e.g., UserRepositoryPort to get a user from a database) and what it provides (e.g., CreateUserUseCase to expose a user creation service).

    adapter: This is the outermost layer. It contains adapters that connect the application to external services.

        web: Adapters for the REST API, handling incoming HTTP requests and translating them into use case commands.

        persistence: Adapters for the database, implementing the repository ports defined in the application layer.

        auth: Adapters for external authentication services, like JWT token management.

    infrastructure: This layer contains the configuration and utilities needed to run the application, such as security filters, global exception handlers, and database initialization.

This structure allows us to swap out external dependencies—like changing the database from JPA to a NoSQL database or the REST framework to something else—without altering the core business logic.

## 📦 Project Structure

```
.
├── LittleshopApplication.java            # Main Spring Boot entry point
├── adapter/                              # External world adapters (web, persistence, auth)
│   ├── auth/                             # JWT token handling and authentication adapters
│   ├── persistence/                      # Data persistence adapters (JPA)
│   └── web/                              # REST API controllers and DTOs
├── application/                          # Use cases, application services, and ports
│   ├── dto/                              # Data Transfer Objects (DTOs) for use cases
│   ├── port/                             # Interfaces for application input (in) and output (out)
│   └── usecase/                          # Implementation of use cases (business logic orchestration)
├── domain/                               # Core business logic and entities
│   ├── exception/                        # Custom domain-specific exceptions
│   └── model/                            # Business entities and value objects
├── infrustructure/                       # Application configuration and utilities
│   ├── aspect/                           # AOP for logging and exception handling
│   ├── config/                           # Spring and security configurations
│   ├── exception/                        # Global exception handler
│   ├── security/                         # JWT authentication filters
│   └── startup/                          # Initial data population (e.g., creating an admin user)
└── util/                                 # General utility classes
```

## ▶️ Getting Started
### Prerequisites

    Java 21 or higher

    Maven

    Docker (optional, for running with Docker)

### Build and Run

Do not forget about environment variables

    Clone the repository:

    git clone https://github.com/abrohamovich/littleshop-backend.git
    cd littleshop-backend

    Build the project with Maven:

    ./mvnw clean install

    Run the application:

    ./mvnw spring-boot:run

    The application will start on http://localhost:8080.

### Docker

#### You can also run the application using Docker:

Do not forget about environment variables

    Build the Docker image:

    docker build -t abrohamovich/littleshop-backend .

    Run the container:

    docker run -p 8080:8080 abrohamovich/littleshop-backend

## 🚀 CI/CD Pipeline

The project includes two GitHub Actions workflows for continuous integration and deployment.

[![Test pipeline](https://github.com/abrohamovich/littleshop/actions/workflows/test-pipeline.yml/badge.svg)](https://github.com/abrohamovich/littleshop/actions/workflows/test-pipeline.yml)

[![Docker pipeline](https://github.com/abrohamovich/littleshop/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/abrohamovich/littleshop/actions/workflows/docker-publish.yml)

You can view the status of these pipelines on the GitHub repository's "Actions" tab.
## 🛡️ Security

The application is secured using JWT (JSON Web Tokens).

    AuthRestController handles user authentication requests.

    JwtAuthenticationFilter intercepts incoming requests to validate the JWT token.

    The security configuration in SecurityConfig defines the rules for which endpoints require authentication.

An initial admin user is created on startup to ensure you can access secured endpoints.
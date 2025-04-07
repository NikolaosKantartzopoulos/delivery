# 🛵 Delivery Application

This is a modular Spring Boot application for managing a food delivery catalogue and related services. 

The project is designed with scalability and maintainability in mind using Gradle convention plugins and a multi-module setup.

---

## 🚀 Features & Structure

### 📦 Modular Architecture
- `app-core`: Entry point and configuration for the application
- `catalogue-api`: Defines public API interfaces and DTOs
- `catalogue-runtime`: Implements API endpoints and service logic
- `infra-database`: Contains Flyway migrations and DB setup

### 🛠 Gradle Setup
- Uses **`buildSrc`** for centralized plugin logic and shared Gradle configuration
- Convention plugins include:
    - `common.java-conventions`: Java, Groovy, Spring Boot, Checkstyle, CodeNarc setup
    - `module.java-conventions`: Shared model + Jakarta annotations + Lombok + OpenAPI
    - `module-api.java-conventions` / `module-runtime.java-conventions`: Specialized module types
    - `database.java-conventions`: Flyway and DB-specific setup
    - `test.java-conventions`: Spock, Spring Test, JUnit Platform

### 🧪 Testing
- Uses **Spock Framework** for expressive and behavior-driven testing
- Spring Boot test utilities for integration scenarios
- JUnit Platform launcher included

---

## ⚙️ Prerequisites

- Java 21 (configured via Gradle toolchains)
- Docker (for local Postgres & Redis)

---

## ▶️ Running the App

>s See gradle tasks in Development group

```bash
./gradlew tasks --group Development
```

> Before starting the Spring Boot application, make sure the required services are running via Docker Compose:

```bash
docker compose up -d
./gradlew bootRun

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

> The docker compose script running before build and bootRun starts a postgres db in port 5555

> See gradle tasks in Development group

```bash
./gradlew tasks --group Development
```

> Run the app

```bash
./gradlew bootRun
```

---

## 📦 Building & Deploying Docker Images

### 🔁 Push versioned and `dev01` Docker images to Docker Hub (Mac)
```bash
./gradlew :delivery-infra:delivery-docker:dockerDeployDev01
```
This will:
- Build and tag a versioned Docker image (e.g., `:2025-04-09-09-40-68a0a11-v1.0`)
- Tag the image as `:dev01`
- Push both tags to Docker Hub

### 🐧 Pull and run on your Ubuntu server (dev01)
```bash
# SSH into server
ssh nik@192.168.1.99

# Pull the dev01 image
docker pull nkantartzopoulos/delivery-app:dev01

# Run the dev01 stack
docker compose -f /home/nik/code/development/delivery-app/docker/docker-compose.dev01.yml up -d
```

> 💡 Use `docker compose ... down` to stop the containers

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

### 🧩 Frontend
- `delivery-frontend`: React app powered by Vite
- Supports multiple environments with `.env` files and Docker build args
- Served using `serve` on port 7900

### 🛠 Gradle Setup
- Uses **`buildSrc`** for centralized plugin logic and shared Gradle configuration
- Convention plugins include:
    - `common.java-conventions`: Java, Groovy, Spring Boot, Checkstyle, CodeNarc setup
    - `module.java-conventions`: Shared model + Jakarta annotations + Lombok + OpenAPI
    - `module-api.java-conventions` / `module-runtime.java-conventions`: Specialized module types
    - `database.java-conventions`: Flyway and DB-specific setup
    - `test.java-conventions`: Spock, Spring Test, JUnit Platform
    - `versioning`: Dynamic Docker tag generation based on commit + date
    - `utilities`: Utilities for dumping Gradle or Helm files

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

> The docker compose script running before build and bootRun starts a postgres db on port 5432

> See gradle tasks in Development group

```bash
./gradlew tasks --group Development
```

> Run the app

```bash
./gradlew bootRun
```

This will:
- Automatically start Docker services via `dockerUp`
- Run with `dev` profile unless overridden via `-Pprofiles=xyz`

### 🐘 Local Docker Compose (PostgreSQL)

Defined in `delivery-infra/docker-compose.yml` or `.dev01.yml`:
```yaml
services:
  postgres-db:
    image: postgres
    container_name: postgres-delivery-db
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=delivery
    volumes:
      - deliveryPSQL:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    networks:
      - monitoring
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
```

---

## ⚙️ Spring Configuration (application.properties)

```properties
spring.application.name=delivery

# SERVER
server.ssl.enabled=false

# DATA SOURCE
spring.datasource.url=jdbc:postgresql://localhost:5432/delivery
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.flyway.schemas=delivery
spring.flyway.default-schema=delivery

# JPA / HIBERNATE
spring.jpa.properties.hibernate.default_schema=delivery
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# ACTUATOR
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

# OPEN API
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Docker
spring.docker.compose.enabled=false

# Environment properties
env.company-name=Sample company name
```

---

## 📦 Building & Deploying Docker Images

### 🔁 Backend - Gradle Tasks (Docker Hub)

```bash
./gradlew :delivery-infra:delivery-docker:dockerDeployDev01
```
This will:
- Generate a version tag like `2025-04-09-09-40-68a0a11-v1.0`
- Tag the image as `:dev01`
- Push both to Docker Hub under `nkantartzopoulos/delivery-app-backend`

Or manually run steps:
```bash
./gradlew createDockerImageTag dockerBuildAppCore dockerTagAsDev01 dockerPushAppCore dockerPushDev01
```

Push a `latest` tag:
```bash
./gradlew :delivery-infra:delivery-docker:dockerPushLatest
```

### 🖥 Frontend - NPM Scripts

From `delivery-frontend/`:
```bash
npm run docker:push:latest
npm run docker:push:dev01
```
These use `buildx` to generate and push tagged images like:
- `nkantartzopoulos/delivery-app-frontend:latest`
- `...:dev01`

---

### 🐧 Pull and run on your Ubuntu server (dev01)
```bash
ssh nik@192.168.1.99

# Pull dev01-tagged images
docker pull nkantartzopoulos/delivery-app-backend:dev01
docker pull nkantartzopoulos/delivery-app-frontend:dev01

# Apply Kubernetes manifests
kubectl apply -f /home/nik/code/development/delivery-app/k8s/dev01
```

To stop the stack:
```bash
kubectl delete -f /home/nik/code/development/delivery-app/k8s/dev01
```

---

## 🧰 Utilities & Diagnostics

### Gradle Diagnostic Helpers
```bash
./gradlew dumpGradleFiles    # Lists *.gradle contents
./gradlew dumpHelmFiles      # Dumps all files under delivery-infra/helm
```

---

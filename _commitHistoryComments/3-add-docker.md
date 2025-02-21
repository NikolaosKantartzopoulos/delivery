# Docker Compose Configuration for PostgreSQL

This document explains the purpose of each part of the `docker-compose.yml` configuration and the required dependency in Spring Boot.

## Docker Compose Configuration

```yaml
services:
  postgres-db:
    image: postgres
    container_name: postgres-db
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=agenda-application
    volumes:
      - actuatorDemoPSQL:/var/lib/postgresql/data
    ports:
      - "5432:5432"

volumes:
  deliveryPSQL:
```

### Explanation:
- **`services.postgres-db`**: Defines a PostgreSQL service.
- **`image: postgres`**: Uses the official PostgreSQL Docker image.
- **`container_name: postgres-db`**: Names the container `postgres-db`.
- **`environment`**: Sets up environment variables for PostgreSQL credentials and database name.
- **`volumes`**: Mounts a volume (`actuatorDemoPSQL`) for persistent data storage.
- **`ports`**: Exposes PostgreSQL on port `5432`.
- **`volumes.deliveryPSQL`**: Declares a named volume for persistence.

## Spring Boot Dependency for Docker Compose

```gradle
developmentOnly 'org.springframework.boot:spring-boot-docker-compose'
```

### Explanation:
- This dependency allows Spring Boot to manage Docker Compose lifecycle during development.
- It ensures that the required services (like PostgreSQL) are available before the application starts.

This setup provides an isolated PostgreSQL database environment for development and testing.


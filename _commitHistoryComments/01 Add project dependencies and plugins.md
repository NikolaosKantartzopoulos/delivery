# Explanation of Added Plugins and Dependencies

## Plugins

### Groovy Plugin
**ID:** `groovy`

**Reason for Adding:**
The Groovy plugin is included to enable support for writing tests using the Spock framework, which is a powerful testing library for Groovy. Since Spock provides an expressive and readable syntax, it improves the quality and maintainability of tests.

### SpringDoc OpenAPI Plugin
**ID:** `org.springdoc.openapi-gradle-plugin` (Version 1.8.0)

**Reason for Adding:**
This plugin automates the generation of OpenAPI documentation for the Spring Boot application. It simplifies the process of keeping API documentation up to date and integrates seamlessly with SpringDoc OpenAPI libraries.

## Dependencies

### Spring Framework Dependencies
- **Spring Boot DevTools:** Provides hot reloading for a better development experience.
- **Jakarta Validation API & Hibernate Validator:** Enables validation support for Java objects.
- **SpringDoc OpenAPI Starter WebMVC UI:** Helps generate OpenAPI documentation.
- **Freemarker:** Template engine for generating dynamic HTML views.
- **Spring Boot Web Starter:** Core web functionalities for the application.

### Utility & Database Dependencies
- **ModelMapper:** Simplifies object mapping between DTOs and entities.
- **Spring Boot Actuator:** Exposes endpoints for monitoring and metrics.
- **Lombok:** Reduces boilerplate code using annotations.
- **Spring Data JPA & JDBC:** Provides ORM and database access.
- **PostgreSQL Driver:** Enables PostgreSQL database connectivity.
- **Flyway:** Handles database migrations.
- **Micrometer Prometheus Registry:** Integrates application metrics with Prometheus.

### Testing Dependencies
- **JUnit 5 & Spring Boot Test Starter:** Enables unit and integration testing.
- **JUnit Platform Launcher:** Required for running JUnit tests.
- **Spock Framework:** Provides a Groovy-based alternative for writing tests in a more expressive way.

This setup ensures the application is well-structured, documented, and testable while maintaining efficient development workflows.


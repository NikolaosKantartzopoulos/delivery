# Spring Boot Configuration Explained

This document provides an explanation of each property in the `application.properties` file and its purpose in the Spring Boot application.

## Application Name
```properties
spring.application.name=delivery
```
- This sets the application name to `delivery`, which is useful for logging and service discovery when working with microservices.

## Data Source Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```
- These properties configure the connection to a PostgreSQL database running on `localhost` on port `5432`.
- `spring.datasource.url` specifies the JDBC connection string.
- `spring.datasource.username` and `spring.datasource.password` provide authentication credentials.

## JPA / Hibernate Configuration
```properties
spring.jpa.properties.hibernate.default_schema=delivery
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
#spring.jpa.hibernate.ddl-auto=update
```
- `spring.jpa.properties.hibernate.default_schema=delivery` ensures that all database tables are created under the `delivery` schema.
- `spring.jpa.show-sql=true` enables logging of SQL queries executed by Hibernate, useful for debugging.
- `spring.jpa.hibernate.ddl-auto=create-drop` makes Hibernate drop and recreate tables on application startup. Useful for development but should be changed to `update` or `validate` in production.
- The `update` option (commented out) would keep existing data while modifying the schema as needed.

## Actuator (Monitoring & Health Checks)
```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
management.prometheus.metrics.export.enabled=true
management.endpoint.prometheus.access=unrestricted
```
- `management.endpoints.web.exposure.include=*` exposes all actuator endpoints, making them accessible for monitoring.
- `management.endpoint.health.show-details=always` ensures that detailed health information is always visible.

## OpenAPI & Swagger Configuration
```properties
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```
- `springdoc.api-docs.path=/v3/api-docs` sets the path for the OpenAPI documentation.
- `springdoc.swagger-ui.path=/swagger-ui.html` provides a UI to interact with the API documentation.

These configurations are essential for ensuring database connectivity, entity management, monitoring, and API documentation within the Spring Boot application.


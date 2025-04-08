# 26 Docker: Docker Compose Relocation and Configuration Update

This document summarizes the changes made to the project structure and configuration to relocate `docker-compose.yml` and update PostgreSQL port mappings.

## 📁 Moved `docker-compose.yml`

- The `docker-compose.yml` file was moved from the project root to a new directory:
  ```
  ./delivery-infra/docker-compose.yml
  ```

## 🛠️ Updated Gradle `dockerUp` Task

**File:** `delivery-application/app-core/build.gradle`

- The `workingDir` for the `dockerUp` task was updated to point to the new location of `docker-compose.yml`.

**Before:**
```groovy
workingDir rootDir  // so it picks up ./docker-compose.yml from the root
```

**After:**
```groovy
workingDir "$rootDir/delivery-infra"
```

## 🧪 Updated Application Properties

**File:** `delivery-application/app-core/src/main/resources/application.properties`

- The PostgreSQL JDBC URL was updated to reflect the new host port mapping:

**Before:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/delivery
```

**After:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5555/delivery
```

## ❌ Removed Old `docker-compose.yml`

- The original `docker-compose.yml` file in the project root was deleted.

---

These changes support better organization of infrastructure components and allow the PostgreSQL container to be accessed via port `5555` on the host machine.

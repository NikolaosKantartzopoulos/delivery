# 🧭 Migrating to Multi-Module Structure: `delivery-application/app-core`

## 📝 Purpose

This document describes how the original `src/` structure of the Spring Boot application was reorganized into a Gradle multi-project layout. The main application source code was moved under the subproject: `delivery-application/app-core`.

---

## 🗂️ Old Structure

Previously, all application source files were located in a flat structure at the root level:

```
project-root/
├── build.gradle
├── settings.gradle
├── src/
│   ├── main/java/nik/delivery/...
│   ├── main/resources/...
│   ├── test/java/nik/delivery/...
│   ├── test/groovy/delivery/...
```

---

## 📦 New Structure

After the migration, the codebase now follows a nested modular layout:

```
project-root/
├── delivery-application/
│   └── app-core/
│       ├── build.gradle
│       ├── src/
│       │   ├── main/java/nik/delivery/...
│       │   ├── main/resources/...
│       │   ├── test/java/nik/delivery/...
│       │   └── test/groovy/delivery/...
```

---

## 🔀 Migration Summary

All files were moved from the root `src/` directory into the new submodule location:

- Java source files:
    - `src/main/java/**` → `delivery-application/app-core/src/main/java/**`
- Java test files:
    - `src/test/java/**` → `delivery-application/app-core/src/test/java/**`
- Groovy test files:
    - `src/test/groovy/**` → `delivery-application/app-core/src/test/groovy/**`
- Resources:
    - `src/main/resources/**` → `delivery-application/app-core/src/main/resources/**`
- Templates:
    - `src/main/resources/templates/**` → `delivery-application/app-core/src/main/resources/templates/**`
- Flyway migrations:
    - `src/main/resources/db.migration/**` → `delivery-application/app-core/src/main/resources/db.migration/**`

---

## ⚙️ Supporting Changes

- **Root `settings.gradle`** was updated:

  ```groovy
  pluginManagement {
      repositories {
          gradlePluginPortal()
      }
  }

  rootProject.name = 'delivery'
  include 'delivery-application:app-core'

  println('SETTINGS FILE: This is executed during the initialization phase')
  ```

- **Root `build.gradle`** was cleared, as logic moved into `app-core`.

- **Docker Compose** was disabled inside `application.properties` to prevent Spring Boot from automatically managing it:

  ```properties
  spring.docker.compose.enabled=false
  ```

---

## 🧪 Running the App and Tests

Before running the app or executing tests, you now **must manually start Docker Compose** to bring up the database service:

```bash
docker-compose up -d
```

Once Docker is running, you can:

- Build the project:
  ```bash
  ./gradlew build
  ```

- Run the app:
  ```bash
  ./gradlew :delivery-application:app-core:bootRun
  ```

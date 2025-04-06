# Gradle Setup Changelog

## ✅ Overview

This changelog documents the integration of `buildSrc`, convention plugins, and version catalogs (`libs.versions.toml`) into the Gradle project.

---

## 🏗️ 1. Added `buildSrc` directory

- Created `buildSrc` to define shared plugins and configurations.
- Added:
    - `build.gradle` with `groovy-gradle-plugin` to support plugin development.
    - `settings.gradle` with dependency resolution to use `libs.versions.toml`.
    - `src/main/groovy/delivery.java-conventions.gradle`: a convention plugin with:
        - `java`, `groovy`, `checkstyle`, `codenarc`, and `spring-boot` plugins.
        - Java toolchain set to version 21.
        - Shared `codenarc` rules.
        - `compileOnly` extending from `annotationProcessor`.

## 🔧 2. Converted build scripts to use `delivery.java-conventions`

Affected modules:
- `app-core`
- `catalogue-api`
- `catalogue-runtime`
- `infra-database`

Each switched from hardcoding plugin and config logic to applying:

```groovy
plugins {
    id 'delivery.java-conventions'
}
```

## 🧰 3. Introduced `libs.versions.toml`

- Centralized all versions for libraries and plugins.
- Used with the `versionCatalogs` block.
- Extracted dependencies from modules into catalog:
    - Spring Boot starters
    - OpenAPI, JPA, Hibernate Validator
    - Micrometer Prometheus
    - Flyway
    - Spock, JUnit
    - Jakarta APIs
    - ModelMapper
    - Lombok

## 🔄 4. Project-level `build.gradle` and plugin management

- Top-level `build.gradle` now uses:

```groovy
plugins {
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.openapi.plugin) apply false
}
```

- Ensures versions are resolved via the catalog and shared plugin metadata.

---

## ✅ Benefits

- Consistency across all modules.
- Central version management.
- Shared quality rules.
- Cleaner build scripts.

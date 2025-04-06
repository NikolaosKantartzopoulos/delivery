
# 📝 Commit Summary: Convention Plugin Refactor & Cleanup

## 📁 `buildSrc`: Refactored and Modularized Convention Plugins

### 🔄 Renamed
- `delivery.java-conventions.gradle` ➡️ **`common.java-conventions.gradle`**
    - Centralized Spring Boot + general Java/Groovy plugin logic
    - Toolchain config now uses version from `libs.versions.toml`
    - Moved `repositories { mavenCentral() }` into this file

### ✨ Added New Convention Plugin Scripts
- `module.java-conventions.gradle`: Shared module-level setup
    - Adds ModelMapper, Lombok, Jakarta API, Springdoc OpenAPI
    - Disables `bootJar`
- `module-api.java-conventions.gradle`:
    - Applies `module.java-conventions`
    - Adds OpenAPI UI dependency
- `module-runtime.java-conventions.gradle`:
    - Applies `module.java-conventions`
    - Adds Web, JPA, and Validation Spring Starters
- `database.java-conventions.gradle`:
    - Adds Flyway (core + Postgres), disables `bootJar`
- `test.java-conventions.gradle`:
    - Adds Spock, Spring Boot test, JUnit platform launcher
    - Configures `useJUnitPlatform()` for test task

## 📦 Updated Application Modules to Use New Convention Plugins

### `app-core`
- Replaced:
    - `delivery.java-conventions` ➡️ `common.java-conventions`, `module.java-conventions`
- Removed redundant dependencies now covered by plugins (Lombok, ModelMapper, etc.)
- Removed explicit `bootJar`, `codenarc`, and `springBoot` blocks

### `catalogue-api`
- Switched to:
    - `common.java-conventions`, `module-api.java-conventions`
- Removed redundant dependency and build config blocks

### `catalogue-runtime`
- Switched to:
    - `common.java-conventions`, `module-runtime.java-conventions`
- Removed all redundant Spring, OpenAPI, Lombok, and test dependencies
- Removed `bootJar` and `jar` config blocks

### `infra-database`
- Switched to:
    - `common.java-conventions`, `database.java-conventions`
- Removed Flyway dependencies and packaging config (now in plugin)

## 🔥 Removed File
- `delivery-application/build.gradle`: Deleted (was empty or unused)

# Extract catalogue feature to dedicated modules

This commit introduces a modular design for the food category functionality by separating it from the core application and organizing it into dedicated `catalogue-api` and `catalogue-runtime` modules. This improves the project's structure, enforces clean boundaries between API and implementation, and lays the groundwork for future modularization.

## Changes

### 🧱 New Modules
- `delivery-application/catalogue/catalogue-api`
    - Contains interfaces (`FoodCategoryApi`) and DTOs used by external modules.
- `delivery-application/catalogue/catalogue-runtime`
    - Contains implementations (`FoodCategoryApiImpl`, REST controller), domain model, repository, service layer, and tests.

### 🔨 Updated Core Module
- Removed food category classes (API, DTOs, domain, service, tests) from `app-core`.
- `app-core/build.gradle` now includes:
    - `implementation project(':delivery-application:catalogue:catalogue-api')`
    - `runtimeOnly project(':delivery-application:catalogue:catalogue-runtime')`

### 🛠 Gradle & Settings
- Root `build.gradle`: added common `codenarc` configuration for all subprojects.
- `settings.gradle`:
    - Enabled toolchain resolution plugin.
    - Added includes for new modules.

## Benefits

- Clearer separation of responsibilities.
- Runtime implementation (`catalogue-runtime`) not exposed to `app-core`.
- Easier to test, evolve, or replace feature modules independently.

# 🗃️ Move Flyway to a Dedicated Gradle Module

This commit documents the extraction of Flyway-related database migration files and configuration into a new Gradle subproject called `infra-database`.

---

## ✅ What Changed

### 🔄 Modified
- **`delivery-application/app-core/build.gradle`**
    - Removed direct Flyway dependencies
    - Added local project dependency:
      ```groovy
      implementation project(':delivery-application:infra-database')
      ```

### 🗃️ Moved
- **Flyway migration scripts**:
    - `V1__create_schema.sql`
    - `V2__create_table_food_category.sql`
      → moved from `app-core` to `infra-database`

### 🔧 Updated
- **`settings.gradle`**
    - Registered new module:
      ```groovy
      include 'delivery-application:infra-database'
      ```

---

## 📁 New Structure

```
delivery-application/
├── app-core/
│   └── (no longer contains db.migration)
└── infra-database/
    └── src/main/resources/db.migration/
        ├── V1__create_schema.sql
        └── V2__create_table_food_category.sql
```

---

## 🧪 How to Use

- The app-core module now depends on `infra-database`, which contributes migration scripts via the classpath.
- Flyway configuration should remain in `application.properties` with:
  ```properties
  spring.flyway.locations=classpath:db.migration
  ```



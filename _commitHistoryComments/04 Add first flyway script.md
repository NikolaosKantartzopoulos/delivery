# Initial Flyway Migration Script

This document describes the first Flyway migration script for setting up the database schema.

## Migration Script

```sql
CREATE SCHEMA IF NOT EXISTS "delivery";
```

### Explanation:
- **`CREATE SCHEMA IF NOT EXISTS "delivery";`**: Ensures that the `delivery` schema exists in the database. If it already exists, no changes are made.
- This script should be placed in the Flyway migrations folder (e.g., `src/main/resources/db/migration/V1__create_schema.sql`).
- Flyway will execute this script on startup to manage database versioning.

This setup ensures a structured database schema for the application.


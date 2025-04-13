# ✅ Docker Compose Healthcheck Added for Postgres

This update introduces `healthcheck` sections to both development and remote Docker Compose files, improving reliability by ensuring Postgres is fully ready before dependent services or scripts proceed.

---

## 🔧 Changes Summary

### 📄 `docker-compose.dev01.yml`
The `postgres-db` service in the remote dev environment now includes a healthcheck:

```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready -U postgres"]
  interval: 10s
  timeout: 5s
  retries: 5
```

This ensures that Docker Compose can monitor the readiness of the Postgres container before starting dependent services like `delivery-app`.

---

### 📄 `docker-compose.yml` (Local Development)
The same healthcheck was added to the local Postgres container to improve stability for `bootRun`, integration testing, and live development:

```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready -U postgres"]
  interval: 10s
  timeout: 5s
  retries: 5
```

Even in local workflows, this helps prevent race conditions where the application might try to connect before Postgres is fully initialized.

---

## 💡 Why It Matters
- Ensures Postgres is actually ready to accept connections.
- Prevents flaky startup behavior in Spring Boot, Gradle tasks, or Docker Compose apps.
- Especially useful when services start automatically or are orchestrated by automation.

Let me know if you want to wait on the health status programmatically in a Gradle task or script!

# 19 Gradle: Run Docker Compose before bootRun

This change adds a custom Gradle task to ensure the required infrastructure (like the Postgres database) is up before running the Spring Boot application.

### ✅ What was added

In `app-core/build.gradle`:
- A new task `dockerUp` that executes `docker compose up -d`
- The `bootRun` task now depends on `dockerUp`

### ▶️ Result

You can now run:

```bash
./gradlew bootRun
```

And it will:
1. Start Docker containers with `docker compose up -d`
2. Launch the Spring Boot app

### 📁 Files changed
- `delivery-application/app-core/build.gradle`

Added:

```groovy
// Task that runs Docker Compose
tasks.register('dockerUp', Exec) {
    group = 'infrastructure'
    description = 'Runs docker compose up -d'
    commandLine 'docker', 'compose', 'up', '-d'
}

// Make bootRun depend on dockerUp
tasks.named('bootRun') {
    dependsOn 'dockerUp'
}
```

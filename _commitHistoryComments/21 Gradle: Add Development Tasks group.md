# 20 Gradle: Group bootRun, test, and dockerUp under Development group

This change organizes key development tasks into a dedicated group in Gradle to improve visibility and usability.

### ✅ What was added

In `app-core/build.gradle`:
- Grouped `bootRun`, `test`, and `dockerUp` under `"Development"` task group
- Added meaningful descriptions to each for better discoverability
- `bootRun` continues to depend on `dockerUp` to ensure services are started first

### ▶️ Result

You can now run:

```bash
./gradlew tasks --group Development
```

To see only the tasks relevant for local development:

```
Development tasks
-----------------
bootRun   - Run the Spring Boot application
dockerUp  - Run docker-compose to start required containers
test      - Run all tests using Spock
```

### 📁 File changed
- `delivery-application/app-core/build.gradle`

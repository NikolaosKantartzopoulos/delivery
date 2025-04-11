
# Github: Check build on push

This GitHub Actions workflow ensures the Spring Boot application builds correctly every time changes are pushed to the `main` branch.

---

### ✅ What it does
- Checks out the latest code
- Sets up JDK 21 using Temurin
- Installs Docker and Docker Compose (required to run `dockerUp` task)
- Sets up Gradle using the official GitHub action
- Runs the full build including any Docker-based dependencies

---

### 📂 Workflow file location
`.github/workflows/check-build.yml`

---

### 🧱 Included Gradle Integration
The workflow supports a custom Gradle task (`dockerUp`) that spins up infrastructure containers (e.g. PostgreSQL) before building and testing the application.

---

### 🔧 Key Changes

#### ✅ New GitHub Actions Workflow File

A new workflow file named `check-build.yml` was created under `.github/workflows/`. It contains the following steps:

```yaml
name: Check build

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout sources
        uses: actions/checkout@v4

      - name: Setup Java JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: 21

      - name: Set up Docker Compose
        uses: docker/setup-compose-action@v1
        with:
          version: latest

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v4

      - name: Build with Gradle
        run: ./gradlew build
```

This ensures the CI environment mimics local development closely, with Docker services initialized before the build.

---

#### 🔧 Updated `dockerUp` Task in `app-core`

In `delivery-application/app-core/build.gradle`, the `dockerUp` task was updated to remove the hardcoded path to Docker:

```groovy
tasks.register('dockerUp', Exec) {
    group = 'infrastructure'
    description = 'Runs docker compose up -d'
    workingDir "$rootDir/delivery-infra"
    commandLine 'docker', 'compose', 'up', '-d'
}
```

This ensures compatibility across environments, especially CI agents, by relying on the system's default `docker` path.

---

### 🧪 Tips
- You can trigger this workflow manually using the `workflow_dispatch` feature or include PRs.
- Consider creating a `dockerDown` task to tear down services after testing.

---

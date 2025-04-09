# 🐳 Dev01 Deployment Workflow — Docker + Gradle Integration

## 🔧 New Commands and Capabilities

### 🔁 Local Development (Mac)
```bash
# Build and push versioned + dev01 Docker images
./gradlew :delivery-infra:delivery-docker:dockerDeployDev01
```

### 🐧 Remote Dev01 Environment (Ubuntu Server)
```bash
# SSH into server
ssh nik@192.168.1.99

# Pull latest dev01-tagged image
docker pull nkantartzopoulos/delivery-app:dev01

# Copy the dev01 docker-compose.yml to remote server
scp delivery-infra/docker-compose.dev01.yml nik@192.168.1.99:/home/nik/code/development/delivery-app/docker/

# Start or restart the stack
docker compose -f /home/nik/code/development/delivery-app/docker/docker-compose.dev01.yml up -d

# Optional: shut down the stack
docker compose -f /home/nik/code/development/delivery-app/docker/docker-compose.dev01.yml down
```

---

## 🧠 Overview

This commit introduces a clean, automated workflow to build, tag, and deploy the Dockerized `delivery-app` using Gradle. The primary goal is to support a persistent **dev01 environment** on a remote Ubuntu server, while maintaining a **lightweight and isolated deployment footprint**.

### Key Principles:
- No source code on the server
- Only Docker images and a `docker-compose.yml`
- Tagged images (`:dev01`) for stability
- Composable, reusable Gradle tasks

---

## 🔨 What Was Added or Changed

### 📦 Dockerfile (delivery-infra/delivery-docker/Dockerfile)
- A minimal Dockerfile using `eclipse-temurin:21-jdk`
- Accepts a `JAR_FILE` build argument and runs the app with `java -jar`

### 🛠️ delivery-infra/delivery-docker/build.gradle
- **`prepareDockerContext`**: Copies the JAR and Dockerfile into `build/docker`
- **`dockerBuildAppCore`**: Builds a versioned Docker image using the output from `bootJar`
- **`dockerTagAsDev01`**: Tags the built image as `:dev01`
- **`dockerPushAppCore`**: Pushes the versioned image
- **`dockerPushDev01`**: Pushes the `:dev01` tag
- **`dockerDeployDev01`**: High-level task that builds, tags, and pushes for `dev01`

### 🏷 versioning.gradle (buildSrc)
- Updated date format to include hour and minute: `yyyy-MM-dd-HH-mm`
- Ensures unique tags per image

### ⚙️ delivery-application/app-core/build.gradle
- Disabled the default `jar` task
- Enabled the `bootJar` task for Spring Boot packaging

### 🧾 docker-compose.dev01.yml
- Runs the app with the `:dev01` image tag
- Includes a Postgres service
- Configured for internal Docker networking

### 🧩 settings.gradle
- Registered `delivery-infra:delivery-docker` module

---

## 🧪 Thought Process

This update was guided by the following needs:

1. **Separation of concerns**:
    - Keep the deployment environment isolated from the source tree
    - Push only what is necessary: Docker images

2. **Reproducibility**:
    - Every image is uniquely tagged by date, time, and Git commit
    - `:dev01` is a stable alias for remote deployment

3. **Ease of use**:
    - Simple `./gradlew` commands to do complex orchestration
    - Clear separation between local builds and remote deployment

4. **Kubernetes-readiness**:
    - By separating Docker build from container orchestration, the path is open to Helm and Kubernetes


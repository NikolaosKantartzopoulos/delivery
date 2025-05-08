# 🚀 Dev01 Deployment & Socat Management Guide

This guide walks you through deploying the `dev01` environment Kubernetes manifests and verifying local `socat` port forwarding setup.

---

## 📦 1. Copy Kubernetes YAML Files

Use `scp` to copy all updated files in your local `dev01` directory to the Ubuntu server:

```bash
scp ./delivery-infra/k8s/dev01/*.yaml nik@192.168.1.99:/home/nik/code/development/delivery-app/k8s/dev01
```

---

## 🔐 2. SSH into the Remote Server

```bash
ssh nik@192.168.1.99
```

---

## ☸️ 3. Apply the Kubernetes Manifests

From within the SSH session:

```bash
kubectl apply -f /home/nik/code/development/delivery-app/k8s/dev01
```

This will create/update:
- Frontend deployment + service
- Backend deployment + service
- PostgreSQL StatefulSet + service

---

## 🧪 4. Verify Socat Port Forwarding

### List All Running `socat` Services:

```bash
ps aux | grep socat
```

This helps confirm whether your `socat` systemd services are active and forwarding ports.

---

### Start a Specific `socat` Service Manually

Example for `fe-dev01`:

```bash
sudo systemctl start socat-fe-dev01.service
```

You can also enable and check status:

```bash
sudo systemctl enable socat-fe-dev01.service
sudo systemctl status socat-fe-dev01.service
```

---

## ✅ Done!

Your dev01 environment should now be accessible via the defined domains, powered by Cloudflared and Kubernetes.


---

## 🐳 Docker Image Push Guide (Backend & Frontend)

You can push Docker images for both the backend and frontend to Docker Hub using preconfigured commands.

---

### 📦 Backend: Gradle Tasks

From the project root:

```bash
./gradlew dockerPushLatest
```

This command:
- Builds the Spring Boot JAR (`bootJar`)
- Copies it to the Docker context
- Builds the image as `nkantartzopoulos/delivery-app-backend:latest`
- Pushes it to Docker Hub

For a `dev01`-tagged variant (if defined):
```bash
./gradlew dockerDeployDev01
```

---

### 🖥 Frontend: NPM Scripts

From the `delivery-frontend` directory:

```bash
npm run docker:push:latest
```

Or to push a specific environment image:

```bash
npm run docker:push:dev01
```

These use `docker buildx` with the appropriate `TARGET_ENV` build arg and tag.

> Ensure you are logged into Docker (`docker login`) before pushing.

---


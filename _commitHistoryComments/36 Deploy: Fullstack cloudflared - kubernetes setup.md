# 🌐 Fullstack Deployment & Cloudflared Integration Overview

This update brings together backend and frontend configuration improvements, Kubernetes deployment manifests, and a robust Cloudflared tunneling setup
for development and remote accessibility.

---

## 🧩 Summary of Changes

### ✅ Backend Enhancements

- **`WebConfig.java`**:
    - Added CORS support for `localhost` and `dev01delivery.rtlan.gr`.
- **`application.properties`**:
    - Explicitly disabled SSL (`server.ssl.enabled=false`).

### 🐳 Docker

- Updated Docker image name from `delivery-app` → `delivery-app-backend`.
- Improved `dockerPushLatest` Gradle task to use `buildx` and simplify tagging/pushing.

### 🧪 Dev Environment

- Updated Docker Compose and Kubernetes specs to reflect backend image name change.
- New unified fullstack K8s YAML: `delivery-fullstack.yml`
    - Includes PostgreSQL, frontend, backend, and Ingress.

---

## 🛰️ Cloudflared Tunnel Setup

Cloudflared enables **domain-based access to localhost services**, which are exposed by `socat` from NodePort-mapped Kubernetes containers.

### 🗺 High-Level Flow:

```plaintext
Internet Request
    ↓
Cloudflare DNS + Tunnel
    ↓
localhost:PORT (via cloudflared config)
    ↓
socat service
    ↓
192.168.200.200:NODE_PORT
    ↓
Kubernetes Service → Pod
```

---

## 🗂️ File References for More Info

| File                                                                                                               | Purpose                                                  |
|--------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| [`cloudflared-local-tunnel-info.md`](../delivery-infra/ubuntu-server/cloudflared/cloudflared-local-tunnel-info.md) | Setup of cloudflared tunnel with full `ingress:`         |
| [`config.yaml`](../delivery-infra/ubuntu-server/cloudflared/config.yaml)                                           | Actual Cloudflared ingress mapping config                |
| [`port-mapping.md`](../delivery-infra/ubuntu-server/info/port-mapping.md)                                          | Full table of NodePort <-> localhost <-> domain mappings |
| [`generate-port-forward-services.sh`](../delivery-infra/ubuntu-server/scripts/generate-port-forward-services.sh)   | Bash script to generate `socat` systemd units            |

---

## 🧠 Benefits

- Clear separation between environments and builds.
- Portable deployment across Docker Compose and Kubernetes.
- Public domain-based access without exposing Kubernetes ingress externally.

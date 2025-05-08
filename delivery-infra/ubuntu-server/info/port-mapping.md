# Environments: Frontend & Backend Port Mapping

This document describes how the **frontend** and **backend** services are exposed via local ports using `socat`, and how they connect to corresponding
ports inside Kubernetes nodes.

> Note: This environment assumes Minikube is configured with a static IP address of 192.168.200.200. All port forwards target this IP to access
> services running inside the cluster.

---

## Overview

The `socat`-based setup creates **local systemd services** to forward traffic from local ports to specific ports on a Kubernetes node (
`192.168.200.200`). This helps developers access FE/BE services as if they were running locally.

Each forward follows this pattern:

```
localhost:<LOCAL_PORT> → 192.168.200.200:<REMOTE_PORT>
```

---

# 🌐 Domain Access via Cloudflared Tunneling

To provide secure, domain-based access to local services exposed by `socat`, we use **Cloudflared tunnels**. These tunnels forward requests from
public domain names to corresponding `localhost` ports on the development machine.

The configuration is defined in the Cloudflared `config.yml` under the `ingress:` section, mapping each domain to its respective internal service.

---

## 🔀 Ingress Mappings

### 💻 Frontend (FE) Services

|                   Domain | Local Target ( Ubuntu server ) |     Description |
|-------------------------:|--------------------------------|----------------:|
| `dev01delivery.rtlan.gr` | `http://localhost:8081`        | Frontend Dev 01 |
| `dev02delivery.rtlan.gr` | `http://localhost:8082`        | Frontend Dev 02 |
| `dev03delivery.rtlan.gr` | `http://localhost:8083`        | Frontend Dev 03 |
| `sit01delivery.rtlan.gr` | `http://localhost:8091`        | Frontend SIT 01 |
|      `delivery.rtlan.gr` | `http://localhost:9001`        |   Frontend Prod |

### ⚙️ Backend (BE) / API Services

|                       Domain | Local Target ( Ubuntu server ) |    Description |
|-----------------------------:|--------------------------------|---------------:|
| `dev01-be-delivery.rtlan.gr` | `http://localhost:8181`        | Backend Dev 01 |
| `dev02-be-delivery.rtlan.gr` | `http://localhost:8182`        | Backend Dev 02 |
| `dev03-be-delivery.rtlan.gr` | `http://localhost:8183`        | Backend Dev 03 |
| `sit04-be-delivery.rtlan.gr` | `http://localhost:8893`        | Backend SIT 04 |
|       `be-delivery.rtlan.gr` | `http://localhost:9801`        |   Backend Prod |

> `http2Origin: false` is used on backend entries to ensure compatibility with HTTP/1.1-based Spring Boot services.

---

## 🧰 Benefits

* Enables public or DNS-based access to local services.
* Facilitates collaboration, remote QA, and mobile testing.
* Secure, TLS-encrypted access without exposing NodePort or Ingress externally.

---

## 🌐 Frontend Port Mapping

These entries forward traffic to the **frontend containers** running inside the cluster:

| Local Port ( Ubuntu server ) | Minikube Port | Identifier | Description      |
|------------------------------|---------------|------------|------------------|
| 8081                         | 30081         | fe-dev01   | Frontend Dev 01  |
| 8082                         | 30082         | fe-dev02   | Frontend Dev 02  |
| 8083                         | 30083         | fe-dev03   | Frontend Dev 03  |
| 8091                         | 30091         | fe-sit01   | Frontend SIT 01  |
| 9001                         | 30901         | fe-prod01  | Frontend Prod 01 |

These map to exposed container ports of each FE pod or service (e.g., typically running React or Vue apps on port `80` or `3000` internally).

---

## ⚙️ Backend / API Port Mapping

These entries forward traffic to the **backend (Spring Boot API)** services:

| Local Port ( Ubuntu server ) | Minikube Port | Identifier | Description    |
|------------------------------|---------------|------------|----------------|
| 8181                         | 30181         | be-dev01   | Backend Dev 01 |
| 8182                         | 30182         | be-dev02   | Backend Dev 02 |
| 8183                         | 30183         | be-dev03   | Backend Dev 03 |
| 8891                         | 30191         | be-sit04   | Backend SIT 04 |
| 9051                         | 30951         | be-prod    | Backend Prod   |

These typically expose the Spring Boot app on port `8080` within the container, routed externally via NodePort services.

---

## 🔐 Notes

* All forwards are managed by `systemd` via dynamically generated services (e.g. `socat-fe-dev01.service`).
* Restart policies ensure services persist across reboots.
* Useful for local testing, Postman, or frontend app development without ingress dependency.

---

## 🚀 Target Node

All forwards are directed to:

```
192.168.200.200 (Kubernetes Node)
```

---

## Dependencies

Make sure `socat` is installed:

```bash
sudo apt install socat
```

---

## ✅ Usage

After running the script, you can access services like:

* Frontend Dev 01: [http://localhost:8081](http://localhost:8081)
* Backend Dev 01: [http://localhost:8181](http://localhost:8181)

These mimic production-like paths for isolated, stable development.

---

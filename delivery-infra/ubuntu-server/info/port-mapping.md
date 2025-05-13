# Environments: Frontend, Backend, Prometheus & Grafana Port Mapping

This document describes how the **frontend**, **backend**, **Prometheus**, and **Grafana** services are exposed via local ports using `socat`, and how
they connect to corresponding ports inside Kubernetes nodes.

> Note: This environment assumes Minikube is configured with a static IP address of 192.168.200.200. All port forwards target this IP to access
> services running inside the cluster.

---

## Overview

The `socat`-based setup creates **local systemd services** to forward traffic from local ports to specific ports on a Kubernetes node (
`192.168.200.200`). This helps developers access FE/BE/monitoring services as if they were running locally.

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

|                    Domain | Local Target (Ubuntu server) |     Description |
|--------------------------:|------------------------------|----------------:|
| `delivery-dev01.rtlan.gr` | `http://localhost:30101`     | Frontend Dev 01 |
| `delivery-dev02.rtlan.gr` | `http://localhost:30102`     | Frontend Dev 02 |
| `delivery-dev03.rtlan.gr` | `http://localhost:30103`     | Frontend Dev 03 |
| `delivery-sit01.rtlan.gr` | `http://localhost:30111`     | Frontend SIT 01 |
|       `delivery.rtlan.gr` | `http://localhost:30121`     |   Frontend Prod |

### ⚙️ Backend (BE) / API Services

|                       Domain | Local Target (Ubuntu server) |    Description |
|-----------------------------:|------------------------------|---------------:|
| `be-delivery-dev01.rtlan.gr` | `http://localhost:30201`     | Backend Dev 01 |
| `be-delivery-dev02.rtlan.gr` | `http://localhost:30202`     | Backend Dev 02 |
| `be-delivery-dev03.rtlan.gr` | `http://localhost:30203`     | Backend Dev 03 |
| `be-delivery-sit04.rtlan.gr` | `http://localhost:30211`     | Backend SIT 04 |
|       `be-delivery.rtlan.gr` | `http://localhost:30221`     |   Backend Prod |

### 📊 Prometheus Services

|                               Domain | Local Target (Ubuntu server) |       Description |
|-------------------------------------:|------------------------------|------------------:|
| `prometheus-delivery-dev01.rtlan.gr` | `http://localhost:30301`     | Prometheus Dev 01 |
| `prometheus-delivery-dev02.rtlan.gr` | `http://localhost:30302`     | Prometheus Dev 02 |
| `prometheus-delivery-dev03.rtlan.gr` | `http://localhost:30303`     | Prometheus Dev 03 |
| `prometheus-delivery-sit01.rtlan.gr` | `http://localhost:30311`     | Prometheus SIT 01 |
|       `prometheus-delivery.rtlan.gr` | `http://localhost:30321`     |   Prometheus Prod |

### 📈 Grafana Dashboards

|                            Domain | Local Target (Ubuntu server) |    Description |
|----------------------------------:|------------------------------|---------------:|
| `grafana-delivery-dev01.rtlan.gr` | `http://localhost:30401`     | Grafana Dev 01 |
| `grafana-delivery-dev02.rtlan.gr` | `http://localhost:30402`     | Grafana Dev 02 |
| `grafana-delivery-dev03.rtlan.gr` | `http://localhost:30403`     | Grafana Dev 03 |
| `grafana-delivery-sit01.rtlan.gr` | `http://localhost:30411`     | Grafana SIT 01 |
|       `grafana-delivery.rtlan.gr` | `http://localhost:30421`     |   Grafana Prod |

> `http2Origin: false` is used on backend entries to ensure compatibility with HTTP/1.1-based Spring Boot services.

---

## 🧰 Benefits

* Enables public or DNS-based access to local services.
* Facilitates collaboration, remote QA, and mobile testing.
* Secure, TLS-encrypted access without exposing NodePort or Ingress externally.

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

* Frontend Dev 01: [http://localhost:30101](http://localhost:30101)
* Backend Dev 01: [http://localhost:30201](http://localhost:30201)
* Prometheus Dev 01: [http://localhost:30301](http://localhost:30301)
* Grafana Dev 01: [http://localhost:30401](http://localhost:30401)

These mimic production-like paths for isolated, stable development.

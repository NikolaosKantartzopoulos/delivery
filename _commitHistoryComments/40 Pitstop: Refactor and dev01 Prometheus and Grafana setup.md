# Port and Infrastructure Synchronization: Refactor Summary

**Date:** 2025-05-13  
**Environment:** `dev01`, cloudflared, Kubernetes, socat, frontend/backend monitoring stack

---

## 📌 Impact

- Ensures **Kubernetes compatibility** for NodePorts
- Enables **Cloudflare access** to all mapped environments via consistent subdomains
- Facilitates **external team testing**, Postman integration, and seamless local tunnel routing

---

## ✅ Summary

This update synchronizes service port exposure, tunnel routing, and documentation across the development infrastructure stack. It primarily focuses on
**refactoring external NodePort values** and updating configurations and documentation to reflect this unified and valid setup.

---

## 🎯 Key Goals

- Replace **non-Kubernetes-compliant NodePorts** (e.g., `52101`, `54101`) with valid values inside `30000–32767`.
- Normalize all port usage across:
    - Kubernetes manifests
    - Cloudflared config files
    - Port-forwarding scripts (systemd + socat)
    - Developer documentation

---

## 🔧 Technical Highlights

### 🌍 Cloudflared Tunnel

- **Renamed hostnames** to consistent format:  
  `dev01delivery.rtlan.gr` → `delivery-dev01.rtlan.gr`  
  `dev01-be-delivery.rtlan.gr` → `be-delivery-dev01.rtlan.gr`
- **Tunnel services** remapped to correct local ports:
    - Frontend Dev01 → `localhost:30101`
    - Backend Dev01 → `localhost:30201`
    - Prometheus Dev01 → `localhost:30301`
    - Grafana Dev01 → `localhost:30401`

### 🛠 Kubernetes Manifests

- `NodePort` entries updated to match system port plan:
    - Backend service: `30201`
    - Prometheus: `30301`
    - Grafana: `30401`
    - Frontend: `30101`
- Added missing labels and normalized service names (`delivery-backend-svc`, etc.)

### 🌐 Environment & Code Changes

- `.env.dev01`: `VITE_API_BASE_URL` corrected to `https://be-delivery-dev01.rtlan.gr/api`
- `WebConfig.java`: `allowedOrigins` CORS header updated from `https://dev01delivery.rtlan.gr` → `https://delivery-dev01.rtlan.gr`

### 🛡️ Socat Systemd Scripts

- `generate-port-forward-services.sh` updated to generate:
    - Valid local→remote `socat` bridges
    - All ports now in the `30000–32767` range

### 📄 Docs Updated

- `port-mapping.md` expanded to include:
    - Prometheus and Grafana sections
    - Updated tables for frontend/backend services
- `cloudflared-local-tunnel-info.md` and `config.yaml`:
    - Fully aligned with real port mappings and tunnel hostnames

### Set Up Grafana Dashboard in deployed dev01

1. Open Grafana at `localhost:3000`
2. Go to **"Data sources" > "Add data source"**
3. Add a new data source of type **Prometheus**
    - Set the URL to: `http://prometheus-svc:9090`
    - Click **Save & Test**

4. Create a new **Dashboard**

    - Click **Add Visualization**
    - Choose the Prometheus data source just configured
    - Use queries like:
        - `api_food_category_create_total`
        - `api_food_category_get_all_total`
        - `api_food_category_delete_total`
        - `api_food_category_delete_all_total`

5. Press **Run Query** and **Refresh** regularly to see live data.

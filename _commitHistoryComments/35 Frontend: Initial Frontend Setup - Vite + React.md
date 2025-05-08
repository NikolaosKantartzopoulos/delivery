# 🧱 Initial Frontend Setup: Vite + React

This commit introduces the full scaffolding of the **`delivery-frontend`** app using **Vite** and **React**, structured to support multiple environments with Docker, static deployment, and runtime configuration through `.env` files.

---

## ✨ Highlights

### 📁 Environment Configuration

- `.env`: Local development defaults (API base URL, frontend name, environment)
- `.env.dev01`: Production-like cloud environment with backend tunnel domain

### 🐳 Docker Support

- `Dockerfile`:
    - Multi-stage build using Node 18 Alpine
    - Supports build argument `TARGET_ENV` for dynamic builds
    - Final container serves static content via `serve` on port 7900

### 📦 Project Structure

- `index.html`, `App.jsx`, `main.jsx`: Minimal React app with Vite entry points
- `App.css`, `index.css`: Responsive styles with form/table layouts
- `public/burger.svg`: Branded favicon/icon
- `vite.config.js`: Configured to serve on port 7900 with strict port mode

### 🔧 ESLint

- Configured with:
    - `eslint-plugin-react-hooks`
    - `eslint-plugin-react-refresh`
    - Modern globals and JSX parsing

### 📜 Scripts & Build Commands

- `npm run build:dev01` → builds with dev01 settings
- `npm run docker:push:latest` and `docker:push:dev01` → Docker image builds and pushes

---

## 📌 Goals & Next Steps

This setup prepares the project for:
- Easy local development and environment switching
- Deployment with Helm/Cloudflared using domain-based access
- Integration with backend API and category management


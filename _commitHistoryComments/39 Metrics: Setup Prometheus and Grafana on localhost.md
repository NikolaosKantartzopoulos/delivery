# Metrics: Prometheus and Grafana Local Setup

This guide describes how to set up Prometheus and Grafana locally to monitor your Spring Boot application.

---

## 🧾 Summary of Commit Changes: Prometheus and Grafana Local Setup

This commit integrates **Prometheus** and **Grafana** into the local development environment for observability and monitoring. It focuses on setting up Prometheus to scrape metrics from the Spring Boot actuator endpoint and visualizing these metrics using Grafana dashboards.

### ✅ Key Changes Introduced

#### 1. **Metric Exposure Configuration**

- In `application.properties`, Prometheus metrics exposure is enabled via:
  ```properties
  management.endpoints.web.exposure.include=*
  management.endpoint.prometheus.access=unrestricted
  management.prometheus.metrics.export.enabled=true
  ```

#### 2. **Prometheus and Grafana Containers**

- The `docker-compose.yml` file is extended with two new services:
    - `prometheus`: Configured to scrape from `host.docker.internal:8080/actuator/prometheus`.
    - `grafana`: Connected to the same `monitoring` network, with default credentials `admin/admin`.

#### 3. **Prometheus Scrape Configuration**

- A new `config/prometheus.yml` file is introduced with the job:
  ```yaml
  job_name: "backend"
  metrics_path: "/actuator/prometheus"
  static_configs:
    - targets: ["host.docker.internal:8080"]
  ```

#### 4. **Spring Boot Integration with Micrometer**

- `libs.micrometer.prometheus` is added to `module-runtime.java-conventions.gradle`.
- In `FoodCategoryRestApi`, Micrometer `Counter` metrics are registered and incremented for the following operations:
    - `api_food_category_create`
    - `api_food_category_get_all`
    - `api_food_category_delete`
    - `api_food_category_delete_all`

#### 5. **Testing Enhancements**

- Unit tests now inject a `SimpleMeterRegistry` into the `FoodCategoryRestApi` to test metric-aware behaviors.

---

## 📘 Guide: Running Prometheus & Grafana Locally with Spring Boot

Start the app with:

```bash
./gradlew bootRun
```

### Endpoints to Know

- **Spring Boot App**:

    - Metrics: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)
    - Health: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
    - Base: [http://localhost:8080/](http://localhost:8080/)

- **Prometheus**:

    - UI: [http://localhost:9090/](http://localhost:9090/)
    - Query metrics: [http://localhost:9090/query](http://localhost:9090/query)

- **Grafana**:
    - Dashboard: [http://localhost:3000/](http://localhost:3000/)
    - Login: user `admin`, password `admin`

### Set Up Grafana Dashboard

1. Open Grafana at `localhost:3000`
2. Go to **"Data sources" > "Add data source"**
3. Add a new data source of type **Prometheus**
    - Set the URL to: `http://host.docker.internal:9090`
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

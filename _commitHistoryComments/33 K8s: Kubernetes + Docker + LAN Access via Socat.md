# 🚀 Delivery App: Kubernetes + Docker + LAN Access via Socat

This update improves our Kubernetes and Docker workflows by:

- Adding support for a `latest` Docker tag
- Updating the Kubernetes deployment and service definitions
- Enabling external access from the LAN via NodePort and `socat`

---

## ✅ Summary of Changes

### 🔧 Spring Boot Controller

- The `HomepageController` now displays:
  - Hostname
  - Local IP address

Used to confirm which pod handled a request (for debugging load balancing).

---

### 🐳 Docker Build Updates

New Gradle tasks added to tag and push Docker images as `latest`:

- `dockerTagAsLatest`: Tags the most recently built image as `latest`
- `dockerPushLatest`: Pushes the `latest` tag to Docker Hub

#### Usage:

```bash
./gradlew :delivery-infra:delivery-docker:dockerPushLatest
```

---

### ☸️ Kubernetes Updates

#### Deployment:
- Replicas increased to 3
- Uses image `nkantartzopoulos/delivery-app:latest`
- Active Spring profile set to `prod`

#### Service:
- Exposed via `type: NodePort` on port `30081`
- Allows direct access from host or LAN using `socat`

#### Postgres:
- StatefulSet persists volume with `volumeClaimTemplates`
- Retains data across pod restarts

---

## 🌐 LAN Access with `socat`

Minikube runs on `192.168.200.200`, which is **not always directly accessible from the LAN**.

To bridge this, we use [`socat`](https://linux.die.net/man/1/socat) to forward traffic from the host IP (e.g., `192.168.1.99`) to the Minikube NodePort.

### Example

```bash
http://192.168.1.99:8081 → socat → 192.168.200.200:30081
```

This allows any LAN device to reach your service using a friendly local port.

---

## 🔧 Automated Socat Setup with systemd

The script below:

- Creates persistent `systemd` services for each port forward
- Enables and starts them automatically on boot

---

### 🛠️ Script: `generate-port-forward-services.sh`

```bash
#!/bin/bash

# Define your port mappings here: local_port:remote_port:name
FORWARDS=(
  "8081:30081:dev01"
  "8082:30082:dev02"
  "8083:30083:dev03"
  "8091:30091:sit01"
  "9001:30901:prod01"
)

# Minikube IP to forward to
MINIKUBE_IP="192.168.200.200"
SOCAT_PATH="$(which socat)"

if [ -z "$SOCAT_PATH" ]; then
  echo "❌ socat not found. Please install it first: sudo apt install socat"
  exit 1
fi

for mapping in "${FORWARDS[@]}"; do
  IFS=":" read -r LOCAL REMOTE NAME <<< "$mapping"
  SERVICE_NAME="k8s-forward-$NAME.service"
  SERVICE_PATH="/etc/systemd/system/$SERVICE_NAME"

  echo "🔧 Creating $SERVICE_NAME (host:$LOCAL → $MINIKUBE_IP:$REMOTE)"

  sudo tee "$SERVICE_PATH" > /dev/null <<EOF
[Unit]
Description=Forward host:$LOCAL to Minikube:$REMOTE for $NAME
After=network.target

[Service]
ExecStart=$SOCAT_PATH TCP4-LISTEN:$LOCAL,fork TCP4:$MINIKUBE_IP:$REMOTE
Restart=always
RestartSec=3
User=root

[Install]
WantedBy=multi-user.target
EOF

  # Reload and enable the service
  sudo systemctl daemon-reload
  sudo systemctl enable "$SERVICE_NAME"
  sudo systemctl restart "$SERVICE_NAME"
done

echo "✅ All socat forwards installed and started."
```

---

### ✅ How to Use

1. Save the script as `generate-port-forward-services.sh`
2. Make it executable:
   ```bash
   chmod +x generate-port-forward-services.sh
   ```
3. Run it with sudo:
   ```bash
   sudo ./generate-port-forward-services.sh
   ```

---

## 🧪 Test from a LAN Device

```bash
curl http://192.168.1.99:8081
```

This should return the Delivery app homepage served by one of the Kubernetes pods.

---

## 📌 Notes

- `socat` forwards can be managed via:
  ```bash
  sudo systemctl status k8s-forward-dev01.service
  sudo systemctl restart k8s-forward-dev01.service
  ```

- You can view all running forwards:
  ```bash
  sudo systemctl list-units --type=service | grep k8s-forward
  ```

- Log output:
  ```bash
  journalctl -u k8s-forward-dev01.service
  ```

---

Happy deploying 🚀

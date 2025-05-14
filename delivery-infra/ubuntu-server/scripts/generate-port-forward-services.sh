#!/bin/bash

# Define your port mappings here: local_port:remote_port:name
FORWARDS=(
  # 🌐 Frontend
  "30101:30101:fe-dev01"
  "30102:30102:fe-dev02"
  "30103:30103:fe-dev03"
  "30111:30111:fe-sit01"
  "30121:30121:fe-prod01"

  # ⚙️ Backend / API
  "30201:30201:be-dev01"
  "30202:30202:be-dev02"
  "30203:30203:be-dev03"
  "30211:30211:be-sit04"
  "30221:30221:be-prod"

  # 📊 Prometheus
  "30301:30301:prometheus-dev01"
  "30302:30302:prometheus-dev02"
  "30303:30303:prometheus-dev03"
  "30311:30311:prometheus-sit01"
  "30321:30321:prometheus-prod"

  # 📊 Grafana Dashboards
  "30401:30401:grafana-dev01"
  "30402:30402:grafana-dev02"
  "30403:30403:grafana-dev03"
  "30411:30411:grafana-sit01"
  "30421:30421:grafana-prod"
)

# K8s node IP to forward to
TARGET_IP="192.168.200.200"
SOCAT_PATH="$(which socat)"

if [ -z "$SOCAT_PATH" ]; then
  echo "❌ socat not found. Please install it first: sudo apt install socat"
  exit 1
fi

for mapping in "${FORWARDS[@]}"; do
  IFS=":" read -r LOCAL REMOTE NAME <<< "$mapping"
  SERVICE_NAME="socat-$NAME.service"
  SERVICE_PATH="/etc/systemd/system/$SERVICE_NAME"

  echo "🔧 Creating $SERVICE_NAME (host:$LOCAL → $TARGET_IP:$REMOTE)"

  sudo tee "$SERVICE_PATH" > /dev/null <<EOF
[Unit]
Description=Port forward $NAME (localhost:$LOCAL → $TARGET_IP:$REMOTE)
After=network.target

[Service]
ExecStart=$SOCAT_PATH TCP4-LISTEN:$LOCAL,fork TCP4:$TARGET_IP:$REMOTE
Restart=always
RestartSec=3
User=root

[Install]
WantedBy=multi-user.target
EOF

  sudo systemctl daemon-reload
  sudo systemctl enable "$SERVICE_NAME"
  sudo systemctl restart "$SERVICE_NAME"
done

echo "✅ All socat forwards installed and started."

#!/bin/bash

# Define your port mappings here: local_port:remote_port:name
FORWARDS=(
  # 🌐 Frontend
  "8081:30081:fe-dev01"
  "8082:30082:fe-dev02"
  "8083:30083:fe-dev03"
  "8091:30091:fe-sit01"
  "9001:30901:fe-prod01"

  # ⚙️ Backend / API
  "8181:30181:be-dev01"
  "8182:30182:be-dev02"
  "8183:30183:be-dev03"
  "8891:30191:be-sit04"
  "9051:30951:be-prod"
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

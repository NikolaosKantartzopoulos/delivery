# [Cloudflared local tunnel](https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/do-more-with-tunnels/local-management/create-local-tunnel/)

## Map hostname to service
`/root/.cloudflared/config.yaml`
```yaml
tunnel: the-tunnel-uuid
credentials-file: /home/nik/.cloudflared/the-tunnel-uuid.json

ingress:
  # 🌐 Frontend environments
  - hostname: dev01delivery.rtlan.gr
    service: http://localhost:8081
  - hostname: dev02delivery.rtlan.gr
    service: http://localhost:8082
  - hostname: dev03delivery.rtlan.gr
    service: http://localhost:8083
  - hostname: sit01delivery.rtlan.gr
    service: http://localhost:8091
  - hostname: delivery.rtlan.gr
    service: http://localhost:9001

  # ⚙️ Backend / API services
  - hostname: dev01-be-delivery.rtlan.gr
    service: http://localhost:8181
    originRequest:
      http2Origin: false
  - hostname: dev02-be-delivery.rtlan.gr
    service: http://localhost:8182
    originRequest:
      http2Origin: false
  - hostname: dev03-be-delivery.rtlan.gr
    service: http://localhost:8183
    originRequest:
      http2Origin: false
  - hostname: sit04-be-delivery.rtlan.gr
    service: http://localhost:8893
    originRequest:
      http2Origin: false
  - hostname: be-delivery.rtlan.gr
    service: http://localhost:9801
    originRequest:
      http2Origin: false

  # 🛑 Default catch-all
  - service: http_status:404
```

## Systemd file to start
```bash
root@ubuntu-server:/#  cat /etc/systemd/system/cloudflared.service
[Unit]
Description=cloudflared
After=network-online.target
Wants=network-online.target

[Service]
TimeoutStartSec=0
Type=notify
ExecStart=/usr/bin/cloudflared --no-autoupdate tunnel run cloudflared-local-tunnel
Restart=on-failure
RestartSec=5s

[Install]
WantedBy=multi-user.target
```

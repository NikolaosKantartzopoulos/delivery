# [Cloudflared local tunnel](https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/do-more-with-tunnels/local-management/create-local-tunnel/)

## Map hostname to service

Ports are mapped in :**30ABC** format:
- A: 1 Frontend, 2 Backend, 3 Prometheus, 4 Grafana
- B: 1 Dev environment, 2 SIT environment, 3 prod environment
- C: Id of env


`/root/.cloudflared/config.yaml`
```yaml
tunnel: the-tunnel-uuid
credentials-file: /home/nik/.cloudflared/the-tunnel-uuid.json

ingress:
    # 🌐 Frontend environments
    - hostname: delivery-dev01.rtlan.gr
      service: http://localhost:30101

    - hostname: delivery-dev02.rtlan.gr
      service: http://localhost:30102

    - hostname: delivery-dev03.rtlan.gr
      service: http://localhost:30103

    - hostname: delivery-sit01.rtlan.gr
      service: http://localhost:30111

    - hostname: delivery.rtlan.gr
      service: http://localhost:30121

    # ⚙️ Backend / API services
    - hostname: be-delivery-dev01.rtlan.gr
      service: http://localhost:30201
      originRequest:
          http2Origin: false

    - hostname: be-delivery-dev02.rtlan.gr
      service: http://localhost:30202
      originRequest:
          http2Origin: false

    - hostname: be-delivery-dev03.rtlan.gr
      service: http://localhost:30203
      originRequest:
          http2Origin: false

    - hostname: be-delivery-sit04.rtlan.gr
      service: http://localhost:30211
      originRequest:
          http2Origin: false

    - hostname: be-delivery.rtlan.gr
      service: http://localhost:30221
      originRequest:
          http2Origin: false

    # 📊 Prometheus (per environment)
    - hostname: prometheus-delivery-dev01.rtlan.gr
      service: http://localhost:30301
      originRequest:
          http2Origin: false

    - hostname: prometheus-delivery-dev02.rtlan.gr
      service: http://localhost:30302
      originRequest:
          http2Origin: false

    - hostname: prometheus-delivery-dev03.rtlan.gr
      service: http://localhost:30303
      originRequest:
          http2Origin: false

    - hostname: prometheus-delivery-sit01.rtlan.gr
      service: http://localhost:30311
      originRequest:
          http2Origin: false

    - hostname: prometheus-delivery.rtlan.gr
      service: http://localhost:30321
      originRequest:
          http2Origin: false

    # 📊 Grafana dashboards (per environment)
    - hostname: grafana-delivery-dev01.rtlan.gr
      service: http://localhost:30401
      originRequest:
          http2Origin: false

    - hostname: grafana-delivery-dev02.rtlan.gr
      service: http://localhost:30402
      originRequest:
          http2Origin: false

    - hostname: grafana-delivery-dev03.rtlan.gr
      service: http://localhost:30403
      originRequest:
          http2Origin: false

    - hostname: grafana-delivery-sit01.rtlan.gr
      service: http://localhost:30411
      originRequest:
          http2Origin: false

    - hostname: grafana-delivery.rtlan.gr
      service: http://localhost:30421
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

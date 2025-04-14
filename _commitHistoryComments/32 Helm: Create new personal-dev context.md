# Helm: Create new personal-dev context

## Create new personal-dev context
```bash
kubectl config set-context personal-dev \
  --cluster=minikube \
  --user=minikube \
  --namespace=default
```

## Activate context

```bash
kubectl config use-context personal-dev
```
## Check Current Context

```bash
kubectl config current-context
```

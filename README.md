# hpa-demo — Lab11 (Spring Boot/K8S)

CPU-bound Spring Boot service used to demonstrate Kubernetes Horizontal Pod Autoscaler (HPA) scaling behavior on Minikube.

## Local run (no Kubernetes)

```bash
docker compose up --build
curl "http://localhost:8080/api/cpu/burn?upperBound=200000"
curl "http://localhost:8080/api/status"
```

## Run on Minikube

```bash
# 1. Point the local Docker CLI at Minikube's Docker daemon
eval $(minikube docker-env)
docker build -t hpa-demo:1.0.0 .

# 2. Enable the metrics pipeline HPA depends on
minikube addons enable metrics-server

# 3. Deploy
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/hpa.yaml

# 4. Watch
kubectl get hpa hpa-demo -w
```

## Generate load

```bash
kubectl run load-generator --image=busybox:1.36 --restart=Never -it --rm -- /bin/sh -c \
  "while true; do wget -q -O- http://hpa-demo.default.svc.cluster.local/api/cpu/burn?upperBound=800000; done"
```

Full walkthrough, HPA algorithm explanation, and production pitfalls: see `post.md` in the parent directory.

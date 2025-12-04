#!/bin/bash
# Script de exemplo para deploy em GKE

# Criar cluster
gcloud container clusters create fortepix-cluster --num-nodes=3 --region=us-central1

# Obter credenciais
gcloud container clusters get-credentials fortepix-cluster --region=us-central1

# Aplicar manifests Kubernetes
kubectl apply -f k8s/deployment-java.yaml
kubectl apply -f k8s/deployment-python.yaml
kubectl apply -f k8s/service-java.yaml
kubectl apply -f k8s/service-python.yaml
kubectl apply -f k8s/ingress.yaml

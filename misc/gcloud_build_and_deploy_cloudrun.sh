#!/bin/bash
# Script de exemplo para build e deploy em Cloud Run

# Build imagem Java
gcloud builds submit --tag gcr.io/SEU_PROJETO/fortepix-java:beta ./java-api

# Build imagem Python
gcloud builds submit --tag gcr.io/SEU_PROJETO/fortepix-python:beta ./python-api

# Deploy Java em Cloud Run
gcloud run deploy fortepix-java   --image gcr.io/SEU_PROJETO/fortepix-java:beta   --platform managed   --region us-central1   --allow-unauthenticated

# Deploy Python em Cloud Run
gcloud run deploy fortepix-python   --image gcr.io/SEU_PROJETO/fortepix-python:beta   --platform managed   --region us-central1   --allow-unauthenticated

#!/bin/bash
# Script de exemplo para inicialização do gcloud

gcloud init
gcloud auth login
gcloud config set project SEU_PROJETO_ID
gcloud config set compute/region us-central1
gcloud config set compute/zone us-central1-a

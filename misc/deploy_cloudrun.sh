#!/bin/bash
# ============================================================
# Deploy FortePix API Java no Cloud Run (Google Cloud)
# Usando Artifact Registry como repositório de imagens
# ============================================================

set -e

# --------- PARAMETROS A EDITAR ANTES DE USAR -----------------
PROJETO_ID="SEU_PROJETO_ID_AQUI"       # ex.: fortepix-prod
REGIAO="us-central1"                   # ex.: southamerica-east1 (São Paulo) ou us-central1
REPO_NAME="fortepix-api-repo"          # nome do repositório Artifact Registry
SERVICE_NAME="fortepix-api-java"       # nome do serviço no Cloud Run
IMAGE_NAME="fortepix-api-java"         # nome da imagem
# -------------------------------------------------------------

echo ">>> Verificando gcloud..."
if ! command -v gcloud &> /dev/null; then
  echo "ERRO: gcloud não encontrado. Instale o Google Cloud SDK."
  exit 1
fi

echo ">>> Configurando projeto e região..."
gcloud config set project "${PROJETO_ID}"
gcloud config set compute/region "${REGIAO}"

echo ">>> Ativando APIs necessárias (Run, Artifact Registry, Cloud Build)..."
gcloud services enable run.googleapis.com
gcloud services enable artifactregistry.googleapis.com
gcloud services enable cloudbuild.googleapis.com

echo ">>> Criando repositório Artifact Registry (se não existir)..."
EXISTE_REPO=$(gcloud artifacts repositories list   --location="${REGIAO}"   --format="value(name)" | grep "${REPO_NAME}" || true)

if [ -z "${EXISTE_REPO}" ]; then
  gcloud artifacts repositories create "${REPO_NAME}"     --repository-format=docker     --location="${REGIAO}"     --description="Repositório da FortePix API Java"
else
  echo "Repositório ${REPO_NAME} já existe."
fi

IMAGE_URI="${REGIAO}-docker.pkg.dev/${PROJETO_ID}/${REPO_NAME}/${IMAGE_NAME}:latest"

echo ">>> Build e push da imagem usando Cloud Build..."
gcloud builds submit . --tag "${IMAGE_URI}"

echo ">>> Deploy no Cloud Run..."
gcloud run deploy "${SERVICE_NAME}"   --image "${IMAGE_URI}"   --platform managed   --region "${REGIAO}"   --allow-unauthenticated

echo ">>> URLs do serviço:"
gcloud run services describe "${SERVICE_NAME}"   --region "${REGIAO}"   --format="value(status.url)"

echo "=================================================="
echo "Deploy concluído. Edite PROJETO_ID / REGIAO / REPO_NAME se necessário."
echo "=================================================="

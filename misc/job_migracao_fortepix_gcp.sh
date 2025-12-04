#!/bin/bash
# ===========================================================
# FortePix - Job 1: Migração da Nova Versão da API para GCP
# Stack: Artifact Registry + Cloud Run
# ===========================================================

PROJETO_ID="SEU_PROJETO_ID_AQUI"     # ex.: fortepix-prod
REGIAO="us-central1"                 # ex.: us-central1
REPO_NAME="fortepix-repo"

JAVA_IMAGE_NAME="fortepix-java-api"
PY_IMAGE_NAME="fortepix-python-api"

JAVA_SERVICE_NAME="fortepix-java"
PY_SERVICE_NAME="fortepix-python"

JAVA_API_DIR="./java-api"
PYTHON_API_DIR="./python-api"

echo ">>> Verificando gcloud instalado..."
if ! command -v gcloud &> /dev/null
then
    echo "ERRO: gcloud não encontrado. Instale o Google Cloud SDK antes."
    exit 1
fi

echo ">>> Verificando autenticação atual do gcloud:"
gcloud auth list
echo "Certifique-se de que a conta correta (ex.: gabimbq3@gmail.com) está ativa."
read -p "Pressione ENTER para continuar..." _

echo ">>> Configurando projeto e região..."
gcloud config set project "$PROJETO_ID"
gcloud config set compute/region "$REGIAO"

echo ">>> Ativando serviços necessários (Run, Artifact Registry, Cloud Build)..."
gcloud services enable run.googleapis.com
gcloud services enable artifactregistry.googleapis.com
gcloud services enable cloudbuild.googleapis.com

echo ">>> Verificando/CRIANDO repositório Artifact Registry: $REPO_NAME"
EXISTE_REPO=$(gcloud artifacts repositories list --location="$REGIAO" --format="value(name)" | grep "$REPO_NAME" || true)
if [ -z "$EXISTE_REPO" ]; then
  gcloud artifacts repositories create "$REPO_NAME"     --repository-format=docker     --location="$REGIAO"     --description="Repositório FortePix para imagens da API"
fi

echo ">>> Build nova versão API JAVA..."
if [ ! -d "$JAVA_API_DIR" ]; then
  echo "ERRO: Pasta $JAVA_API_DIR não encontrada."
  exit 1
fi

JAVA_IMAGE_URI="$REGIAO-docker.pkg.dev/$PROJETO_ID/$REPO_NAME/$JAVA_IMAGE_NAME:latest"
gcloud builds submit "$JAVA_API_DIR" --tag "$JAVA_IMAGE_URI"

echo ">>> Build nova versão API PYTHON..."
if [ ! -d "$PYTHON_API_DIR" ]; then
  echo "ERRO: Pasta $PYTHON_API_DIR não encontrada."
  exit 1
fi

PY_IMAGE_URI="$REGIAO-docker.pkg.dev/$PROJETO_ID/$REPO_NAME/$PY_IMAGE_NAME:latest"
gcloud builds submit "$PYTHON_API_DIR" --tag "$PY_IMAGE_URI"

echo ">>> Deploy API JAVA em Cloud Run..."
gcloud run deploy "$JAVA_SERVICE_NAME"   --image "$JAVA_IMAGE_URI"   --platform managed   --region "$REGIAO"   --allow-unauthenticated

echo ">>> Deploy API PYTHON em Cloud Run..."
gcloud run deploy "$PY_SERVICE_NAME"   --image "$PY_IMAGE_URI"   --platform managed   --region "$REGIAO"   --allow-unauthenticated

JAVA_URL=$(gcloud run services describe "$JAVA_SERVICE_NAME" --region="$REGIAO" --format="value(status.url)")
PY_URL=$(gcloud run services describe "$PY_SERVICE_NAME" --region="$REGIAO" --format="value(status.url)")

echo "=================================================="
echo "MIGRAÇÃO (DEPLOY) CONCLUÍDA."
echo "URL API JAVA:   $JAVA_URL"
echo "URL API PYTHON: $PY_URL"
echo "=================================================="

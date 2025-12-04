#!/bin/bash
# ===========================================================
# FortePix - Job 2: Conectar APIs ao Cloud SQL (Banco)
# ===========================================================

PROJETO_ID="SEU_PROJETO_ID_AQUI"
REGIAO="us-central1"

JAVA_SERVICE_NAME="fortepix-java"
PY_SERVICE_NAME="fortepix-python"

CONN_NAME="SEU_PROJETO:us-central1:fortepix-db"

DB_NAME="fortepix"
DB_USER="fortepix_user"
DB_PASS="SENHA_FORTE_AQUI"

gcloud config set project "$PROJETO_ID"
gcloud config set compute/region "$REGIAO"

echo ">>> Atualizando serviço JAVA com Cloud SQL + env vars..."

gcloud run services update "$JAVA_SERVICE_NAME"   --region="$REGIAO"   --add-cloudsql-instances="$CONN_NAME"   --set-env-vars="DB_URL=jdbc:postgresql:///$DB_NAME?cloudSqlInstance=$CONN_NAME&socketFactory=com.google.cloud.sql.postgres.SocketFactory,DB_USER=$DB_USER,DB_PASS=$DB_PASS"

echo ">>> Atualizando serviço PYTHON com Cloud SQL + env vars..."

gcloud run services update "$PY_SERVICE_NAME"   --region="$REGIAO"   --add-cloudsql-instances="$CONN_NAME"   --set-env-vars="DB_URL=$DB_NAME,DB_USER=$DB_USER,DB_PASS=$DB_PASS"

echo ">>> Job 2 concluído."

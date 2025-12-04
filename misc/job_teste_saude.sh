#!/bin/bash
# ===========================================================
# FortePix - Job 3: Teste de Saúde das APIs
# ===========================================================

JAVA_URL="https://SUA_URL_JAVA_AQUI"
PY_URL="https://SUA_URL_PYTHON_AQUI"

echo ">>> Testando API JAVA..."
curl -i "$JAVA_URL/health" || echo "Falha ao acessar /health JAVA"

echo ""
echo ">>> Testando API PYTHON..."
curl -i "$PY_URL/health" || echo "Falha ao acessar /health PYTHON"

echo ""
echo ">>> Verifique códigos HTTP (200 = OK) e corpo das respostas."

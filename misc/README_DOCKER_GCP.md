FortePix API Java - Docker + Deploy GCP
======================================

Arquivos incluídos:

1. Dockerfile
   - Multi-stage build (Maven + JRE)
   - Gera imagem com o jar `fortepix-api-0.1.0-SNAPSHOT.jar`
   - Exposição da porta 8080

2. deploy_cloudrun.sh
   - Script de deploy para o Google Cloud Run usando Artifact Registry.
   - Você deve editar:
     - PROJETO_ID
     - REGIAO
     - REPO_NAME
     - SERVICE_NAME (se desejar outro nome)

Passos para uso:

1. Certifique-se de estar na pasta raiz do projeto `fortepix-api-java/`,
   onde se encontra o `pom.xml` e o `Dockerfile`.

2. Autentique no GCP:
   gcloud auth login
   gcloud auth application-default login

3. Edite o arquivo `deploy_cloudrun.sh` com os dados do seu projeto.

4. Dê permissão de execução:
   chmod +x deploy_cloudrun.sh

5. Execute:
   ./deploy_cloudrun.sh

O script irá:
- Ativar APIs necessárias
- Criar o repositório Artifact Registry (se não existir)
- Fazer build e push da imagem
- Fazer deploy no Cloud Run
- Exibir a URL final da API.

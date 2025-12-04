FortePix – Stack Docker (Java + Python) com docker-compose
=========================================================

Este pacote contém:
- docker-compose.yml
- Dockerfile da API Java (Spring Boot)
- Dockerfile da API Python (FastAPI)

Objetivo:
Subir, em ambiente local de desenvolvimento, os dois serviços da API FortePix em modo de simulação,
mantendo a arquitetura isolada e sem acesso a dados reais ou ambientes externos.

Passos rápidos:
1. Descompacte este ZIP.
2. Certifique-se de ter Docker e docker-compose instalados.
3. Na pasta raiz (onde está o docker-compose.yml), rode:

   docker-compose up --build

4. Serviços esperados:
   - Java API: http://localhost:8080
   - Python API: http://localhost:8090

IMPORTANTE:
- Nenhuma configuração de produção está incluída.
- Ajuste as imagens, variáveis de ambiente e integrações conforme seu ambiente real.

FortePix – Pacote Unificado: Build Completo da API + Stack Docker
=================================================================

Este pacote reúne, em um único arquivo .zip, dois componentes principais:

1) Build Completo da API FortePix (Simulação)
---------------------------------------------
Local: /build_completo

Contém:
- Backend Java (Spring Boot): núcleo principal da API FortePix (Pix, Antifraude, 2FAT, 2FAT + Biometria, Usuários, Auditoria).
- Backend Python (FastAPI): API auxiliar para auditoria e testes.
- Configurações em H2 (Java) e SQLite (Python) para ambiente de simulação.
- Nenhum dado real incluído, nenhuma conexão externa configurada.

2) Stack Docker + docker-compose
--------------------------------
Local: /docker_stack

Contém:
- docker-compose.yml para orquestrar os serviços Java e Python.
- Dockerfile da API Java.
- Dockerfile da API Python.
- Estrutura mínima para subir os serviços em ambiente local usando Docker.

Uso recomendado
---------------
1. Descompactar este pacote .zip.
2. Abrir a pasta /build_completo/java-api e /build_completo/python-api para revisar o código.
3. Integrar (se desejar) o conteúdo de build_completo nas pastas do docker_stack (java-api/ e python-api/).
4. Na pasta /docker_stack, executar:

   docker-compose up --build

5. Ajustar, quando necessário, para apontar para bancos reais, variáveis de ambiente seguras e endpoints de produção.

IMPORTANTE:
- Este pacote é um modelo de arquitetura segura e isolada.
- Não acessa banco de dados real.
- Não realiza nenhuma operação externa.

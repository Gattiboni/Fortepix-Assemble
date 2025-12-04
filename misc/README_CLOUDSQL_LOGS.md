FortePix API - Cloud SQL (GCP) + Healthcheck + Logs Antifraude
==============================================================

Arquivos incluídos:

1. src/main/resources/application-prod.yml
   - Exemplo de configuração para uso com Cloud SQL Postgres no GCP.
   - Ajuste:
     - <DB_NAME>
     - <PROJETO_ID>
     - <REGIAO>
     - <INSTANCIA>
   - Use variáveis de ambiente DB_USER e DB_PASS no Cloud Run.

2. src/main/resources/logback-spring.xml
   - Configuração de logs:
     - Console
     - Arquivo em logs/fortepix-api.log
   - Loggers dedicados para:
     - com.fortepix.seguranca
     - com.fortepix.pix

3. src/main/java/com/fortepix/health/HealthController.java
   - Endpoint simples de health:
     - GET /health
   - Útil para testes e monitoramento externos.

4. src/main/java/com/fortepix/dominio/LogAntifraude.java
   - Entidade JPA para registrar eventos antifraude.

5. src/main/java/com/fortepix/repositorio/LogAntifraudeRepository.java
   - Repositório JPA para consultar/salvar logs antifraude.

6. src/main/java/com/fortepix/seguranca/LogAntifraudeService.java
   - Serviço para registrar logs de risco:
     - documento, chave, valor, resultado, score, origem, data/hora.

Integração sugerida:

- No PixService (ou onde a análise de risco é feita):
  - Após o cálculo de score + resultado do AntifraudeService,
    chame LogAntifraudeService.registrar(...) para gravar o evento.

Ambiente de produção:

- Defina perfil 'prod' no Spring Boot:
  - spring.profiles.active=prod
- No Cloud Run:
  - Configure as variáveis de ambiente DB_USER, DB_PASS.
  - Use Secret Manager para armazenar senhas de forma segura.

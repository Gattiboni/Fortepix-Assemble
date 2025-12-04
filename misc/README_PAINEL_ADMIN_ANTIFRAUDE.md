# FortePix - Painel Administrativo Antifraude (Logs)

Este pacote adiciona um painel administrativo básico via API REST
para visualizar, filtrar, gerar estatísticas e exportar os logs
antifraude registrados na tabela `logs_antifraude`.

## Arquivos incluídos

1. Controller (REST)
   - `src/main/java/com/fortepix/admin/AntifraudeAdminController.java`
   - Endpoints:
     - GET `/admin/antifraude/logs`
       - Filtros: `documento`, `chaveDestino`, `resultadoRisco`, `dataInicio`, `dataFim`
     - GET `/admin/antifraude/stats/por-resultado`
       - Estatísticas de quantidade por tipo de risco
     - GET `/admin/antifraude/stats/por-dia`
       - Estatísticas de quantidade por dia
     - GET `/admin/antifraude/logs/export`
       - Exporta os logs filtrados em CSV para download

2. Service
   - `src/main/java/com/fortepix/seguranca/AntifraudeAdminService.java`
   - Usa `LogAntifraudeRepository` para buscar todos os registros
     e aplica filtros em memória (simples, ideal para volumes médios).
   - Responsável por:
     - Filtragem por campos
     - Agrupamento por resultado
     - Agrupamento por dia
     - Geração de CSV

## Integração

1. Copie os arquivos para o seu projeto FortePix API Java, respeitando os pacotes.

2. Garanta que:
   - `LogAntifraude` (entidade) e `LogAntifraudeRepository` já estejam presentes.
   - Spring Security permita acesso aos endpoints `/admin/**` apenas para `ROLE_ADMIN`.

3. Exemplo de uso:

   - Listar logs:
     - GET `/admin/antifraude/logs?documento=12345678900&dataInicio=2025-01-01&dataFim=2025-01-31`

   - Estatísticas por resultado:
     - GET `/admin/antifraude/stats/por-resultado?dataInicio=2025-01-01&dataFim=2025-01-31`

   - Exportar CSV:
     - GET `/admin/antifraude/logs/export?resultadoRisco=BLOQUEIO_PREVENTIVO`

Este painel serve como base para integração futura com Flutter Web / Mobile
para o painel administrativo do FortePix.

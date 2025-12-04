# FortePix - Módulo Completo de Log Antifraude

Este pacote inclui os três componentes necessários para registrar logs antifraude na API FortePix:

## 1. Entidade JPA
- `LogAntifraude.java`
- Representa o registro persistido no banco.
- Campos:
  - documentoUsuario
  - chaveDestino
  - valor
  - resultadoRisco
  - scoreCalculado
  - criadoEm
  - origem

## 2. Repositório JPA
- `LogAntifraudeRepository.java`
- Interface padrão para salvar/consultar logs.

## 3. Serviço
- `LogAntifraudeService.java`
- Método:
  registrar(documento, chave, valor, resultado, score, origem)

## 4. Script SQL
- `logs_antifraude.sql`
- Estrutura completa da tabela + índices otimizados.

## Como integrar:

1. Copie os arquivos para:
   - dominio → entidade
   - repositorio → repositório
   - seguranca → serviço

2. Importe no PixService:
   ```
   private final LogAntifraudeService logAntifraudeService;
   ```

3. Registre logs após análise de risco:
   ```
   logAntifraudeService.registrar(...);
   ```

Pronto! Logs antifraude 100% operacionais.

# FortePix API - Projeto de Teste (Spring Boot)

Este projeto é uma versão funcional de teste da API FortePix, com foco em:
- Envio de Pix com fluxo antifraude simplificado
- Estrutura para 2FAT / biometria (stub)
- Logs antifraude centralizados
- Seed inicial em banco H2 para testes rápidos
- Endpoints REST para Usuários, Contas e Pix

## Tecnologias

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Spring Security (liberado para testes)
- H2 Database (em memória)
- Lombok

---

## Como executar

1. Certifique-se de ter **Java 17+** e **Maven** instalados.
2. Extraia o ZIP deste projeto.
3. No diretório raiz do projeto, execute:

```bash
mvn spring-boot:run
```

4. A aplicação subirá em:

```text
http://localhost:8080
```

O console do H2 fica disponível (se habilitado) em:

```text
http://localhost:8080/h2-console
```

---

## Seed inicial (H2)

O arquivo `src/main/resources/data.sql` cria automaticamente:

### Usuários

- **ID 1** – Usuário Teste Comum  
  - Email: `usuario.comum@fortepix.test`  
  - Perfil: `CLIENTE_COMUM`

- **ID 2** – Usuário Segurança Alta  
  - Email: `seg.alta@fortepix.test`  
  - Perfil: `CLIENTE_SEGURANCA_ALTA`

### Contas

- **Conta 1**  
  - Usuário: `1`  
  - Chave Pix: `chave-comum@fortepix.test`  
  - Saldo: `1500.00`

- **Conta 2**  
  - Usuário: `2`  
  - Chave Pix: `chave-seg-alta@fortepix.test`  
  - Saldo: `5000.00`

---

## Endpoints principais

### 👤 Usuários

**Criar usuário**  
`POST /api/usuarios`  
Body (JSON):
```json
{
  "nome": "Novo Cliente",
  "email": "novo@cliente.com",
  "perfil": "CLIENTE_COMUM"
}
```

**Listar usuários**  
`GET /api/usuarios`

**Buscar usuário por ID**  
`GET /api/usuarios/{id}`

---

### 💳 Contas

**Criar conta para usuário existente**  
`POST /api/contas/criar/{usuarioId}`  
Body (JSON):
```json
{
  "chavePix": "minha.chave@teste.com",
  "saldo": 1000.00
}
```

**Listar contas**  
`GET /api/contas`

**Buscar conta por ID**  
`GET /api/contas/{id}`

---

### ⚡ Pix

**Enviar Pix (teste)**  
`POST /api/pix/enviar/{usuarioId}`  

Exemplo usando o usuário de ID `1` (conta de origem) e a conta de destino com chave `chave-seg-alta@fortepix.test`:

Request:
```http
POST http://localhost:8080/api/pix/enviar/1
Content-Type: application/json
```

Body (JSON):
```json
{
  "chaveDestino": "chave-seg-alta@fortepix.test",
  "valor": 100.00,
  "descricao": "Teste Pix FortePix"
}
```

Resposta de sucesso (exemplo):
```json
{
  "idExterno": "uuid-gerado",
  "status": "CONCLUIDO",
  "valor": 100.0,
  "chaveDestino": "chave-seg-alta@fortepix.test",
  "descricao": "Teste Pix FortePix",
  "dataConclusao": "2025-11-30T12:00:00Z"
}
```

---

## Coleção Postman / Insomnia

Na raiz do projeto há um arquivo JSON:

- `fortepix-api-collection.json`

Ele contém todos os endpoints já configurados apontando para `http://localhost:8080`.  
Basta importar no Postman ou no Insomnia para começar a testar.

---

## Observações

- O antifraude (`AntifraudeService`) está em modo **stub**: sempre retorna `APROVADO`.  
  - Está pronto para ser substituído por regras reais, IA, integrações externas, blacklist etc.
- O `TwoFactorService` também é um **stub** que representa o ponto de integração com 2FAT + biometria.
- A segurança HTTP está liberada (`permitAll`) para facilitar os testes iniciais.

Este projeto é uma base para demonstração, testes e evolução da arquitetura FortePix.

---

## Antifraude - Regras de Referência

A classe `AntifraudeService` implementa uma lógica de referência baseada em:

- **Valor da transação**
  - >= 20.000,00 → `ALTO_RISCO`
  - Entre 5.000,00 e 20.000,00 → depende do horário/perfil
- **Horário**
  - Transações de valor médio/alto entre **00:00 e 06:00** podem resultar em `BLOQUEIO_PREVENTIVO`
- **Perfil do usuário**
  - `CLIENTE_SEGURANCA_ALTA` com valor acima de 5.000,00 → tendência a `BLOQUEIO_PREVENTIVO`
- Valores até 500,00 → aprovados por padrão (`APROVADO`)

Esta lógica é totalmente substituível por:
- Motor de regras (Drools etc.)
- IA / modelos de machine learning
- Blacklist interna de contas/chaves/CPFs/CNPJs
- Integração com bureaus externos de risco.

---

## 2FAT + Biometria - Pontos de Integração

A classe `TwoFactorService` define:

- Quando o **2FAT** é exigido:
  - Valor >= 500,00
  - Ou perfil `CLIENTE_SEGURANCA_ALTA`

- Quando a **biometria** é exigida:
  - Valor >= 2.000,00
  - Ou perfil `CLIENTE_SEGURANCA_ALTA`

### Pontos para implementação real

- `enviarCodigo2FAT(...)`  
  - Integrar com e-mail/SMS/push (Twilio, SES, Firebase etc.)
- `validarCodigo2FAT(...)`  
  - Validar token armazenado em banco/cache (Redis etc.), com expiração.
- `validarBiometria(...)`  
  - Integrar com SDK de biometria do dispositivo / provedor terceiro.

O `PixService` já chama `TwoFactorService.validar2FatorSeNecessario(...)`
após o antifraude, o que garante a ordem:

1. Antifraude
2. 2FAT / Biometria
3. Liquidação (débito/crédito)

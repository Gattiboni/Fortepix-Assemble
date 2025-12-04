# FortePix API - Primeira parte para APP final (Contrato de Erros + Validação)

Este pacote contém **apenas os arquivos necessários** para concluir a primeira parte
da API final FortePix:

1. Padrão de erro unificado para toda a API.
2. Validação de request do Pix (Bean Validation).
3. Exceções específicas para 2FAT.

## Arquivos incluídos

### 1) Padrão de erro

- `com/fortepix/api/error/ErrorCode.java`  
- `com/fortepix/api/error/ErrorResponse.java`  
- `com/fortepix/api/error/GlobalExceptionHandler.java`  

Esses arquivos definem o formato de erro:

```json
{
  "timestamp": "2025-11-30T12:00:00-03:00",
  "code": "FORTEPIX_SALDO_INSUFICIENTE",
  "message": "Saldo insuficiente para realizar o Pix.",
  "details": null,
  "path": "/api/pix/enviar/1"
}
```

### 2) Exceções específicas 2FAT

- `com/fortepix/pix/exception/TwoFactorCodigoInvalidoException.java`
- `com/fortepix/pix/exception/TwoFactorCodigoExpiradoException.java`

Devem ser usadas no `TwoFactorService` em vez de `IllegalArgumentException`.

### 3) DTO de Pix com validação

- `com/fortepix/pix/dto/PixRequestDTO.java`

Já com anotações:

- `@NotBlank` na chave Pix de destino
- `@NotNull` e `@DecimalMin("0.01")` no valor

## Passos para integrar no seu projeto FortePix

1. **Copiar os arquivos** deste pacote para o mesmo caminho de `src/main/java` do seu projeto.
2. No `pom.xml`, garantir que exista a dependência:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

3. No `PixController`, importar `jakarta.validation.Valid` e usar:

```java
public ResponseEntity<PixResponseDTO> criarIntencaoPix(
        @PathVariable Long usuarioId,
        @Valid @RequestBody PixRequestDTO request) {
    ...
}
```

4. No `TwoFactorService`, ajustar para lançar:

```java
if (token.getExpiracao().isBefore(OffsetDateTime.now())) {
    throw new TwoFactorCodigoExpiradoException("Código 2FAT expirado.");
}

if (!token.getCodigo().equals(codigoInformado)) {
    throw new TwoFactorCodigoInvalidoException("Código 2FAT inválido.");
}
```

Com isso, o APP (Flutter, painel, etc.) passa a receber erros sempre no mesmo formato
e com `code` FortePix, pronto para mapeamento de mensagens/telas.

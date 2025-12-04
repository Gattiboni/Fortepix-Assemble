# FortePix API - Segunda parte para APP final
## (Mapeamento de códigos p/ Flutter + JWT + Perfis)

Este pacote contém:

1. **Documento de mapeamento** dos códigos FortePix (`ErrorCode`) para textos/telas do app Flutter.  
2. **Camada de segurança** baseada em JWT + perfis, pronta para ser integrada na API FortePix.

---

## 1. Mapeamento de códigos -> textos/telas (Flutter)

Arquivo:

- `flutter-error-mapping.md`

Use esse arquivo como guia para:

- Decidir **qual tela** abrir para cada `ErrorCode`
- Padronizar os **textos** de erro e alertas
- Aplicar os erros de validação de campos (`FORTEPIX_VALIDACAO`) diretamente nos inputs

O app deve sempre ler o campo `code` do JSON de erro.

---

## 2. Segurança JWT + Perfis

Arquivos Java principais (em `src/main/java`):

- `com/fortepix/api/security/JwtService.java`  
- `com/fortepix/api/security/CustomUserDetailsService.java`  
- `com/fortepix/api/security/JwtAuthenticationFilter.java`  
- `com/fortepix/api/security/SecurityConfig.java`  
- `com/fortepix/pix/dto/AuthRequestDTO.java`  
- `com/fortepix/pix/dto/AuthResponseDTO.java`  
- `com/fortepix/pix/controller/AuthController.java`  

### 2.1 Dependências necessárias no pom.xml

Você deve incluir as dependências de **JWT** e **Spring Security**, por exemplo:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

### 2.2 Fluxo de autenticação

1. Usuário informa **e-mail + senha** no app.
2. App chama `POST /api/auth/login` com JSON:

```json
{
  "email": "cliente@fortepix.test",
  "senha": "123456"
}
```

3. Se sucesso, API responde:

```json
{
  "token": "<JWT>",
  "perfil": "CLIENTE",
  "usuarioId": 1
}
```

4. O app passa a enviar o header:

```http
Authorization: Bearer <JWT>
```

em todas as requisições protegidas (/api/pix/**, /api/contas/**, etc.).

### 2.3 Perfis / Roles

O campo `perfil` do `Usuario` é usado para gerar a role:

- `perfil = "CLIENTE"`  → role `ROLE_CLIENTE`
- `perfil = "ADMIN"`    → role `ROLE_ADMIN`
- `perfil = "CLIENTE_EMPRESA"` → role `ROLE_CLIENTE_EMPRESA`
- etc.

Você pode usar anotações como:

```java
@PreAuthorize("hasRole('ADMIN')")
```

em controllers de administração, por exemplo o painel de revisão de Pix suspeitos.

### 2.4 Configuração de segurança

`SecurityConfig` define:

- Rotas públicas:
  - `/api/auth/**`
  - `/actuator/**`
  - `/v3/api-docs/**`
  - `/swagger-ui/**`
- Todas as demais rotas exigem JWT válido.
- Session stateless (sem sessão de servidor).

---

## Como integrar no seu projeto FortePix

1. Copie a pasta `src/main/java` deste pacote para dentro do `src/main/java` do seu projeto principal, mantendo os mesmos pacotes.
2. Ajuste o `pom.xml` com as dependências de **Spring Security** e **JWT**.
3. Garanta que seu modelo `Usuario` tenha:
   - `email`
   - `senha` (hash/Bcrypt)
   - `perfil`
4. Ajuste o `UsuarioRepository` para ter um método `findByEmail` (se ainda não tiver).
5. Opcional: use o endpoint `/api/auth/criar-admin-inicial` **apenas uma vez** para criar o primeiro ADMIN.

Depois disso, sua API FortePix passa a:

- Exigir login e token JWT nas rotas sensíveis.
- Responder com códigos de erro padronizados para o app reagir.

# Camada FortePix - Códigos para Flutter + JWT + Perfis

Este pacote NÃO é o projeto completo, e sim uma **camada adicional**
para você integrar na API FortePix que já está com você.

## Conteúdo

- `src/main/java/com/fortepix/api/security/*`
  - JwtService
  - JwtAuthenticationFilter
  - CustomUserDetailsService
  - SecurityConfig

- `src/main/java/com/fortepix/pix/dto/*`
  - AuthRequestDTO
  - AuthResponseDTO

- `src/main/java/com/fortepix/pix/controller/AuthController.java`

- `docs/flutter-error-mapping.md`
  - Guia de como o app Flutter deve mapear os códigos de erro (`ErrorCode`)
    para telas e textos.

## Como integrar na sua API FortePix

1. **Copie** o conteúdo desta pasta `src/main/java` para dentro do `src/main/java`
   do seu projeto FortePix existente (mantendo os mesmos pacotes).

2. No seu `pom.xml`, garanta as dependências:

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

3. No seu `UsuarioRepository`, adicione o método:

```java
java.util.Optional<Usuario> findByEmail(String email);
```

4. Garanta que o modelo `Usuario` tenha os campos:
   - `email`
   - `senha` (armazenada com BCrypt)
   - `perfil` (ex: CLIENTE, ADMIN, CLIENTE_EMPRESA etc.)

5. Após subir a API, você poderá:
   - Criar o primeiro ADMIN com `POST /api/auth/criar-admin-inicial`
   - Fazer login com `POST /api/auth/login`
   - Usar o token JWT retornado em `Authorization: Bearer <token>`
     nas chamadas subsequentes.

## Flutter

Use `docs/flutter-error-mapping.md` como base para:
- Montar telas de erro/segurança
- Decidir mensagens por `ErrorCode`
- Tratar `FORTEPIX_VALIDACAO` aplicando erros em campos.


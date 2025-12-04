package com.fortepix.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${fortepix.jwt.secret:Zm9ydGVwaXgtZGVtbzEtc2VjcmV0LWNoYW5nZW1l}")
    private String secret;

    @Value("${fortepix.jwt.expiration-minutes:60}")
    private long expirationMinutes;

    public String gerarToken(String subject, String perfil, Long usuarioId) {
        Instant agora = Instant.now();
        Instant expiracao = agora.plus(expirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(agora))
                .setExpiration(Date.from(expiracao))
                .addClaims(Map.of(
                        "perfil", perfil,
                        "usuarioId", usuarioId
                ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairSubject(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public String extrairPerfil(String token) {
        Claims claims = extrairTodosClaims(token);
        Object perfil = claims.get("perfil");
        return perfil != null ? perfil.toString() : null;
    }

    public Long extrairUsuarioId(String token) {
        Claims claims = extrairTodosClaims(token);
        Object usuarioId = claims.get("usuarioId");
        return usuarioId != null ? Long.valueOf(usuarioId.toString()) : null;
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = extrairTodosClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodosClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

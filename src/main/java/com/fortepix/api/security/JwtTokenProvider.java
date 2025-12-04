package com.fortepix.seguranca;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expirationSeconds;

    public JwtTokenProvider(@Value("${fortepix.jwt.secret}") String secret,
                            @Value("${fortepix.jwt.expiration-seconds}") long expirationSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = expirationSeconds;
    }

    public String gerarToken(String subject, String role) {
        Date agora = new Date();
        Date expira = new Date(agora.getTime() + expirationSeconds * 1000);
        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role)
                .setIssuedAt(agora)
                .setExpiration(expira)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validar(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}

package com.kakaopay.cscenter.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class TokenManager {

    private final Key key;
    private static final String PAYLOAD = "payload";
    private static final long DEFAULT_EXPIRATION = 1800;

    public TokenManager(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public <T extends Payload> TokenDto generateToken(T payload) {
        return generateToken(payload, DEFAULT_EXPIRATION);
    }

    public <T extends Payload> TokenDto generateToken(T payload, long expiration) {
        Date exp = new Date(System.currentTimeMillis() + (expiration * 1000));
        String accessToken = Jwts.builder()
                .setClaims(Jwts.claims(Map.of(PAYLOAD, payload)))
                .setExpiration(exp)
                .signWith(this.key)
                .compact();
        return TokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiration(exp.getTime())
                .build();
    }

    public <T extends Payload> T extractPayload(String token, Class<T> type) {
        try {
            return Jwts.parserBuilder()
                    .deserializeJsonWith(new JacksonDeserializer<>(Map.of(PAYLOAD, type)))
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(PAYLOAD, type);
        } catch (Exception e) {
            log.warn("[extractPayload] error message : {}", e.getMessage());
        }
        return null;
    }
}

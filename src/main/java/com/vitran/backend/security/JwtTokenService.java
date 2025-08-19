package com.vitran.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.*;

public class JwtTokenService {

    private final Key key;
    private final String issuer;
    private final long accessTtlSeconds;

    public JwtTokenService(String secret, String issuer, long accessTtlSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.issuer = issuer;
        this.accessTtlSeconds = accessTtlSeconds;
    }

    public String issueAccessToken(String userLoginId,
                                   Long partyId,
                                   String firstName,
                                   Set<String> permissions) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTtlSeconds);

        JwtBuilder builder = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userLoginId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .claim("pid", partyId)
                .claim("given_name", firstName)
                .claim("perms", permissions == null ? List.of() : permissions)
                .signWith(key, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    public Jws<Claims> parseAndValidate(String token) {
        return Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}


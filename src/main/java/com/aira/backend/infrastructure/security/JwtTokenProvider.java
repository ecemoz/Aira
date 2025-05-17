package com.aira.backend.infrastructure.security;

import com.aira.backend.domain.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Generates a signed JWT token containing user's email as subject,
     * and role/id as additional claims.
     */
    public String generateToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();

        System.out.println("[JWT] Token generated for user: " + user.getEmail());
        System.out.println("[JWT] Token: " + token);

        return token;
    }

    /**
     * Extracts the email (subject) from the JWT token
     */
    public String getEmailFromToken(String token) {
        String email = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        System.out.println("[JWT] Extracted email from token: " + email);
        return email;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token);
            System.out.println("[JWT] Token is valid ✅");
            return true;
        } catch (JwtException e) {
            System.out.println("[JWT] Token validation failed ❌: " + e.getMessage());
            return false;
        }
    }
}
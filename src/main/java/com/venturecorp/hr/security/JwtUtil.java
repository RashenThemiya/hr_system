package com.venturecorp.hr.security;

import com.venturecorp.hr.model.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ MUST be 32+ chars for HS256
    private static final String SECRET_KEY =
            "this_is_a_very_secure_secret_key_123456";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24; // 24 hours

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    // ✅ Generate JWT with roles (STRING SAFE)
    public String generateToken(Admin admin) {

        // ✅ roles already stored as String: "ADMIN,SUPERADMIN"
        String roles = admin.getRoles();

        return Jwts.builder()
                .setSubject(admin.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Used by JwtFilter
    public String extractRoles(String token) {
        return getClaims(token).get("roles", String.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

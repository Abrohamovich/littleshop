package org.abrohamovich.littleshop.adapter.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.abrohamovich.littleshop.application.port.out.auth.TokenServicePort;
import org.abrohamovich.littleshop.domain.model.AuthenticationToken;
import org.abrohamovich.littleshop.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtTokenServiceAdapter implements TokenServicePort {
    private final SecretKey key;
    private final long jwtExpirationMs;

    public JwtTokenServiceAdapter(@Value("${jwt.secret}") String jwtSecret,
                                  @Value("${jwt.expiration-ms:2592000000}") long jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    @Override
    public AuthenticationToken generateToken(User user) {
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("email", user.getEmail())
                .claim("role", user.getRole().toString())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        LocalDateTime expiresAt = expiryDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return new AuthenticationToken(token, "Bearer", expiresAt, user.getId());
    }

    @Override
    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

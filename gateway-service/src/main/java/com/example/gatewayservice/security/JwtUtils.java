package com.example.gatewayservice.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret-key}")
    private String JWT_SECRET;

    public void validateJwtToken(String token) throws RuntimeException {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT claims string is empty");
        }
    }
}

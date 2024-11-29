/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.security.jwt;


import com.windev.identity_service.security.user_details.CustomUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String JWT_SECRET_KEY;

    @Value("${app.jwt.expiration-date}")
    private int JWT_EXPIRATION_DATE;

    public String generateToken(Authentication auth) {
        CustomUserDetails userPrinciple = (CustomUserDetails) auth.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_DATE);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrinciple.getId());
        claims.put("email", userPrinciple.getEmail());
        claims.put("authorities", userPrinciple.getAuthorities());

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .addClaims(claims)
                .setExpiration(expiryDate)
                .setIssuedAt(now)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return
                Jwts.parserBuilder()
                        .setSigningKey(getSignKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("validationToken() --> error validate token: {}", e.getMessage());
            return false;
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

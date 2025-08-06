package com.team_3.School_Medical_Management_System.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;
  
    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userPrincipal.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

                public boolean validateJwtToken(String token) {
                    try {

                        Jwts.parserBuilder()
                                .setSigningKey(key())
                                .build()
                                .parseClaimsJws(token);
                    
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }

    public String getUsernameFromJwtToken(String token) {
        return 
        Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        
    }

    public List<String> getRolesFromJwtToken(String token) {
        Claims claims =  Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Lấy danh sách vai trò từ claims
        return claims.get("roles", List.class);
    }
    

    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    
}

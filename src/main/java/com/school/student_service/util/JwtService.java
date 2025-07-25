package com.school.student_service.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    
    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject(); // assuming email is in subject
    }
    // ✅ Parse token and extract all claims
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
    // ✅ Convert valid JWT into Spring Security authentication
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = extractAllClaims(token);
        String username = claims.get("email", String.class);
        String role = claims.get("role", String.class);

        return new UsernamePasswordAuthenticationToken(
            username,
            null,
            List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }

    // ✅ Create filter to apply in SecurityConfig
    public Filter authenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {

                final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    try {
                        var auth = getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }
}

package com.example.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // JWT 서명에 사용할 비밀 키 생성
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 3; // 토큰 유효 시간: 3H

    // JWT 토큰 생성 메서드(로그인 시 사용)
    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // 만료 시간
                .signWith(key) // 서명에 사용할 키 설정
                .compact();
    }

    // 사용자 ID 추출 메서드
    public String getUserIdFromToken(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    // 역할 추출 메서드
    public String getRoleFromToken(String token) {
        return parseClaims(token).getBody().get("role", String.class);
    }

    // JWT 유효성 검사 메서드
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰 파싱하여 Claims 반환하는 내부 메서드
    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}

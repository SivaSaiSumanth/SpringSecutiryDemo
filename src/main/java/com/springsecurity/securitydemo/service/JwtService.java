package com.springsecurity.securitydemo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY =
            "my-secret-key-for-jwt-spring-security-demo-2026";

    public String generateToken(String username) {

        SecretKey key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {

        SecretKey key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}


/*
generateToken()
       ↓
username → JWT

extractUsername()
       ↓
JWT → username



POST /login
    ↓
username + password
    ↓
MySQL authentication
    ↓
generateToken(username)
    ↓
JWT returned

then

GET /payments
Authorization: Bearer <JWT>
    ↓
JWT Filter
    ↓
extractUsername()
    ↓
load user from DB
    ↓
authorities
    ↓
Spring Security authorization
 */
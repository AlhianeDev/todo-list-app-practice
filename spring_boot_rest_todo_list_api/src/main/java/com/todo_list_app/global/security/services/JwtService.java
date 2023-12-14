package com.todo_list_app.global.security.services;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;

import java.util.HashMap;

import java.util.Map;

import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY =

            "94581dc707134ac451c624299909e839f49c06eab566e873f3e3f0b4f4544e41";

    public String extractUserEmail(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUserEmail(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(userDetails.getUsername())

                .setIssuedAt(new Date(System.currentTimeMillis()))

                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))

                .signWith(getSigninKey(), SignatureAlgorithm.HS256)

                .compact();

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getClaims(token);

        return claimsResolver.apply(claims);

    }

    private Claims getClaims(String token) {

        return Jwts.parserBuilder()

                // Using SigningKey, Token Signature Generated.

                .setSigningKey(getSigninKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

    }

    private Key getSigninKey() {

        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);

    }

}

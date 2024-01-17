package com.example.demo.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String jwtSecret;

//    public String generateAccessToken(String id) {
////        Claims claims = Jwts.claims();
////        claims.put("username", username);
//
//        String token = Jwts.builder()
//                .setSubject(id)
////                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
//                .signWith(getSignKey(jwtSecret), SignatureAlgorithm.HS256)
//                .compact();
//
//        return token;
//    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


//    public String generateRefreshToken(String id) {
//        String token = Jwts.builder()
//                .setSubject(id)
////                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
//                .signWith(getSignKey(jwtSecret), SignatureAlgorithm.HS256)
//                .compact();
//
//        return token;
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(getSignKey(jwtSecret))
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            return true;
//
//        } catch (Exception e) {
//
//        }
//
//
//        return false;
//    }


    public String parseMemberIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey(jwtSecret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("id").toString();

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

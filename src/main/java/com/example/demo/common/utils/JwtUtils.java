package com.example.demo.common.utils;

import com.example.demo.member.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {

    public static String generateLoginAccessToken(Member member, String key, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", member.getEmail());
        claims.put("id", member.getId());
        claims.put("nickname", member.getNickname());
        claims.put("image", member.getImage());


        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis()*60*10 + expiredTimeMs)*1000))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static String generateSignUpAccessToken(String email, String key, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", email);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis()*60*10 + expiredTimeMs)*1000))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static Boolean validate(String token, String username, String key) {
        String usernameByToken = getUsername(token, key);

        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return usernameByToken.equals(username) && !result;
    }

    public static String getUsername(String token, String key) {
        return extractAllClaims(token, key).get("username", String.class);
    }

    public static Long getUserId(String token, String key) {
        return extractAllClaims(token, key).get("id", Long.class);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}

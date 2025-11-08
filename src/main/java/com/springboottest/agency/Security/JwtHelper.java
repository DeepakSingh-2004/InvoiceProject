package com.springboottest.agency.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboottest.agency.Entity.UserEntity;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    // 🔑 Secret key for signing JWT (use 256-bit)
    private final String SECRET_KEY = "ThisIsMySuperSecretKeyForJwtTokenGenerationInInventorySystem123456";

    // 🔐 Token validity (in milliseconds) → 10 hours
    private final long JWT_EXPIRATION = 1000 * 60 * 60 * 10;

  public String generateToken(UserEntity userDetails) {

    Map<String, Object> claims = new HashMap<>();

    // Add your custom fields
    claims.put("userId", userDetails.getId());
    claims.put("username", userDetails.getUsername());
    claims.put("role", userDetails.getRole());

    // Token expiry like your example
    // long expiryTime = rememberMe
    //         ? 1000L * 60 * 60 * 24 * 30   // 30 days
    //         : 1000L * 60 * 60 * 12;       // 12 hours

    return createToken(claims, userDetails.getUsername());
}



private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject) // username
            .setIssuedAt(new Date(System.currentTimeMillis()))
            // .setExpiration(new Date(System.currentTimeMillis() + expiryTime))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
}



    // ✅ Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extract single claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Extract all claims from token
    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

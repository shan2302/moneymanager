package com.shantanu.moneymanager.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
//    Loaded from application.properties: money.manager.jwt.secret
    @Value("${money.manager.jwt.secret}")
    private String secret;
//    Generates a token based on the user's email
    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,email);
    }
    private Key getSigningKey(){
        byte[] keyBytes = this.secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String createToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                Typically set to expire in 10 hours
                .setExpiration(new Date(System.currentTimeMillis()+(1000L*60*60*24)))
//        In the version of JJWT you are using (indicated by your pom.xml paths), if secret is just a plain String, the signWith method might be failing silently or throwing an internal exception because it expects a Key object or a specific byte array, not a raw UUID string.
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    Used in JWTRequestFilter to identify the user
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    Validates the token against the database user details
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}

//3:43:01
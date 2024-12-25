package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("stu.edu.vn")
    private String jwtSecret;

    @Value("7200000")
    private int jwtExpirationMs;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extractClaim, UserDetails userDetails) {
        extractClaim.put("roles", userDetails.getAuthorities().stream()
                .map(item -> new SimpleGrantedAuthority(item.getAuthority())).collect(Collectors.toList()));
        return Jwts
                .builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<Map<String, String>> extractRole(String token)
    {
        return (List<Map<String, String>>)
                Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody().get("roles");
    }

    public String generateResetPasswordToken(String email, int expirationInMinutes) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email); // Thêm email vào claims

        return Jwts.builder()
                .setClaims(claims) // Thêm thông tin vào payload
                .setSubject("Reset Password") // Chủ đề token
                .setIssuedAt(new Date()) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMinutes * 60 * 1000)) // Thời gian hết hạn
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Ký token với secret
                .compact();
    }

    public String extractEmailFromResetToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("email", String.class); // Lấy email từ claims
    }

    public boolean isResetTokenValid(String token, String email) {
        try {
            return extractEmailFromResetToken(token).equals(email) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }




}

package com.vn.investion.utils;

import com.vn.investion.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtService {

    private static final String SECRET_KEY = "635266556A576E5A7234753778214125442A472D4B6150645367566B59703273";

    private JwtService() {
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static String generateToken(
            Map<String, Object> extractClaim,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateToken(UserDetails userDetails) {
        var claims = new HashMap<String, Object>();
        claims.put("role", userDetails.getAuthorities());
        return generateToken(claims, userDetails);
    }

    public static boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {
        final var username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public static String getUserName(Authentication authentication) {
        var data = authentication.getPrincipal();
        if (data instanceof User user) {
            return user.getUsername();
        }
        return null;
    }

    public static boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private static Date extractExpiration(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();
        return claims.getExpiration();
    }

    private static Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSigningKey() {
        final var keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static <C> C extractClaim(
            String token,
            Function<Claims, C> claimsResolver
    ) {
        final var claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

}

package com.kaleem.springsecurity.util;

import com.kaleem.springsecurity.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secretkey}")
    private String secretKey;

    public  String generateToken(User user) {
        return Jwts
                .builder()
                .claims(Map.of())
                .subject(user.getUsername())
                .issuer("KALEEM")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*10*1000))
                .signWith(getSigningKey())
                .compact();

    }

    public String getUserName(String jwtToken) {
        Claims claims = claims(jwtToken);
        return claims.getSubject();
    }

    public Date getExpiration(String jwtToken) {
        Claims claims = claims(jwtToken);
        return claims.getExpiration();
    }

    public boolean verifyJwt(String jwtToken, UserDetails userDetails) {
        var username = getUserName(jwtToken);
        var expired = getExpiration(jwtToken).before(new Date());
        return username.equals(userDetails.getUsername()) && !expired;
    }

    private Claims claims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

    }


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.guesthouse.wishboard.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtUtil {
        private SecretKey secretKey;

        public JwtUtil(@Value("${spring.jwt.secret}")String secret) {


            secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        }

        public String getUsername(String token) {

            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
        }

        public Boolean isExpired(String token) {

            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        }

        public String createJwt(String username, Long expiredMs) {

            return Jwts.builder()
                    .claim("username", username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        }
    }


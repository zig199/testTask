package dev.ziggeek.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Date;

@UtilityClass
public class JwtUtil {

    private static final Duration expiration = Duration.ofMinutes(30);
    private static final String secret = "0000";


    public static String generateJwtToken(Long id) {
        Date issued = new Date();
        Date expired = new Date(issued.getTime() + expiration.toMillis());

        return Jwts
                .builder()
                .claim("user_id", id)
                .setIssuedAt(issued)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private static final String BEARER = "Bearer ";

    public static Long getUserId(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            var jwt = authorizationHeader.substring(BEARER.length());
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("JWT token is expired");
            }

            return claims.get("user_id", Long.class);
        }

        return null;
    }
}

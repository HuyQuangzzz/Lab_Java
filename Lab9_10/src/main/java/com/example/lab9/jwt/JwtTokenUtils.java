package com.example.lab9.jwt;


import com.example.lab9.model.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtils {
    
    // Chuỗi bí mật
    @Value("${app.secret}")
    private String JWT_SECRET;

    // Thời hạn của jwt
    private final Integer JWT_EXPIRATION = 3600 * 24;

    // Phương thức tạo token jwt
    public String generateTokenJwt(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                    .setSubject(userDetails.getUser().getEmail())
                    .setIssuedAt(now)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                    .compact();
    }

    // Phương thức lấy thông tin user từ JWT
    public String getUserInfoFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();
         return claims.getSubject();
    }

    // Phương thức validate Token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}

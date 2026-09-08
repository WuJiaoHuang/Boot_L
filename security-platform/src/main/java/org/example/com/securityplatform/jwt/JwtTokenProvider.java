package org.example.com.securityplatform.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET =
            "nexus-commerce-learn-jwt-secret-key-123456";

    //token有效期是24小时
    private static final long EXPIRATION = 1000 * 60 * 60 * 24;

    //把刚才那个字符串 SECRET 转成真正用于 JWT 签名的密钥对象
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(Long userId, String username) {

        Date now = new Date();
        Date expire = new Date(
                now.getTime() + EXPIRATION
        );

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(now)
                .expiration(expire)
                .signWith(getSigningKey())
                .compact();
    }
}
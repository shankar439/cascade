package com.HMPackage.util;

import com.HMPackage.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


public class JwtUtils {
    public static String generateToken(String signingKey, String subject, Long id, String name,List<Role>authorities) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .claim("userId", id)
                .claim("Authorities",authorities)
                .claim("userName", name)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, "secret");
        return builder.compact();
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String authToken, String signingKey) {
        try {
            Jwts.parser().setSigningKey("secret").parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserNameFromJWTs(String token, String signingKey) {
        Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
        return String.valueOf(claims.get("userName"));
    }
}

package com.quizguru.auth.jwt;

import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;

import com.quizguru.auth.properties.JwtProperties;
import com.quizguru.auth.model.Role;
import com.quizguru.auth.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties config;

    public String generateTokenFromUserId(String userId, List<Role> roles) {
        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes());
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", roles)
                .setExpiration(new Date(now.getTime() + config.getExpirationMs()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(Authentication auth) {

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes());

        Date now = new Date();

        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .claim("role", userPrincipal.getAuthorities())
                .setExpiration(new Date(now.getTime() + config.getExpirationMs()))
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return "";
    }

    public String getUserIdFromJwt(String jwt) {

        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return String.valueOf(claims.getSubject());
    }


    public boolean validateToken(String authToken) {
        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes());
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}



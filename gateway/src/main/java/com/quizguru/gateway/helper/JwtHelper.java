package com.quizguru.gateway.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class JwtHelper {
    public static Optional<String> resolveToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}

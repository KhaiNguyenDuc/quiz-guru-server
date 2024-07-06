package com.quizguru.gateway.client;

import com.quizguru.gateway.dto.ApiResponse;
import com.quizguru.gateway.dto.TokenValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthClient {
    private final WebClient.Builder webClientBuilder;
    private static final String HOST = "lb://AUTH-SERVER/auth/api/v1"; // Update scheme and host


    public Mono<ApiResponse<TokenValidationResponse>> validateToken(String token) {
        return webClientBuilder
                .baseUrl(HOST)
                .build()
                .post()
                .uri("/validate-token")
                .bodyValue(token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<TokenValidationResponse>>() {
                });
    }
}

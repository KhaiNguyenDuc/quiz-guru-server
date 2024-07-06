package com.quizguru.gateway.filter;

import com.quizguru.gateway.client.AuthClient;
import com.quizguru.gateway.dto.TokenValidationResponse;
import com.quizguru.gateway.helper.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {

    private AuthClient authClient;
    @Autowired
    public void setAuthClient(@Lazy AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            Optional<String> token = JwtHelper.resolveToken(request);

            if (token.isEmpty()) {
                log.warn("Token not found in request");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            log.warn("Token found in request");

            return authClient.validateToken(token.get())
                    .flatMap(response -> {
                        TokenValidationResponse tokenValidationResponse = response.data();
                        String userId = tokenValidationResponse.userId();
                        String authorization = String.join(",", tokenValidationResponse.userAuthorities());
                        exchange.getRequest().mutate()
                                .header("X-User-Id", userId)
                                .header("X-User-Authorization", authorization)
                                .build();
                        return chain.filter(exchange);
                    })
                    .onErrorResume(ex -> {
                        log.error("Error during token validation", ex);
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    public static class Config {

    }
}

package com.quizguru.gateway;

import com.quizguru.gateway.filter.AuthenticationPrefilter;
import com.quizguru.gateway.utils.CustomHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(
            RouteLocatorBuilder builder,
            AuthenticationPrefilter authenticationPrefilter) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/quizzes/**")
                        .filters(f -> f
                                .filter(authenticationPrefilter.apply(new AuthenticationPrefilter.Config()))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}")
                                .circuitBreaker(config -> config.setName("quizCircuitBreaker")
                                        .setFallbackUri("forward:/contact-support"))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://quizzes")
                )
                .route(p -> p
                        .path("/api/v1/records/**")
                        .filters(f -> f
                                .filter(authenticationPrefilter.apply(new AuthenticationPrefilter.Config()))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://records")
                )
                .route(p -> p
                        .path("/api/v1/libraries/**")
                        .filters(f -> f
                                .filter(authenticationPrefilter.apply(new AuthenticationPrefilter.Config()))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://libraries")
                )
                .route(p -> p
                        .path("/api/v1/auth/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}")
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://auth-server")
                )
                .build();
    }

    /***
     * Every second, specific user can only request once
     * Ref: <a href="https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/gatewayfilter-factories/requestratelimiter-factory.html">...</a>
     * @return RedisRateLimiter
     */
    @Bean
    RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter(1,1,1);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(CustomHeaders.X_USER_ID))
                .switchIfEmpty(Mono.justOrEmpty(getClientIp(exchange)))
                .defaultIfEmpty("anonymous");
    }

    private String getClientIp(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
    }
}
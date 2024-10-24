package com.quizguru.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(
            RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/quizzes/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}")
                                .circuitBreaker(config -> config.setName("quizCircuitBreaker")
                                        .setFallbackUri("forward:/contact-support"))

                        )
                        .uri("lb://quizzes")
                )
                .route(p -> p
                        .path("/api/v1/records/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://records")
                )
                .route(p -> p
                        .path("/api/v1/libraries/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://libraries")
                )
                .route(p -> p
                        .path("/api/v1/customers/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}")
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://customers")
                )
                .build();
    }

    /***
     * Every second, specific user can only request twice
     * Ref: <a href="https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/gatewayfilter-factories/requestratelimiter-factory.html">...</a>
     * @return RedisRateLimiter
     */
    @Bean
    RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter(2,10,1);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getName())
                .switchIfEmpty(Mono.justOrEmpty(getClientIp(exchange)))
                .defaultIfEmpty("anonymous");
    }


    private String getClientIp(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
    }
}
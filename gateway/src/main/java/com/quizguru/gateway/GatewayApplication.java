package com.quizguru.gateway;

import com.quizguru.gateway.filter.AuthenticationPrefilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://quizzes")
                )
                .route(p -> p
                        .path("/api/v1/records/**")
                        .filters(f -> f
                                .filter(authenticationPrefilter.apply(new AuthenticationPrefilter.Config()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://records")
                )
                .route(p -> p
                        .path("/api/v1/libraries/**")
                        .filters(f -> f
                                .filter(authenticationPrefilter.apply(new AuthenticationPrefilter.Config()))
                                .rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://libraries")
                )
                .route(p -> p
                        .path("/api/v1/auth/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://auth-server")
                )
                .build();
    }
}
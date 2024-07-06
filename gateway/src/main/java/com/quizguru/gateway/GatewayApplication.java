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
                        .path("/contexts/**")
                        .filters(f -> f.rewritePath("/contexts(?<segment>/?.*)", "$\\{segment}")
								.filter(authenticationPrefilter.apply(new AuthenticationPrefilter.Config())))
                        .uri("lb://contexts")
                )
                .route(p -> p
                        .path("/auth/**")
                        .uri("lb://auth-server")
                )
                .build();
    }
}
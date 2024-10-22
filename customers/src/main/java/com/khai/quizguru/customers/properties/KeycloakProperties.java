package com.khai.quizguru.customers.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String authServerUrl;
    private String realm;
    private String resource;
    private Credentials credentials = new Credentials();

    @Getter
    @Setter
    public class Credentials {
        private String secret;
    }
}

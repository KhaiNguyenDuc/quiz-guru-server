package com.quizguru.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "refresh-token")
@Data
public class RefreshTokenConfig {
    private int expirationMs;
}

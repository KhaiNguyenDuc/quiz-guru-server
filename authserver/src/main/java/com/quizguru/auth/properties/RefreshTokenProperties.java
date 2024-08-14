package com.quizguru.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "refresh-token")
@Data
public class RefreshTokenProperties {
    private int expirationMs;
}

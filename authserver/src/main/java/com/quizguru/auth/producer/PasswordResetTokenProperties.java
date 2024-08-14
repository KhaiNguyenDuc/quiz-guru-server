package com.quizguru.auth.producer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "password-reset-token")
@Data
public class PasswordResetTokenProperties {
    private int expirationMs;
}

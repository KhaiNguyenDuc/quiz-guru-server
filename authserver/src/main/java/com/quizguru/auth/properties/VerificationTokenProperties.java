package com.quizguru.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "verification-token")
public class VerificationTokenProperties {
    private int expirationMs;
}

package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.config.gemini;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "generate")
@Data
@Component
public class GenerateProperties {
    private String provider;
    private Map<String, Provider> providers = new HashMap<>();

    @Data
    public static class Provider {
        public String model;
        public String apiKey;
        public String apiURL;
    }
    public Provider resolve(String providerName) {
        Provider provider = providers.get(providerName);
        if (provider == null || provider.getApiURL() == null || provider.getApiURL().isBlank()) {
            throw new IllegalArgumentException("Missing provider config for: " + providerName);
        }
        return provider;
    }
}

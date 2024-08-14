package com.quizguru.generates.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "generate")
@Data
@Component
public class GenerateProperties {
    public String model;
    public String apiKey;
    public String apiURL;
}

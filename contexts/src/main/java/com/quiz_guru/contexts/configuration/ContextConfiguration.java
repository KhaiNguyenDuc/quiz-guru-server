package com.quiz_guru.contexts.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "version")
@Data
public class ContextConfiguration {
    private String profile;
}

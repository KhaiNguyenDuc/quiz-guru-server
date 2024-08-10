package com.quizguru.libraries.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "dictionary")
@Setter
public class DictionaryProperties {
    private String apiKey;
    private String url;
    private String host;
}


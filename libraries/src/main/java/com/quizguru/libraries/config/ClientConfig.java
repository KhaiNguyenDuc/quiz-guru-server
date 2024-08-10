package com.quizguru.libraries.config;

import com.quizguru.libraries.properties.DictionaryProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientConfig {

    private final DictionaryProperties dictionaryProperties;
    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-RapidAPI-Key", dictionaryProperties.getApiKey());
            request.getHeaders().add("X-RapidAPI-Host", dictionaryProperties.getHost());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}

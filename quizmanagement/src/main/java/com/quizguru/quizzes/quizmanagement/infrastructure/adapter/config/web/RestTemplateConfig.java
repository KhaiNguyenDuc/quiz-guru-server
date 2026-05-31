package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
}

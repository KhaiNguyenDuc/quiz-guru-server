package com.quizguru.generates;

import com.quizguru.generates.amqp.properties.AmqpProperties;
import com.quizguru.generates.amqp.properties.GenerateProperties;
import com.quizguru.generates.amqp.properties.PromptProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties({GenerateProperties.class, PromptProperties.class, AmqpProperties.class})
public class GenerateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateApplication.class, args);
	}

	@Bean
	public RestTemplate template(@Qualifier("generateProperties") GenerateProperties configuration){
		RestTemplate restTemplate=new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().add("Authorization", "Bearer " + configuration.getApiKey());
			return execution.execute(request, body);
		});
		return restTemplate;
	}
}

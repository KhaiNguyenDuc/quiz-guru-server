package com.quizguru.contexts;

import com.quizguru.contexts.amqp.properties.AmqpProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(AmqpProperties.class)
public class ContextApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContextApplication.class, args);
	}
}

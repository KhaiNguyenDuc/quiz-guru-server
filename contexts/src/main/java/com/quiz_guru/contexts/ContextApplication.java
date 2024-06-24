package com.quiz_guru.contexts;

import com.quiz_guru.contexts.configuration.ContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(ContextConfiguration.class)
public class ContextApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContextApplication.class, args);
	}
}

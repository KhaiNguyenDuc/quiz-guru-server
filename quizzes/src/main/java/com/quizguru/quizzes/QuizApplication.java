package com.quizguru.quizzes;

import com.quizguru.quizzes.properties.AmqpProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(AmqpProperties.class)
public class QuizApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}
}

package com.quizguru.users;

import com.quizguru.users.properties.JavaMailSenderProperties;
import com.quizguru.users.properties.AmqpProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AmqpProperties.class, JavaMailSenderProperties.class})
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}

package com.quizguru.auth;

import com.quizguru.auth.producer.PasswordResetTokenProperties;
import com.quizguru.auth.properties.AmqpProperties;
import com.quizguru.auth.properties.JwtProperties;
import com.quizguru.auth.properties.RefreshTokenProperties;
import com.quizguru.auth.properties.VerificationTokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		JwtProperties.class,
		RefreshTokenProperties.class,
		VerificationTokenProperties.class,
		AmqpProperties.class,
		PasswordResetTokenProperties.class
})
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}

package com.quizguru.libraries;

import com.quizguru.libraries.properties.DictionaryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DictionaryProperties.class)
public class LibrariesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrariesApplication.class, args);
	}

}

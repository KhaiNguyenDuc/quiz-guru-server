package com.quizguru.quizzes.quizmanagement;

import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.config.gemini.GenerateProperties;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.config.prompt.PromptProperties;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.config.rabbitmq.AmqpProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({AmqpProperties.class, PromptProperties.class, GenerateProperties.class})
public class QuizManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizManagementApplication.class, args);
    }

}

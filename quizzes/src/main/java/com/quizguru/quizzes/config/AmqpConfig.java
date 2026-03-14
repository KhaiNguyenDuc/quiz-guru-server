package com.quizguru.quizzes.config;

import com.quizguru.quizzes.properties.AmqpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AmqpConfig {
    private final ConnectionFactory connectionFactory;
    private final AmqpProperties amqpProperties;

//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        // This forces the admin to declare all beans of type Queue, Exchange, and Binding
//        // found in the application context as soon as the app starts.
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }
//
//    @Bean
//    public ApplicationRunner runner(RabbitAdmin rabbitAdmin, AmqpProperties amqpProperties) {
//        return args -> {
//            System.out.println("--- AMQP DIAGNOSTIC START ---");
//
//            // Test 1: Check if properties are binding correctly
//            String generationQueue = amqpProperties.getQueues().getGeneration();
//            System.out.println("Property Check - Generation Queue Name: " + generationQueue);
//
//            if (generationQueue == null) {
//                System.err.println("CRITICAL ERROR: Queue properties are NULL. Check application.yml indentation!");
//            } else {
//                // Test 2: Force Declaration
//                System.out.println("Triggering manual declaration...");
//                try {
//                    rabbitAdmin.initialize();
//                    System.out.println("RabbitAdmin.initialize() completed. Check RabbitMQ UI now.");
//                } catch (Exception e) {
//                    System.err.println("CONNECTION ERROR: Could not connect to RabbitMQ.");
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("--- AMQP DIAGNOSTIC END ---");
//        };
//    }

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.amqpProperties.getExchanges().getInternal());
    }


    @Bean
    public Queue generationQueue() {
        return new Queue(this.amqpProperties.getQueues().getGeneration());
    }

    @Bean
    public Queue textVocabQueue() {
        return new Queue(this.amqpProperties.getQueues().getTextVocab());
    }

    @Bean
    public Queue docFileVocabQueue() {
        return new Queue(this.amqpProperties.getQueues().getFileVocab());
    }

    @Bean
    public Queue listVocabQueue() {
        return new Queue(this.amqpProperties.getQueues().getListVocab());
    }

    @Bean
    public Binding bindingGenerateRequest() {
        return BindingBuilder
                .bind(generationQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalGeneration());
    }

    @Bean
    public Binding bindingTextVocabRequest() {
        return BindingBuilder
                .bind(textVocabQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalTextVocab());
    }

    @Bean
    public Binding bindingDocFileVocabRequest() {
        return BindingBuilder
                .bind(docFileVocabQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalFileVocab());
    }

    @Bean
    public Binding bindingListVocabRequest() {
        return BindingBuilder
                .bind(listVocabQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalListVocab());
    }


    @Bean
    public AmqpTemplate amqpTemplate () {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }
    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }
}


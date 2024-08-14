package com.quizguru.quizzes.config;

import com.quizguru.quizzes.properties.AmqpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AmqpConfig {
    private final ConnectionFactory connectionFactory;
    private final AmqpProperties amqpProperties;

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


package com.quizguru.auth.config;

import com.quizguru.auth.properties.AmqpProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AmqpConfig {

    /*
    * Need spring actuator to open a connection on start up.
    * If not, no queues is created up util @RabbitListener run
    * Ref: https://stackoverflow.com/questions/64857283/spring-amqp-rabbitmq-connection-is-not-created-on-application-startup
    * */

    private final ConnectionFactory connectionFactory;
    private final AmqpProperties amqpProperties;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.amqpProperties.getExchanges().getInternal());
    }

    @Bean
    public Queue verifyMailQueue() {
        return new Queue(this.amqpProperties.getQueues().getVerificationMail());
    }

    @Bean
    public Queue passwordResetMailQueue() {
        return new Queue(this.amqpProperties.getQueues().getPasswordResetMail());
    }

    @Bean
    public Queue baseMailQueue() {
        return new Queue(this.amqpProperties.getQueues().getBaseMail());
    }

    @Bean
    public Binding bindingVerifyMailRequest() {
         return BindingBuilder
                .bind(verifyMailQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalVerificationMail());
    }

    @Bean
    public Binding bindingPasswordResetMailRequest() {
        return BindingBuilder
                .bind(passwordResetMailQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalPasswordResetMail());
    }

    @Bean
    public Binding bindingBaseMailRequest() {
        return BindingBuilder
                .bind(baseMailQueue())
                .to(internalTopicExchange())
                .with(this.amqpProperties.getRoutingKeys().getInternalBaseMail());
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


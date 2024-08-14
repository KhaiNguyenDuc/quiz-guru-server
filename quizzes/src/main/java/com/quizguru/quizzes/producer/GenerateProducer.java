package com.quizguru.quizzes.producer;

import com.quizguru.quizzes.properties.AmqpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateProducer {

    private final AmqpProperties amqpProperties;
    private final AmqpTemplate amqpTemplate;

    public void publishGenerateRequest(Object payload){
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalGeneration(),payload);
    }

    public void publishTextVocabRequest(Object payload){
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalTextVocab(),payload);
    }

    public void publishDocFileVocabRequest(Object payload){
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalFileVocab(), payload);
    }
}
package com.quizguru.quizzes.amqp.producer;

import com.quizguru.quizzes.amqp.properties.AmqpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmqpProducer {

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

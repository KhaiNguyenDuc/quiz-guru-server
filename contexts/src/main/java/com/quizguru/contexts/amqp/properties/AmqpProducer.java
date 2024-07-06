package com.quizguru.contexts.amqp.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmqpProducer {

    private final AmqpProperties amqpProperties;
    private final AmqpTemplate amqpTemplate;

    public void publish(Object payload){
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalGeneration(),payload);
    }
}

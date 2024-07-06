package com.quizguru.contexts.amqp.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "amqp")
@Setter
public class AmqpProperties {

    private Exchanges exchanges;
    private Queues queues;
    private RoutingKeys routingKeys;

    @Getter
    @Setter
    public static class Exchanges {
        private String internal;
    }

    @Getter
    @Setter
    public static class Queues {
        private String generation;
    }

    @Getter
    @Setter
    public static class RoutingKeys {
        private String internalGeneration;
    }
}

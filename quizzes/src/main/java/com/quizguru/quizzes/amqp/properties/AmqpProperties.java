package com.quizguru.quizzes.amqp.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
        private String textVocab;
        private String fileVocab;
    }

    @Getter
    @Setter
    public static class RoutingKeys {
        private String internalGeneration;
        private String internalTextVocab;
        private String internalFileVocab;
    }
}

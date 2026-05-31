package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.config.rabbitmq;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "amqp")
public class AmqpProperties {

    private Exchanges exchanges = new Exchanges();
    private Queues queues = new Queues();
    private RoutingKeys routingKeys = new RoutingKeys();

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
        private String listVocab;
    }

    @Getter
    @Setter
    public static class RoutingKeys {
        private String internalGeneration;
        private String internalTextVocab;
        private String internalFileVocab;
        private String internalListVocab;
    }
}

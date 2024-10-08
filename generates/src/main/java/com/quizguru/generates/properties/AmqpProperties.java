package com.quizguru.generates.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "amqp")
@Setter
public class AmqpProperties {

    private Queues queues;

    @Getter
    @Setter
    public static class Queues {
        private String generation;
        private String textVocab;
        private String fileVocab;
        private String listVocab;
    }
}

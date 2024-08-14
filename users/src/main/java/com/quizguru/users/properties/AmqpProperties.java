package com.quizguru.users.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "amqp")
@Component
@Getter
@Setter
public class AmqpProperties {

    private Queues queues;

    @Getter
    @Setter
    public static class Queues {
        private String verificationMail;
        private String passwordResetMail;
        private String baseMail;
    }
}
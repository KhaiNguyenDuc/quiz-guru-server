package com.quizguru.auth.properties;

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
        private String verificationMail;
        private String passwordResetMail;
        private String baseMail;
    }

    @Getter
    @Setter
    public static class RoutingKeys {
        private String internalVerificationMail;
        private String internalPasswordResetMail;
        private String internalBaseMail;
    }
}

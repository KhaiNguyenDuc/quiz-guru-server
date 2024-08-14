package com.quizguru.auth.producer;

import com.quizguru.auth.dto.request.EmailRequest;
import com.quizguru.auth.properties.AmqpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailProducer {

    private final AmqpProperties amqpProperties;
    private final AmqpTemplate amqpTemplate;

    public void sendVerifyMail(EmailRequest emailRequest){
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalVerificationMail(),emailRequest);
    }

    public void sendResetPasswordMail(EmailRequest emailRequest) {
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalVerificationMail(),emailRequest);
    }

    public void sendMail(EmailRequest emailRequest) {
        amqpTemplate.convertAndSend(amqpProperties.getExchanges().getInternal(),amqpProperties.getRoutingKeys().getInternalVerificationMail(),emailRequest);
    }
}

package com.quizguru.users.consumer;

import com.quizguru.users.dto.EmailRequest;
import com.quizguru.users.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "#{amqpProperties.queues.verificationMail}")
    public void sendVerifyMail(EmailRequest email) {
        emailService.sendVerificationEmail(email);
    }

    @RabbitListener(queues = "#{amqpProperties.queues.passwordResetMail}")
    public void sendPasswordResetMail(EmailRequest email) {
        emailService.sendPasswordResetEmail(email);
    }

    @RabbitListener(queues = "#{amqpProperties.queues.baseMail}")
    public void sendMail(EmailRequest email) {
        emailService.sendMail(email);
    }
}

package com.quizguru.users.service.impl;

import com.quizguru.users.dto.EmailRequest;
import com.quizguru.users.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendVerificationEmail(EmailRequest emailRequest) {
        try{
            String templateName = "verification.html";
            Context context = new Context();
            context.setVariable("message", emailRequest.body());
            MimeMessage message = constructEmailHtmlMessage(emailRequest, context, templateName);
            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("Email Service: Send verify mail fail. Message: " + e.getMessage());
        }
    }

    @Override
    public void sendPasswordResetEmail(EmailRequest emailRequest) {
        try{
            String templateName = "password-reset.html";
            Context context = new Context();
            context.setVariable("message", emailRequest.body());
            MimeMessage message = constructEmailHtmlMessage(emailRequest, context, templateName);
            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("Email Service: Send password reset mail fail. Message: " + e.getMessage());
        }
    }

    @Override
    public void sendMail(EmailRequest emailRequest) {
        try{
            String templateName = "template.html";
            Context context = new Context();
            context.setVariable("message", emailRequest.body());
            MimeMessage message = constructEmailHtmlMessage(emailRequest, context, templateName);
            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("Email Service: Send password reset mail fail. Message: " + e.getMessage());
        }
    }

    public MimeMessage constructEmailHtmlMessage(EmailRequest emailRequest, Context context, String templateName) throws MessagingException {
        MimeMessage  mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailRequest.to());
        helper.setSubject(emailRequest.subject());
        String htmlContent = templateEngine.process(templateName, context);
        helper.setText(htmlContent, true);
        return mimeMessage;
    }
}

package com.quizguru.users.service;

import com.quizguru.users.dto.EmailRequest;

public interface EmailService {
    void sendVerificationEmail(EmailRequest email);

    void sendPasswordResetEmail(EmailRequest email);

    void sendMail(EmailRequest email);
}

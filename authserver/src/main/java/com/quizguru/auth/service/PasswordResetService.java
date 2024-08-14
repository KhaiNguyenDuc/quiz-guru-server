package com.quizguru.auth.service;

import com.quizguru.auth.dto.request.ResetPasswordRequest;

public interface PasswordResetService {
    String sendResetPassword(String username);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}

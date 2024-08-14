package com.quizguru.auth.service;

import com.quizguru.auth.dto.response.RegisterResponse;
import com.quizguru.auth.model.VerificationToken;

public interface VerificationTokenService {
    Boolean verifyUser(String token, String username);

    Boolean resendVerifyToken(String username);

    VerificationToken createToken(String userId);

    void sendVerifyToken(RegisterResponse registerResponse, VerificationToken token);
}

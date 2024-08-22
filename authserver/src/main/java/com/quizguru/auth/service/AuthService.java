package com.quizguru.auth.service;

import com.quizguru.auth.dto.request.LoginCredentials;
import com.quizguru.auth.dto.request.RegisterCredentials;
import com.quizguru.auth.dto.response.RegisterResponse;
import com.quizguru.auth.dto.response.TokenResponse;
import com.quizguru.auth.dto.response.TokenValidationResponse;
import com.quizguru.auth.dto.response.UserResponse;

public interface AuthService {
    TokenValidationResponse validateJwtToken(String token);
    TokenResponse authenticate(LoginCredentials loginCredentials);
    RegisterResponse register(RegisterCredentials registerCredentials);

    UserResponse findById(String id);

    String findRoleFromUserId(String userId);
}

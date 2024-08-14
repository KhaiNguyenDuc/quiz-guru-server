package com.quizguru.auth.service;

import com.quizguru.auth.dto.request.RefreshTokenRequest;
import com.quizguru.auth.dto.response.TokenResponse;
import com.quizguru.auth.model.RefreshToken;
import com.quizguru.auth.model.User;

public interface RefreshTokenService {
    TokenResponse renewAccessToken(RefreshTokenRequest refreshTokenRequest);
    RefreshToken generateOrRenewRefreshToken(String userId);
}

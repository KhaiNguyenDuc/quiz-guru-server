package com.quizguru.auth.service;

import com.quizguru.auth.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(String userId);
}

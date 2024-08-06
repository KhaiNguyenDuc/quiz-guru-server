package com.quizguru.auth.mapper;

import com.quizguru.auth.dto.response.RefreshTokenResponse;
import com.quizguru.auth.model.RefreshToken;

public class TokenMapper {
    public static RefreshTokenResponse tokenToRefreshTokenResponse(RefreshToken refreshToken){
        return  RefreshTokenResponse.builder()
                .token(refreshToken.getToken())
                .expiredDate(refreshToken.getExpiredDate())
                .build();
    }
}

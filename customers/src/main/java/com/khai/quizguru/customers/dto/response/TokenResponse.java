package com.khai.quizguru.customers.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(String accessToken, String refreshToken){
}

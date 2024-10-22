package com.khai.quizguru.customers.dto.response;

import lombok.Builder;
@Builder
public record RegisterResponse(String id, String username, String email) {
}

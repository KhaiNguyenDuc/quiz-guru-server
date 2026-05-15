package com.khai.quizguru.customers.dto.request;

import lombok.Builder;

@Builder
public record RegisterCredentials(String username, String email, String password) {
}

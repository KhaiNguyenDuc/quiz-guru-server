package com.khai.quizguru.customers.dto.request;

public record ResetPasswordRequest(String newPassword, String token, String email) {
}

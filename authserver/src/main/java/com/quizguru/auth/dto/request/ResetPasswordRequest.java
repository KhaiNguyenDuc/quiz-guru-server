package com.quizguru.auth.dto.request;

public record ResetPasswordRequest(String newPassword, String token, String email) {
}

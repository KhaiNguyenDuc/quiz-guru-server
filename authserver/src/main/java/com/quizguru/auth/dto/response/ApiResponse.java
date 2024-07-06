package com.quizguru.auth.dto.response;

public record ApiResponse<T>(T data, String message) {
}

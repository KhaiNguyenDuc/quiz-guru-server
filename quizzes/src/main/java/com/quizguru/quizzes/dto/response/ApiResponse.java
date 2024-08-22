package com.quizguru.quizzes.dto.response;

public record ApiResponse<T>(T data, String message) {
}

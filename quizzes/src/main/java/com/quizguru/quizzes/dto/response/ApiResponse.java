package com.quizguru.quizzes.dto.response;

public record ApiResponse<T>(T object, String message) {
}

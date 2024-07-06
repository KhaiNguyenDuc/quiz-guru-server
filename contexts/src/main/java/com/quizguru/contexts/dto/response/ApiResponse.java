package com.quizguru.contexts.dto.response;

public record ApiResponse<T>(T object, String message) {
}

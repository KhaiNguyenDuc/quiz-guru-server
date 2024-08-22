package com.quizguru.libraries.dto.response;

public record ApiResponse<T>(T data, String message) {
}

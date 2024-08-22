package com.quizguru.generates.dto.response;

public record ApiResponse<T>(T data, String message) {
}


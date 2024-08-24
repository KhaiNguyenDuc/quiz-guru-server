package com.quizguru.records.dto.response;

public record ApiResponse<T>(T data, String message) {
}

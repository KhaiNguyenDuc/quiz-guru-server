package com.quizguru.gateway.dto;

public record ApiResponse<T>(T data, String message) {
}

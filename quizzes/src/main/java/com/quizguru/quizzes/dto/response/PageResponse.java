package com.quizguru.quizzes.dto.response;

public record PageResponse<T>(T data, Integer page, Integer size, Integer totalPages, Integer totalElements) {
}

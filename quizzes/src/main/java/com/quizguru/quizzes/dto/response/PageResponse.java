package com.quizguru.quizzes.dto.response;

public record PageResponse<T>(T content, Integer page, Integer size, Integer totalPages, Integer totalElements) {
}

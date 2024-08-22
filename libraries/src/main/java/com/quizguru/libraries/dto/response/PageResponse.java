package com.quizguru.libraries.dto.response;

import lombok.Builder;

@Builder
public record PageResponse<T>(T data, Integer page, Integer size, Integer totalPages, Integer totalElements) {
}

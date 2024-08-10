package com.quizguru.libraries.dto.response;

import lombok.Builder;

@Builder
public record PageResponse<T>(T content, Integer page, Integer size, Integer totalPages, Integer totalElements) {
}

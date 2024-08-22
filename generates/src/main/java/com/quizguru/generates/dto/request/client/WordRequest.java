package com.quizguru.generates.dto.request.client;

import lombok.Builder;

@Builder
public record WordRequest(
        String name,
        String definition,
        String content) {
}

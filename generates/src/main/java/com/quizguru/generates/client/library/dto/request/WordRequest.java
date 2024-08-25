package com.quizguru.generates.client.library.dto.request;

import lombok.Builder;

@Builder
public record WordRequest(
        String name,
        String definition,
        String content) {
}

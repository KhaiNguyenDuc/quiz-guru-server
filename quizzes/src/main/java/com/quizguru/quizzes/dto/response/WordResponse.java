package com.quizguru.quizzes.dto.response;

import lombok.Builder;

@Builder
public record WordResponse(String id, String name, String definition, String content) {
}

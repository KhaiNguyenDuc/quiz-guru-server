package com.quizguru.quizzes.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
public record WordResponse(String id, String name, String definition, String content) {
}

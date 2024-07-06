package com.quizguru.contexts.dto.response;

import lombok.Builder;

@Builder
public record GenerateContextResponse(String id, Integer problemNumber, String level, Integer duration, String type, String language, String givenText) {
}

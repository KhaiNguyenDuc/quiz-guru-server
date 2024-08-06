package com.quizguru.quizzes.dto.response;

import lombok.Builder;

@Builder
public record GenerateQuizResponse(String id, Integer problemNumber, String level, Integer duration, String type, String language, String givenText) {
}

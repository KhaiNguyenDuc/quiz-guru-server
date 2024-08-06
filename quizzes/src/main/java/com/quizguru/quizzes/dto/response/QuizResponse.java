package com.quizguru.quizzes.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QuizResponse(String id, Integer problemNumber, String level, Integer duration, String type, String language, String givenText) {
}

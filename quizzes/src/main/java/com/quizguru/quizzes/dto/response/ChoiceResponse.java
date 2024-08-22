package com.quizguru.quizzes.dto.response;

import lombok.Builder;

@Builder
public record ChoiceResponse(String id, String name, Boolean isCorrect) { }

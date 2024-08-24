package com.quizguru.records.dto.client;

import lombok.Builder;

@Builder
public record ChoiceResponse(String id, String name, Boolean isCorrect) { }

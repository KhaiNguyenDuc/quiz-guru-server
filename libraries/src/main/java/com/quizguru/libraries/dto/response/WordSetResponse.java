package com.quizguru.libraries.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record WordSetResponse(String id, String name, List<WordResponse> words, Integer wordNumber, String quizId, Integer reviewNumber, Instant createdAt) {
}


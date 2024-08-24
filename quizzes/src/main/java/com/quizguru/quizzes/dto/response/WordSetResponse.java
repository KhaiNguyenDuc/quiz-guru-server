package com.quizguru.quizzes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record WordSetResponse(String id, String name, List<WordResponse> words, Integer wordNumber, String quizId, Integer reviewNumber) {
}


package com.quizguru.quizzes.client.library.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record WordSetResponse(String id, String name, List<WordResponse> words, Integer wordNumber, String quizId, Integer reviewNumber) {
}


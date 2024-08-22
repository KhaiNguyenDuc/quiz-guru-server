package com.quizguru.generates.dto.request.client;

import lombok.Builder;

import java.util.List;

@Builder
public record WordSetRequest(String id, String name, String quizId, List<WordRequest> words) {
}

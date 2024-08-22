package com.quizguru.libraries.dto.request;

import java.util.List;

public record WordSetRequest(String id, String name, String quizId, List<WordRequest> words) {
}

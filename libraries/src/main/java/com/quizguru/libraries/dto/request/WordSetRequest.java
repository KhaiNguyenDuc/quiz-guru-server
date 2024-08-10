package com.quizguru.libraries.dto.request;

import java.util.List;

public record WordSetRequest(String name, String quizId, List<WordRequest> words) {
}

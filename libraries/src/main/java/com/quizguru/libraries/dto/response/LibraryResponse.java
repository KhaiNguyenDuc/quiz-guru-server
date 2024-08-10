package com.quizguru.libraries.dto.response;

import java.util.List;

public record LibraryResponse(String id, List<WordSetResponse> wordSets) {
}

package com.quizguru.quizzes.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record DetailQuizResponse(
        String id,
        List<QuestionResponse> questions,
        String givenText,
        String type,
        Integer duration,
        WordSetResponse wordSet,
        String language
) {
}


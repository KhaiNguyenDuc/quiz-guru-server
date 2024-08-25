package com.quizguru.quizzes.dto.response;

import com.quizguru.quizzes.client.library.dto.response.WordSetResponse;
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


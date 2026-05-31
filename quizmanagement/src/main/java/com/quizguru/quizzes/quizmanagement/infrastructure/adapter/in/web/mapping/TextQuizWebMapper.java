package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.mapping;

import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizResult;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.response.GenerateQuizResponse;

public class TextQuizWebMapper {
    public static GenerateQuizResponse mapToWebResponse(CreateTextQuizResult result) {
        return GenerateQuizResponse.builder()
                .quizId(result.quizId())
                .number(result.number())
                .level(result.level())
                .duration(result.duration())
                .type(result.type())
                .language(result.language())
                .givenText(result.givenText())
                .build();
    }
}

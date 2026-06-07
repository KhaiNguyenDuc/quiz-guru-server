package com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.mapping;

import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizCommand;
import com.quizguru.quizzes.quizmanagement.application.port.in.textquiz.create.CreateTextQuizResult;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.request.TextQuizRawWebRequest;
import com.quizguru.quizzes.quizmanagement.infrastructure.adapter.in.web.response.GenerateQuizResponse;

public class TextQuizWebMapper {
    public static GenerateQuizResponse toResponse(CreateTextQuizResult result) {
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

    public static CreateTextQuizCommand toCommand(String userId, TextQuizRawWebRequest request){
        return CreateTextQuizCommand.builder()
                .userId(userId)
                .quizType(request.type())
                .number(request.number())
                .language(request.language())
                .level(request.language())
                .duration(request.duration())
                .content(request.content())
                .htmlContext(request.htmlContext())
                .build();
    }
}

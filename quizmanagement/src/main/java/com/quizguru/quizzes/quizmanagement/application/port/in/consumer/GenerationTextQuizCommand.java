package com.quizguru.quizzes.quizmanagement.application.port.in.consumer;

import com.quizguru.quizzes.quizmanagement.application.port.out.GenerateTextQuizPrompt;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.Level;
import com.quizguru.quizzes.quizmanagement.domain.model.enums.QuizType;

public record GenerationTextQuizCommand(
        String userId,
        String quizId,
        String givenText,
        QuizType quizType,
        Integer number,
        String language,
        Level level,
        Integer duration
) {
    public static GenerationTextQuizCommand fromPrompt(GenerateTextQuizPrompt prompt){
        return new GenerationTextQuizCommand(
                prompt.userId(),
                prompt.quizId(),
                prompt.givenText(),
                prompt.quizType(),
                prompt.number(),
                prompt.language(),
                prompt.level(),
                prompt.duration()
        );
    }
}

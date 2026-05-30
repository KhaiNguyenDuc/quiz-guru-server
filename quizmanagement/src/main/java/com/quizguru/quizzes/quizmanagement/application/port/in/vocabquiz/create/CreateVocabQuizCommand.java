package com.quizguru.quizzes.quizmanagement.application.port.in.vocabquiz.create;

import java.util.List;

public record CreateVocabQuizCommand(
        String userId,
        String quizId,
        String wordSetId,
        String wordSetName,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration,
        List<String> normalizedVocabWords
) {
    public CreateVocabQuizCommand withId(String userId, String quizId){
        return new CreateVocabQuizCommand(
                userId, quizId, wordSetId(),
                wordSetName(), type(), number(), language(), level(), duration(), normalizedVocabWords());
    }
}

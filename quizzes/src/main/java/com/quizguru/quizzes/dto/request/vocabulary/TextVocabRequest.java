package com.quizguru.quizzes.dto.request.vocabulary;

public record TextVocabRequest(
        String userId,
        String quizId,
        String wordSetId,
        String wordSetName,
        String content,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {

    public TextVocabRequest withId(String userId, String quizId){
        return new TextVocabRequest(userId, quizId, wordSetId(), wordSetName(), content(), type(), number(), language(), level(), duration());
    }
}
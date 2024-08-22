package com.quizguru.quizzes.dto.request.vocabulary;

import java.util.List;

public record ListToVocabRequest(
        String userId,
        String quizId,
        String wordSetId,
        String wordSetName,
        List<String> names,
        String type,
        Integer number,
        String language,
        String level,
        Integer duration
) {

    public ListToVocabRequest withId(String userId, String quizId){
        return new ListToVocabRequest(userId, quizId, wordSetId(), wordSetName(), names(), type(), number(), language(), level(), duration());
    }
}
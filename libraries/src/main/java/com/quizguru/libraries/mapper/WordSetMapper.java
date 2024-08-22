package com.quizguru.libraries.mapper;

import com.quizguru.libraries.dto.response.WordSetResponse;
import com.quizguru.libraries.model.WordSet;

import java.util.ArrayList;
import java.util.List;

public class WordSetMapper {
    public static WordSetResponse toWordSetResponse(WordSet wordSet) {
        return WordSetResponse.builder()
                .id(wordSet.getId())
                .wordNumber(wordSet.getWordNumber())
                .name(wordSet.getName())
                .quizId(wordSet.getQuizId())
                .reviewNumber(wordSet.getReviewNumber())
                .words(WordMapper.toWordResponses(wordSet.getWords()))
                .build();
    }

    public static List<WordSetResponse> toWordSetResponses(List<WordSet> wordSets) {
        List<WordSetResponse> wordSetResponses = new ArrayList<>();
        for (WordSet wordSet : wordSets) {
            WordSetResponse wordSetResponse = toWordSetResponse(wordSet);
            wordSetResponses.add(wordSetResponse);
        }
        return wordSetResponses;
    }
}

package com.quizguru.libraries.mapper;

import com.quizguru.libraries.dto.response.WordResponse;
import com.quizguru.libraries.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordMapper {
    public static List<WordResponse> toWordResponses(List<Word> words) {
        List<WordResponse> wordResponses = new ArrayList<>();
        for (Word word : words) {
            WordResponse wordResponse = WordResponse.builder()
                    .id(word.getId())
                    .name(word.getName())
                    .definition(word.getDefinition())
                    .build();
            wordResponses.add(wordResponse);
        }
        return wordResponses;
    }

    public static WordResponse toWordResponse(Word word) {
        return WordResponse.builder()
                .id(word.getId())
                .name(word.getName())
                .definition(word.getDefinition())
                .build();
    }
}

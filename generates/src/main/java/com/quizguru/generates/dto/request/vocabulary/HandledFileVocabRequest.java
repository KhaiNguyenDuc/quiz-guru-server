package com.quizguru.generates.dto.request.vocabulary;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class HandledFileVocabRequest extends TextVocabRequest {

    private String fileContent;

    @Override
    public String getText() {
       return this.fileContent;
    }
}

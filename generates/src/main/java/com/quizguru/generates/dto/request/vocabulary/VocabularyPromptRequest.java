package com.quizguru.generates.dto.request.vocabulary;

import com.quizguru.generates.dto.request.PromptRequest;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class VocabularyPromptRequest extends PromptRequest {
    public Integer numberOfWords;
    public String wordSetId;
    public String wordSetName = "";
    public Boolean isDoQuiz = false;
}

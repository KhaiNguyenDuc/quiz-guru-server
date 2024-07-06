package com.quizguru.generates.dto.request.text;

import com.quizguru.generates.dto.request.HasHtmlContent;
import com.quizguru.generates.dto.request.PromptRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BaseTextRequest extends PromptRequest implements HasHtmlContent {

    private String content;
    private String htmlContent;

    @Override
    public String getText() {
        return this.content;
    }

    @Override
    public String getHtmlContent() {
        return this.htmlContent;
    }
}

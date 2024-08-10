package com.quizguru.libraries.exception;

import com.quizguru.libraries.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefinitionNotFoundException extends RuntimeException{

    private String message;
    public DefinitionNotFoundException(String errorCode, Object...var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }
}

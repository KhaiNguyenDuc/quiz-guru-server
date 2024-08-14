package com.quizguru.auth.exception;

import com.quizguru.auth.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessDeniedException extends RuntimeException {
    private String message;
    public AccessDeniedException(String errorCode, Object...var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }
}

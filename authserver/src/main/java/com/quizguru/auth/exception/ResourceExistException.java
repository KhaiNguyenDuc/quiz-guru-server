package com.quizguru.auth.exception;

import com.quizguru.auth.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceExistException extends RuntimeException {
    private String message;
    public ResourceExistException(String errorCode, Object... var2)
    {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }
}

package com.quizguru.quizzes.exception;

import com.quizguru.quizzes.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidRequestException extends RuntimeException{
    private String message;
    public InvalidRequestException(String errorCode, Object... var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }


}

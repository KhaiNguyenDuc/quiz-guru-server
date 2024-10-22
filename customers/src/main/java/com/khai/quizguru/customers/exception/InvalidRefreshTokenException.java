package com.khai.quizguru.customers.exception;

import com.khai.quizguru.customers.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidRefreshTokenException extends RuntimeException {
    private String message;
    public InvalidRefreshTokenException(String errorCode, Object...var2){
        this.message = MessageUtils.getMessage(errorCode, var2);
    }
}

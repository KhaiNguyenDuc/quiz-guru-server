package com.khai.quizguru.customers.exception;

import com.khai.quizguru.customers.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidVerifyTokenException extends RuntimeException {
    private String message;
    public InvalidVerifyTokenException(String errorCode, Object...var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }
}

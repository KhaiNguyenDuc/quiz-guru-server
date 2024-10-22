package com.khai.quizguru.customers.exception;

import com.khai.quizguru.customers.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private String message;
    public ResourceNotFoundException(String errorCode, Object... var2)
    {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }
}

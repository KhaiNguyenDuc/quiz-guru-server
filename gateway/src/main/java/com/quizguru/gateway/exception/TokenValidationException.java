package com.quizguru.gateway.exception;

import org.springframework.web.service.annotation.HttpExchange;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message){
        super(message);
    }
}

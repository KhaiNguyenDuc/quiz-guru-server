package com.quizguru.gateway.client.auth.response;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message){
        super(message);
    }
}

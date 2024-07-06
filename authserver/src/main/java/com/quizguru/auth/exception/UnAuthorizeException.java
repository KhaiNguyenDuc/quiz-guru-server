package com.quizguru.auth.exception;

public class UnAuthorizeException extends RuntimeException{
    public UnAuthorizeException(String message){
        super(message);
    }
}

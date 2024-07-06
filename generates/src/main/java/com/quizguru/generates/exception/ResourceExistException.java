package com.quizguru.generates.exception;

public class ResourceExistException extends RuntimeException {
    public ResourceExistException(String message) {
        super(message);
    }
}

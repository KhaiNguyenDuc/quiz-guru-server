package com.quizguru.generates.exception;

public class InvalidRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 7338611633679911108L;

    public InvalidRequestException(String message) {
        super(message);
    }


}

package com.khai.quizguru.customers.exception;

import com.khai.quizguru.customers.dto.error.ExceptionDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<ExceptionDetails> handleResourceExistException(
            ResourceExistException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDetails> handleUnAuthorizeException(
            UnauthorizedException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.UNAUTHORIZED.toString(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ExceptionDetails> handleTokenValidationException(
            TokenValidationException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        log.error("Invalidate token:" + exceptionDetail.detail());
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidRefreshTokenException(
            InvalidRefreshTokenException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
       return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidVerifyTokenException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidVerifyTokenException(
            InvalidVerifyTokenException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetails> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.FORBIDDEN.toString(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidRequestException(
            InvalidRequestException ex, WebRequest request
    ) {
        ExceptionDetails exceptionDetail = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }
}

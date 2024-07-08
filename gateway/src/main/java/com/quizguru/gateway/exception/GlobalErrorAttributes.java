package com.quizguru.gateway.exception;

import com.quizguru.gateway.utils.Constant;
import com.quizguru.gateway.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

//Ref: https://stackoverflow.com/questions/66254535/spring-cloud-gateway-global-exception-handling-and-custom-error-response
@Component
@Slf4j
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
         Throwable error = getError(request);
         if(error instanceof WebClientResponseException webClientException){

             if (webClientException.getStatusCode() == HttpStatus.BAD_REQUEST) {
                 ExceptionDetails exceptionDetail = new ExceptionDetails(
                         HttpStatus.UNAUTHORIZED.toString(),
                         HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                         MessageUtils.getMessage(Constant.ERROR_CODE.TOKEN_INVALID)
                 );
                 return exceptionDetail.toMap(HttpStatus.UNAUTHORIZED);

             }
         }
         ExceptionDetails exceptionDetail = new ExceptionDetails(
                 HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                 HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                 error.getMessage()
         );
         return exceptionDetail.toMap(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

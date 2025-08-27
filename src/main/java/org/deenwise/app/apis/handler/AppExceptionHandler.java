package org.deenwise.app.apis.handler;

import org.deenwise.app.apis.exception.UserNotFoundException;
import org.deenwise.app.apis.response.ApiErrorResponse;
import org.deenwise.app.apis.response.AuthErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(exception = UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserDataNotFoundException(UserNotFoundException userNotFoundException) {
        ApiErrorResponse apiErrorResponse =
                ApiErrorResponse.builder()
                        .message(userNotFoundException.getMessage())
                        .timeStamp(LocalDateTime.now().toString())
                        .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(exception = NullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleNullPointerException(NullPointerException nullPointerException) {
        ApiErrorResponse apiErrorResponse =
                ApiErrorResponse.builder()
                        .message(nullPointerException.getMessage())
                        .timeStamp(LocalDateTime.now().toString())
                        .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(exception = InternalAuthenticationServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleInternalAuthServiceException(InternalAuthenticationServiceException internalAuthenticationServiceException) {
        ApiErrorResponse apiErrorResponse =
                ApiErrorResponse.builder()
                        .message(internalAuthenticationServiceException.getMessage())
                        .timeStamp(LocalDateTime.now().toString())
                        .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.OK);
    }
}

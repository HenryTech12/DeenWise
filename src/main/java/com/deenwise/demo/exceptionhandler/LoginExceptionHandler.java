package com.deenwise.demo.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class LoginExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        log.error("LOGIN ERROR: {}", usernameNotFoundException.getMessage());
        return new ResponseEntity<>(usernameNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        log.error("LOGIN ERROR: {}", badCredentialsException.getMessage());
        return new ResponseEntity<>(badCredentialsException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<String> handleInternalAuthServiceException(InternalAuthenticationServiceException internalAuthenticationServiceException) {
        log.error("ERROR: {}", internalAuthenticationServiceException.getMessage());
        return new ResponseEntity<>(internalAuthenticationServiceException.getMessage(),HttpStatus.UNAUTHORIZED);
    }

}

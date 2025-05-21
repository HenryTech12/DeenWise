package com.deenwise.demo.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@Slf4j
public class TemplateExceptionHandler {

    @ExceptionHandler(TemplateInputException.class)
    public ResponseEntity<String> handleTemplateException(TemplateInputException templateInputException) {
       log.error("ERROR: {}",templateInputException.getMessage());
       return new ResponseEntity<>(templateInputException.getMessage(), HttpStatus.NOT_FOUND);
    }
}

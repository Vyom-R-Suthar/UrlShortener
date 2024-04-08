package com.internship.UrlShortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handler1(){
        System.out.println("Inside handler1");
        return new ResponseEntity<>("Some Error happened, please try again", HttpStatus.BAD_REQUEST);
    }
}

package com.example.userservice.controlleradvices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ArithmeticException.class)
    public String handleArithmeticException(){
        return "arithmetic-exception";
    }
}

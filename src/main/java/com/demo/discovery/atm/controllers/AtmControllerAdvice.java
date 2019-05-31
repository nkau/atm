package com.demo.discovery.atm.controllers;

import com.demo.discovery.atm.exceptions.ATMSystemException;
import com.demo.discovery.atm.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AtmControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ATMSystemException.class)
    protected ResponseEntity<ErrorResponse> handleAccountNotFoundException(ATMSystemException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }
}

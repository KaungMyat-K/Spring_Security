package com.sec.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionHalder {

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Exception ex,HttpServletRequest request, HttpServletResponse response) {
            String errorMessage = ex.getMessage();
            return new ResponseEntity<Object>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }	
}

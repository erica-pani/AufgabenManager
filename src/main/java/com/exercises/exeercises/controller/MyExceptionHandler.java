package com.exercises.exeercises.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class MyExceptionHandler {
    

    @ExceptionHandler
    public ResponseEntity<?>  handleEntityNotFoundException(EntityNotFoundException exception) {

        return new ResponseEntity<>(null);
    }
}

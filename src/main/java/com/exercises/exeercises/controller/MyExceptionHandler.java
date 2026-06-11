package com.exercises.exeercises.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class MyExceptionHandler {
    

    @ExceptionHandler
    public ResponseEntity<?>  handleEntityNotFoundException(EntityNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Entity not found");
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler
    public ResponseEntity<?>  handleUsernameNotFoundException(UsernameNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Username not found");
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler
    public ResponseEntity<?>  handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Argument not allowed");
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}

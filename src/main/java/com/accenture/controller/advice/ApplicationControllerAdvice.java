package com.accenture.controller.advice;


import com.accenture.exception.PizzaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(PizzaException.class)
    public ResponseEntity<MessageError> handleFromageException(PizzaException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }


}

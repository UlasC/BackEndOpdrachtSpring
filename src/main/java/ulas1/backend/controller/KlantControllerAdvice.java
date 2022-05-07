package ulas1.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.KlantNotFoundException;

@ControllerAdvice
public class KlantControllerAdvice {
    @ExceptionHandler(KlantNotFoundException.class)
    public ResponseEntity<Object> handleKlantNotFoundException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

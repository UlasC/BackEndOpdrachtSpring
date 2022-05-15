package ulas1.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.OnderdeelNotFoundException;

@ControllerAdvice
public class OnderdeelControllerAdvice {
    @ExceptionHandler(OnderdeelNotFoundException.class)
    public ResponseEntity<Object> handleOnderdeelNotFoundException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());

    }
}

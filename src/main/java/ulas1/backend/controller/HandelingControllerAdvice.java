package ulas1.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.HandelingNotFoundException;
import ulas1.backend.exception.OnderdeelNotFoundException;


@ControllerAdvice
public class HandelingControllerAdvice {
        @ExceptionHandler(HandelingNotFoundException.class)
        public ResponseEntity<Object> handleHandelingNotFoundException(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());

        }
    }
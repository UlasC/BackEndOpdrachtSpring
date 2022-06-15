package ulas1.backend.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.AutoNotFoundException;
import ulas1.backend.exception.KlantHasNoCarException;

@ControllerAdvice
public class AutoControllerAdvice {
    @ExceptionHandler(AutoNotFoundException.class)
    public ResponseEntity<Object> handleAutoNotFoundException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(KlantHasNoCarException.class)
    public ResponseEntity<Object> handleKlantHasNoCarException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

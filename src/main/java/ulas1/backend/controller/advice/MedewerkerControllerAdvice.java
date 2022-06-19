package ulas1.backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.MedewerkerNotFoundException;

@ControllerAdvice
public class MedewerkerControllerAdvice {
    @ExceptionHandler(MedewerkerNotFoundException.class)
    public ResponseEntity<Object> handleMedewerkerNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

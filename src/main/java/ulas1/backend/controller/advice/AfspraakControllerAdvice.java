package ulas1.backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.KlantHeeftAlAfspraakException;
import ulas1.backend.exception.MedewerkerHeeftAlAfspraakException;

@ControllerAdvice
public class AfspraakControllerAdvice {
    @ExceptionHandler(KlantHeeftAlAfspraakException.class)
    public ResponseEntity<Object> handleKlantHeeftAlAfspraakException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MedewerkerHeeftAlAfspraakException.class)
    public ResponseEntity<Object> handleMedewerkerHeeftAlAfspraakException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

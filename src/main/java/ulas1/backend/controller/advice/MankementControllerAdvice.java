package ulas1.backend.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulas1.backend.exception.MankementNotFoundException;
import ulas1.backend.exception.AutoHasNoMankementenException;

@ControllerAdvice
public class MankementControllerAdvice {
        @ExceptionHandler(MankementNotFoundException.class)
        public ResponseEntity<Object> handleMankementNotFoundException(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        @ExceptionHandler(AutoHasNoMankementenException.class)
        public ResponseEntity<Object> handleAutoHasNoMankementException(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

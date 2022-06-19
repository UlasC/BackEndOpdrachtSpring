package ulas1.backend.controller.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(){
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Geef de input data alsjeblieft in JSON-formaat");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("De sessie is verlopen. Log alsjeblieft opnieuw in.");
    }
}

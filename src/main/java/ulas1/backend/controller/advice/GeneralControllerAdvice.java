package ulas1.backend.controller.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.hibernate.LazyInitializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(){
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Geef de input data alsjeblieft in JSON-formaat");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geef alsjeblieft een correct JWT-token met de request.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("De sessie is verlopen. Log alsjeblieft opnieuw in.");
    }

    //Komt soms voor bij ketting-DELETE-requests, waarbij je een entiteit uit één tabel wil weghalen en
    // als gevolg daarvan entiteiten uit andere tabellen ook moet weghalen, bijvoorbeeld wanneer je
    // een auto verwijdert en als gevolg alle bijbehorende mankementen ook verwijderd moeten worden
    //Als je na deze exception even wacht dan doet hij het wel
    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<Object> handleLazyInitializationException(Exception ex){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Wacht even met requests sturen, de server heeft iets meer tijd nodig.");
    }
}

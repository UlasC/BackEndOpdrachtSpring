package ulas1.backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import ulas1.backend.exception.KlantHeeftGeenFotosException;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class IdentiteitskaartFotoControllerAdvice {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kon het bestand niet vinden.");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(Exception ex){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Het uploaden van de foto is niet gelukt.");
    }

    @ExceptionHandler(KlantHeeftGeenFotosException.class)
    public ResponseEntity<Object> handleKlantHeeftGeenFotosException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(Exception ex){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Foto is te groot!");
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Object> handleMissingServletRequestPartException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Je hebt niet genoeg parameters meegegeven om deze opdracht uit te kunnen voeren.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Je hebt niet genoeg paramters meegegeven om deze opdracht uit te kunnen voeren.");
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMultipartException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Geef alsjeblieft een foto mee als parameter.");
    }
}

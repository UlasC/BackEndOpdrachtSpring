package ulas1.backend.exception;

public class MankementNotFoundException extends RuntimeException{

    public MankementNotFoundException(Integer mankementId){
        super("Geen mankement gevonden met dit mankementId '" + mankementId + "'");
    }
}

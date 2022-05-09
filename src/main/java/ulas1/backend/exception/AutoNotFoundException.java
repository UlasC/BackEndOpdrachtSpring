package ulas1.backend.exception;

public class AutoNotFoundException extends RuntimeException {
    public AutoNotFoundException(String kenteken){
        super("Geen auto gevonden met dit kenteken '" + kenteken + "'");
    }
}

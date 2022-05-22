package ulas1.backend.exception;

public class HandelingNotFoundException extends RuntimeException {
    public HandelingNotFoundException(int handelingsnummer) {
        super("Geen handeling gevonden met dit handelingsnummer '" +  handelingsnummer + "'");
    }
}
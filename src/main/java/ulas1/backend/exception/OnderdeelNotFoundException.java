package ulas1.backend.exception;

public class OnderdeelNotFoundException extends RuntimeException{
    public OnderdeelNotFoundException(int artikelnummer) {
        super("Geen onderdeel gevonden met dit artikelnummer '" + artikelnummer + "'");
    }
}

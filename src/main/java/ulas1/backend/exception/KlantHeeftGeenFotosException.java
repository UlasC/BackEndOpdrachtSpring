package ulas1.backend.exception;

public class KlantHeeftGeenFotosException extends RuntimeException{
    public KlantHeeftGeenFotosException(String firstName, String lastName) {
        super("Klant " + firstName + " " + lastName + " heeft geen foto's in het systeem staan.");
    }
}

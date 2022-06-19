package ulas1.backend.exception;

public class KlantHasNoCarException extends RuntimeException {

    public KlantHasNoCarException(String firstname, String lastname) {
        super("Klant " + firstname + " " + lastname + " heeft geen auto in het systeem staan.");
    }
}

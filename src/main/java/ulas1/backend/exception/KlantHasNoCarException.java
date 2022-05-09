package ulas1.backend.exception;

public class KlantHasNoCarException extends RuntimeException {

    public KlantHasNoCarException(int bsn) {
        super("Klant met bsn '" + bsn + "' heeft geen auto in het systeem staan.");
    }
}

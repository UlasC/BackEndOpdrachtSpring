package ulas1.backend.exception;

public class KlantNotFoundException extends RuntimeException{
    public KlantNotFoundException(int bsn){
        super("Geen klant gevonden met bsn '" + bsn + "'");
    }
}

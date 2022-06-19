package ulas1.backend.exception;

public class KlantNotFoundException extends RuntimeException{
    public KlantNotFoundException(){
        super("Geen klant gevonden met dit bsn-nummer.");
    }
}

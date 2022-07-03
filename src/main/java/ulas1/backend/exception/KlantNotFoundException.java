package ulas1.backend.exception;

public class KlantNotFoundException extends RuntimeException{

    //Vanwege privacy-redenen wordt het bsn-nummer waarop werd gezocht
    // niet genoemd in de exception-message.
    public KlantNotFoundException(){
        super("Geen klant gevonden met dit bsn-nummer.");
    }
}

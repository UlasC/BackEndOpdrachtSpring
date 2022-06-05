package ulas1.backend.exception;

public class KlantHeeftAlAfspraakException extends RuntimeException{
    public KlantHeeftAlAfspraakException(String firstname, String lastname){
        super("Klant " + firstname + " " + lastname + " heeft op dat moment al een andere afspraak staan.");
    }
}

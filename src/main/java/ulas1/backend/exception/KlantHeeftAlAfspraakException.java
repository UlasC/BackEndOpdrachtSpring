package ulas1.backend.exception;

public class KlantHeeftAlAfspraakException extends RuntimeException{

    //Normaal gebruik ik de primaire sleutel in exception-messages.
    //Vanwege privacy-redenen heb ik dat hier niet gedaan, en
    // in plaats van het bsn-nummer de voor- en achternaam weergegeven.
    public KlantHeeftAlAfspraakException(String firstname, String lastname){
        super("Klant " + firstname + " " + lastname + " heeft op dat moment al een andere afspraak staan.");
    }
}

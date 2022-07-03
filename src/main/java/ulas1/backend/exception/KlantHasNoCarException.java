package ulas1.backend.exception;

public class KlantHasNoCarException extends RuntimeException {

    //Normaal gebruik ik de primaire sleutel in exception-messages.
    //Vanwege privacy-redenen heb ik dat hier niet gedaan, en
    // in plaats van het bsn-nummer de voor- en achternaam weergegeven.
    public KlantHasNoCarException(String firstname, String lastname) {
        super("Klant " + firstname + " " + lastname + " heeft geen auto in het systeem staan.");
    }
}

package ulas1.backend.exception;

public class KlantHeeftGeenFotosException extends RuntimeException{

    //Normaal gebruik ik de primaire sleutel in exception-messages.
    //Vanwege privacy-redenen heb ik dat hier niet gedaan, en
    // in plaats van het bsn-nummer de voor- en achternaam weergegeven.
    public KlantHeeftGeenFotosException(String firstName, String lastName) {
        super("Klant " + firstName + " " + lastName + " heeft geen foto's in het systeem staan.");
    }
}


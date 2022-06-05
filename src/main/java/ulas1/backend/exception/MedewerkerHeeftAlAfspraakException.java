package ulas1.backend.exception;

public class MedewerkerHeeftAlAfspraakException extends RuntimeException{
    public MedewerkerHeeftAlAfspraakException(String gebruikersnaam){
        super("Medewerker '" + gebruikersnaam + "' heeft op dat moment al een andere afspraak staan.");
    }
}

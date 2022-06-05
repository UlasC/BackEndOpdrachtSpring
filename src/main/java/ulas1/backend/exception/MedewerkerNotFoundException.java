package ulas1.backend.exception;

public class MedewerkerNotFoundException extends RuntimeException {
    public MedewerkerNotFoundException(String gebruikersnaam){
        super("Geen medewerker gevonden met deze gebruikersnaam '" + gebruikersnaam + "'");
    }
}

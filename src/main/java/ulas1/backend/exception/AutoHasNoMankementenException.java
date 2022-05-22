package ulas1.backend.exception;

public class AutoHasNoMankementenException extends RuntimeException {
    public AutoHasNoMankementenException(String kenteken) {
        super("Auto met dit kenteken '" + kenteken + "' heeft geen mankementen in het systeem staan.");
    }
}

package org.biblioteca_gruppo_05.Eccezioni;

public class UtenteEsitenteException extends RuntimeException {
    public UtenteEsitenteException(String message) {
        super(message);
    }
}

package org.biblioteca_gruppo_05.Eccezioni;

public class LibroEsistenteException extends RuntimeException {
    public LibroEsistenteException(String message) {
        super(message);
    }
}

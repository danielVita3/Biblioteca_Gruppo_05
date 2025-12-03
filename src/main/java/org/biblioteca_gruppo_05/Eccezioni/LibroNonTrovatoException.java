package org.biblioteca_gruppo_05.Eccezioni;

public class LibroNonTrovatoException extends RuntimeException {
    public LibroNonTrovatoException(String message){
        super(message);
    }
    public LibroNonTrovatoException(){
    }
}

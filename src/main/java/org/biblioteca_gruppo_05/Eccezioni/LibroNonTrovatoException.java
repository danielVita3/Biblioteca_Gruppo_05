package org.biblioteca_gruppo_05.Eccezioni;

public class LibroNonTrovatoException extends Exception {
    public LibroNonTrovatoException(String message){
        super(message);
    }
    public LibroNonTrovatoException(){
    }
}

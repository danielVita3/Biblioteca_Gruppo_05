package org.biblioteca_gruppo_05.Eccezioni;

public class UtenteNonTrovatoException extends Exception {
    public UtenteNonTrovatoException(String message){
        super(message);
    }
    public UtenteNonTrovatoException(){
    }
}

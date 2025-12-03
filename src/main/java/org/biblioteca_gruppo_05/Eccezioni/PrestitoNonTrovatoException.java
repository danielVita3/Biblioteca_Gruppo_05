package org.biblioteca_gruppo_05.Eccezioni;

public class PrestitoNonTrovatoException extends RuntimeException{
    public PrestitoNonTrovatoException(String message){
        super(message);
    }
    public PrestitoNonTrovatoException(){
    }
}

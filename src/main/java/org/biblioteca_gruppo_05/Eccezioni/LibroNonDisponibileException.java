package org.biblioteca_gruppo_05.Eccezioni;

public class LibroNonDisponibileException extends RuntimeException{
    public LibroNonDisponibileException(String message){
        super(message);
    }
    public LibroNonDisponibileException(){
    }
}

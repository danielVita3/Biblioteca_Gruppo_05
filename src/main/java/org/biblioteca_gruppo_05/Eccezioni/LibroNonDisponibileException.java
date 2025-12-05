package org.biblioteca_gruppo_05.Eccezioni;

public class LibroNonDisponibileException extends Exception{
    public LibroNonDisponibileException(String message){
        super(message);
    }
    public LibroNonDisponibileException(){
    }
}

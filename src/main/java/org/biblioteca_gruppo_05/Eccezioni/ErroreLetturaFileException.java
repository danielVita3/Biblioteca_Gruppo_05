package org.biblioteca_gruppo_05.Eccezioni;

public class ErroreLetturaFileException extends RuntimeException {
    public ErroreLetturaFileException(String message){
        super(message);
    }
    public ErroreLetturaFileException(){
    }
}

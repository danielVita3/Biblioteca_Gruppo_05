package org.biblioteca_gruppo_05.Eccezioni;

public class ErroreLetturaFileException extends Exception {
    public ErroreLetturaFileException(String message){
        super(message);
    }
    public ErroreLetturaFileException(){
    }
}

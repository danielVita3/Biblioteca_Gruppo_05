package org.biblioteca_gruppo_05.Eccezioni;

public class ErroreScritturaFileException extends RuntimeException{
    public ErroreScritturaFileException(String message){
        super(message);
    }
    public ErroreScritturaFileException(){
    }
}

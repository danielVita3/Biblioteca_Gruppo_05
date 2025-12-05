package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Eccezioni.ErroreLetturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.ErroreScritturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.UtenteEsitenteException;
import org.biblioteca_gruppo_05.Eccezioni.UtenteNonTrovatoException;
import org.biblioteca_gruppo_05.Gestione_Libri.Libro;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ArchivioProfili implements Serializable {
    private Map<String, Profilo> profilo;
    private final String fileName;
    public ArchivioProfili(String fileName){
        this.profilo=new LinkedHashMap<>();
        this.fileName=fileName;
    }
    public void aggiungiProfilo(Profilo p)throws UtenteEsitenteException {}
    public void rimuoviProfilo(Profilo p)throws UtenteNonTrovatoException {}
    public Libro ricercaProfiloPerMatricola(String matricola)throws UtenteNonTrovatoException{}
    public List<Libro> ricercaProfiloPerNome(String nome)throws UtenteNonTrovatoException{}
    public List<Libro> ricercaProfiloPerCognome(String cognome)throws UtenteNonTrovatoException{}
    public void visualizzaProfili(){};
    public void salvaSuFile() throws ErroreScritturaFileException {}
    public void leggiDaFile() throws ErroreLetturaFileException {}
    @Override
    public String toString(){}
}

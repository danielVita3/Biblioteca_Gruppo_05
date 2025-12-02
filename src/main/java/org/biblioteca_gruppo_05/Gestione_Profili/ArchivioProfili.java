package org.biblioteca_gruppo_05.Gestione_Profili;

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
    public void aggiungiProfilo(Profilo p){};
    public void rimuoviProfilo(Profilo p){};
    public void modificaProfilo(String matricola){};
    public Libro ricercaProfiloPerMatricola(String matrciola){};
    public List<Libro> ricercaProfiloPerNome(String nome){};
    public List<Libro> ricercaProfiloPerCognome(String cognome){};
    public void visualizzaProfili(){};
    public void salvaSuFile() throws IOException {};
    public void leggiDaFile() throws IOException{};
    @Override
    public String toString(){};
}

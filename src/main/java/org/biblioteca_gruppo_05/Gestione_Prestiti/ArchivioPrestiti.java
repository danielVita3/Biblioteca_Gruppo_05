package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


public class ArchivioPrestiti implements Serializable {
    private List <Prestito> prestiti;
    private final String fileName;
    public ArchivioPrestiti(String fileName){
        this.prestiti=new ArrayList<>();
        this.fileName=fileName;
    }
    public void registraPrestito(Prestito p){};
    public void restituzionPrestito(Prestito p{};
    public Prestito ricercaPrestitoPerISBN(Libro l){};
    public List<Prestito> ricercaPrestitoPerMatricola(Profilo p){};
    public List<Prestito> ricercaProfiloPerCognome(String cognome){};
    public void visualizzaPrestiti(){};
    public void salvaSuFile() throws IOException {};
    public void leggiDaFile() throws IOException{};
    @Override
    public String toString(){};
}

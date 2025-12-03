package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


public class ArchivioPrestiti implements Serializable {
    private Map <Integer,Prestito> prestiti;
    private final String fileName;
    public ArchivioPrestiti(String fileName){
        this.prestiti=new LinkedHashMap<>();
        this.fileName=fileName;
    }
    public void registraPrestito(Prestito p){}
    public void restituzionPrestito(Prestito p){}
    public List<Prestito> ricercaPrestitoPerMatricola(String matricola){}
    public List<Prestito> ricercaPrestitoPerISBN(int ISBN){}
    public void visualizzaPrestiti(){}
    private void salvaSuFile()  {}
    private void leggiDaFile() {}
    @Override
    public String toString(){}
}

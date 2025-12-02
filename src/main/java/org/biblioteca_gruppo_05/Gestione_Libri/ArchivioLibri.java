package org.biblioteca_gruppo_05.Gestione_Libri;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ArchivioLibri implements Serializable {
    private Map<String, Libro> libri;
    private final String fileName;
    public ArchivioLibri(String fileName){
        this.libri=new LinkedHashMap<>();
        this.fileName=fileName;
    }
    public void aggiungiLibro(Libro l){};
    public void rimuoviLibro(Libro l){};
    public void modificaLibro(String ISBN){};
    public Libro ricercaLibroPerISBN(String ISBN){};
    public List<Libro> ricercaLibriPerTitolo(String titolo){};
    public List<Libro> ricercaLibriPerAutore(String autore){};
    public void visualizzaLibri(){};
    private void salvaSuFile() {};
    private void leggiDaFile() {};
    @Override
    public String toString(){};
}

package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.*;

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
    public void aggiungiLibro(Libro l)throws LibroEsistenteException {};
    public void rimuoviLibro(Libro l)throws LibroNonDisponibileException {};
    public Libro ricercaLibroPerISBN(int ISBN)throws LibroNonTrovatoException {};
    public List<Libro> ricercaLibriPerTitolo(String titolo)throws LibroNonTrovatoException{};
    public List<Libro> ricercaLibriPerAutore(String autore)throws LibroNonTrovatoException{};
    public void visualizzaLibri(){};
    private void salvaSuFile()throws ErroreScritturaFileException {};
    private void leggiDaFile()throws ErroreLetturaFileException {};
    @Override
    public String toString(){};
}

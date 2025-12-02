package org.biblioteca_gruppo_05.Gestione_Libri;

import java.io.Serializable;
import java.time.LocalDate;
import java.lang.Comparable;

public class Libro implements Serializable,Comparable <Libro> {
    private String titolo;
    private String autore;
    private String ISBN;
    private int numeroCopie;
    private LocalDate dataPubblicazione;
    public Libro(String titolo,String autore,String ISBN,int numeroCopie,LocalDate dataPubblicazione){
        this.titolo=titolo;
        this.autore=autore;
        this.ISBN=ISBN;
        this.numeroCopie=numeroCopie;
        this.dataPubblicazione=dataPubblicazione;
    }
    public String getTitolo(){};
    public String getAutore(){};
    public String getISBN(){};
    public int getNumeroCopie(){};
    public LocalDate getDataPubblicazione(){};
    public void setTitolo(String titolo){};
    public void setAutore(String autore){};
    public void setISBN(String ISBN){};
    public void setNumeroCopie(int numeroCopie){};
    public void setDataPubblicazione(String dataPubblicazione){};

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Libro l1, Libro l2){};

    @Override
    public int hashCode(){};

    @Override
    public String toString(){};
}

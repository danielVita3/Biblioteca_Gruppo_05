package org.biblioteca_gruppo_05.Gestione_Libri;

import java.io.Serializable;
import java.time.LocalDate;
import java.lang.Comparable;

public class Libro implements Serializable,Comparable <Libro> {

    private String titolo;
    private String autore;
    private int ISBN;
    private int numeroCopie;
    private LocalDate dataPubblicazione;
    public Libro(String titolo,String autore,int ISBN,LocalDate dataPubblicazione){
        this.titolo=titolo;
        this.autore=autore;
        this.ISBN=ISBN;
        this.numeroCopie=1;
        this.dataPubblicazione=dataPubblicazione;
    }
    public boolean controllaISBN(){}
    public String getTitolo(){}
    public String getAutore(){}
    public String getISBN(){}
    public int getNumeroCopie(){}
    public LocalDate getDataPubblicazione(){}
    public void setTitolo(String titolo){}
    public void setAutore(String autore){}
    public void setISBN(int ISBN){}
    public void setNumeroCopie(int numeroCopie){}
    public void setDataPubblicazione(String dataPubblicazione){}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Libro l1){
        return 0;
    }

    @Override
    public int hashCode(){
        return 0;
    }

    @Override
    public String toString(){}
}

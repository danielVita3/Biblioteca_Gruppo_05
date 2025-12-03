package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.Serializable;
import java.time.LocalDate;
import java.lang.Comparable;

public class Prestito implements Serializable, Comparable<Prestito> {
    private static int cont=0;
    private int id;
    private LocalDate dataPrestito;
    private LocalDate dataScadenza;
    private String profilo;
    private int libro;
    private int costoPenale;


    public Prestito(LocalDate dataPrestito,LocalDate dataScadenza,String matricola, int ISBN){
        this.dataPrestito=dataPrestito;
        this.dataScadenza=dataScadenza;
        this.profilo=matricola;
        this.libro=ISBN;
        this.id=cont++;
        this.costoPenale= calcolaPenale();
    }
    
    public LocalDate getDataPrestito(){}
    public LocalDate getDataScadenza(){}
    public String getProfilo(){}
    public int getLibro(){}
    public boolean controlloMatricola(){};
    public boolean controlloISBN(){};
    public int getId(){}
    public int calcolaPenale(){}


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Prestito p1){
        return 0;
    }

    @Override
    public int hashCode(){
        return 0;
    };
    @Override
    public String toString(){}



}

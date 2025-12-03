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
    private Profilo profilo;
    private Libro libro;
    private int costoPenale;


    public Prestito(LocalDate dataPrestito,LocalDate dataScadenza,Profilo profilo, Libro libro){
        this.dataPrestito=dataPrestito;
        this.dataScadenza=dataScadenza;
        this.profilo=profilo;
        this.libro=libro;
        this.id=cont++;
        this.costoPenale= calcolaPenale();
    }
    
    public LocalDate getDataPrestito(){}
    public LocalDate getDataScadenza(){}
    public Profilo getProfilo(){}
    public Libro getLibro(){}
    public int getId( ){}
    public boolean controlloLibro(){}
    public boolean controlloProfilo(){}
    public int calcolaPenale(){}
    public void setLibro(Libro libro){}

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

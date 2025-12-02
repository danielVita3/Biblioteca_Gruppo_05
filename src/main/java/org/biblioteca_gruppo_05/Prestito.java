package org.biblioteca_gruppo_05;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestito implements Serializable {
    private LocalDate dataPrestito;
    private LocalDate dataScadenza;
    private Profilo profilo;
    private Libro libro;

    public  Profilo(LocalDate dataPrestito,LocalDate dataScadenza,Profilo profilo, Libro libro){
        this.dataPrestito=dataPrestito;
        this.dataScadenza=dataScadenza;
        this.profilo=profilo;
        this.libro=libro;
    }
    public LocalDate getDataPrestito(){};
    public LocalDate getDataScadenza(){};
    public Profilo getProfilo(){};
    public Libro getLibro(){};
    public void setDataPrestito(LocalDate dataPrestito){};
    public void setDataScadenza(LocalDate dataScadenza){};
    public void setrofilo(Profilo profilo){};
    public void setLibro(Libro libro){};

    @Override
    public String toString(){};
}

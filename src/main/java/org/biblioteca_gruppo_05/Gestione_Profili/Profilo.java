package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Prestiti.Prestito;

import java.io.Serializable;
import java.lang.Comparable;
public class Profilo implements Serializable, Comparable<Profilo> {
    private String nome;
    private String cognome;
    private String matricola;
    private String mail;
    private int numeroPrestiti;
    public  Profilo(String nome,String cognome,String matricola,String mail){
        this.nome=nome;
        this.cognome=cognome;
        this.matricola=matricola;
        this.mail=mail;
        this.numeroPrestiti=0;
    }
    public boolean controlloMatricola(){}
    public String getNome(){}
    public String getCognome(){}
    public String getMatricola(){}
    public String getMail(){}
    public int getNumeroPrestiti(){}
    public void setNome(String nome){}
    public void setCognome(String cognome){}
    public void setMatricola(String matricola){}
    public void setMail(String mail){}
    public void setNumeroPrestiti(int prestito){}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Profilo p1){
        return 0;
    }

    @Override
    public int hashCode(){
        return 0;
    }

    @Override
    public String toString(){}
}

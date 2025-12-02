package org.biblioteca_gruppo_05.Gestione_Profili;

import java.io.Serializable;

public class Profilo implements Serializable {
    private String nome;
    private String cognome;
    private String matricola;
    private String mail;
    private int numeroPrestiti;
    public  Profilo(String nome,String cognome,String matricola,String mail,int numeroPrestiti){
        this.nome=nome;
        this.cognome=cognome;
        this.matricola=matricola;
        this.mail=mail;
        this.numeroPrestiti=numeroPrestiti;
    }
    public String getNome(){};
    public String getCognome(){};
    public String getMatricola(){};
    public String getMail(){};
    public int getNumeroPrestiti(){}
    public void setNome(String nome){};
    public void setCognome(String cognome){};
    public void setMatricola(String matricola){};
    public void setMail(String mail){};
    public void setNumeroPrestiti(int numeroPrestiti){};
    @Override
    public String toString(){};
}

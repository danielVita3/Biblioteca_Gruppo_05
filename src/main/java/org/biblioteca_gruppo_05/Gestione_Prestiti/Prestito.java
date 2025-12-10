package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.Serializable;
import java.time.LocalDate;
import java.lang.Comparable;
import java.time.temporal.ChronoUnit;

/**
 * @class Prestito
 * @brief Rappresenta un prestito del sistema bibliotecario.
 *
 * Questa classe modella l'entità Prestito. Contiene i dati relativi all'utente che procede al
 * prestito e al libro preso in prestito.
 * Implementa l'interfaccia Comparable per permettere l'ordinamento (per id)
 * e Serializable per il salvataggio su file.
 */
public class Prestito implements Serializable, Comparable<Prestito> {
    /** Contatore statico per generare ID univoci incrementali. */
    private static int cont = 0;

    /** Identificativo univoco del prestito. */
    private int id;

    /** Data di inizio del prestito. */
    private LocalDate dataPrestito;

    /** Data di scadenza prevista per la restituzione. */
    private LocalDate dataScadenza;

    /** Matricola dell'utente che ha richiesto il prestito. */
    private String profilo;

    /** Codice ISBN del libro prestato. */
    private String libro;

    /** Importo della penale (calcolato in base al ritardo). */
    private int costoPenale;

    /**
     * @brief Costruttore della classe Prestito.
     *
     * @param dataPrestito La data in cui inizia il prestito.
     * @param dataScadenza La data entro cui il libro deve essere restituito.
     * @param matricola La matricola dell'utente che prende il libro.
     * @param ISBN Il codice ISBN del libro prestato.
     *
     * @pre dataPrestito e dataScadenza non devono essere null;dataPrestito deve essere valida e dataScadenza deve essere successiva a dataPrestito.
     * @pre matricola non deve essere null o vuota, ISBN non deve essere null o vuoto.
     * @post Viene istanziato un nuovo oggetto Prestito con ID univoco e costo penale calcolato in base alle date.
     */
    public Prestito(LocalDate dataPrestito,LocalDate dataScadenza,String matricola, String ISBN){
        if(Libro.controllaISBN(ISBN)){
            if(Profilo.controlloMatricola(matricola)) {
                this.dataPrestito = dataPrestito;
                this.dataScadenza = dataScadenza;
                this.profilo = matricola;
                this.libro = ISBN;
                this.id = cont++;
                this.costoPenale = calcolaPenale();
            }
        }

    }

    /**
     * @brief Restituisce la data di inizio del prestito.
     * @return Oggetto LocalDate rappresentante l'inizio del prestito.
     * @post Restituisce l'attributo richiesto.
     */
    public LocalDate getDataPrestito(){
        return dataPrestito;
    }

    /**
     * @brief Restituisce la data di scadenza del prestito.
     * @return Oggetto LocalDate rappresentante la scadenza.
     * @post Restituisce l'attributo richiesto.
     */
    public LocalDate getDataScadenza(){
        return dataScadenza;
    }

    /**
     * @brief Restituisce la matricola dell'utente associato al prestito.
     * @return Stringa contenente la matricola.
     * @post Restituisce l'attributo richiesto.
     */
    public String getProfilo(){
        return profilo;
    }

    /**
     * @brief Restituisce l'ISBN del libro associato al prestito.
     * @return Intero rappresentante l'ISBN.
     * @post Restituisce l'attributo richiesto.
     */
    public String getLibro(){
        return libro;
    }



    /**
     * @brief Restituisce l'ID univoco del prestito.
     * @return Intero rappresentante l'ID.
     * @post Restituisce l'attributo richiesto.
     */
    public int getId(){
        return id;
    }

    /**
     * @brief Calcola l'eventuale penale per il prestito.
     *
     * @return Valore intero della penale (calcolato sulla tempistica del prestito).
     *
     * @pre Le date di prestito e scadenza devono essere valide.
     * @post Restituisce un valore >= 0 calcolato sulla differenza date.
     */
    public int calcolaPenale(){
        LocalDate dataConsegna=LocalDate.now();
        if(dataConsegna.isBefore(this.dataScadenza) || dataConsegna.isEqual(this.dataScadenza))
            return 0;
        long mesiDiRitardo= ChronoUnit.MONTHS.between(this.dataScadenza,dataConsegna);
        int penale= (int) (mesiDiRitardo*10);
        return penale;
    }



    /**
     * @brief Confronta questo profilo con un altro oggetto per verificarne l'uguaglianza.
     * @param obj L'oggetto con cui confrontare il prestito corrente.
     * @return true se gli oggetti sono uguali, false altrimenti.
     *
     * @pre obj può essere null o un oggetto di qualsiasi tipo.
     * @post Restituisce true solo se obj è un'istanza di Prestito con lo stesso id.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj==this) return true;
        if(this.getClass()!=obj.getClass()) return false;
        Prestito p=(Prestito) obj;
        return this.id==p.getId();
    }

    /**
     * @brief Confronta due profili per l'ordinamento.
     * @param p1 Il prestito da confrontare con l'istanza corrente.
     * @return Un intero negativo, zero o positivo se questo oggetto è minore, uguale o maggiore di p1.
     *
     * @pre p1 non deve essere null.
     * @post Viene restituito il valore dettato dall'ordine.
     */
    @Override
    public int compareTo(Prestito p1){
        return Integer.compare(this.id,p1.getId());
    }

    /**
     * @brief Calcola il codice hash del prestito.
     * @return L'hash code calcolato (basato sulla matricola).
     *
     * @pre L'oggetto deve essere inizializzato correttamente.
     * @post Restituisce un intero consistente con il metodo equals.
     */
    @Override
    public int hashCode(){
        return Integer.hashCode(this.id);
    }

    /**
     * @brief Restituisce una rappresentazione testuale del prestito.
     * @return Una stringa contenente i dettagli principali del prestito.
     *
     * @post Restituisce una stringa non nulla che descrive lo stato dell'oggetto.
     */
    @Override
    public String toString(){
            StringBuffer sb=new StringBuffer();
            sb.append("\nID=");
            sb.append(id);
            sb.append("\nProfilo=");
            sb.append(profilo);
            sb.append("\nData prestito=");
            sb.append(dataPrestito);
            sb.append("\nData di scadenza=");
            sb.append(dataScadenza);
            return sb.toString();

    }



}

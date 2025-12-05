package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.Serializable;
import java.time.LocalDate;
import java.lang.Comparable;
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
    private int libro;

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
    public Prestito(LocalDate dataPrestito,LocalDate dataScadenza,String matricola, int ISBN){
        this.dataPrestito=dataPrestito;
        this.dataScadenza=dataScadenza;
        this.profilo=matricola;
        this.libro=ISBN;
        this.id=cont++;
        this.costoPenale= calcolaPenale();
    }

    /**
     * @brief Restituisce la data di inizio del prestito.
     * @return Oggetto LocalDate rappresentante l'inizio del prestito.
     * @post Restituisce l'attributo richiesto.
     */
    public LocalDate getDataPrestito(){ return dataPrestito; }

    /**
     * @brief Restituisce la data di scadenza del prestito.
     * @return Oggetto LocalDate rappresentante la scadenza.
     * @post Restituisce l'attributo richiesto.
     */
    public LocalDate getDataScadenza(){ return dataScadenza; }

    /**
     * @brief Restituisce la matricola dell'utente associato al prestito.
     * @return Stringa contenente la matricola.
     * @post Restituisce l'attributo richiesto.
     */
    public String getProfilo(){ return profilo; }

    /**
     * @brief Restituisce l'ISBN del libro associato al prestito.
     * @return Intero rappresentante l'ISBN.
     * @post Restituisce l'attributo richiesto.
     */
    public int getLibro(){ return libro; }

    /**
     * @brief Verifica la validità formale della matricola associata.
     * @return true se la matricola rispetta il formato, false altrimenti.
     *
     * @pre La matricola deve essere stata inizializzata.
     * @post Restituisce l'esito della validazione senza modificare lo stato.
     */
    public boolean controlloMatricola(){
        return true;
    }

    /**
     * @brief Verifica la validità formale dell'ISBN associato.
     * @return true se l'ISBN rispetta il formato, false altrimenti.
     *
     * @pre L'ISBN deve essere stato inizializzato.
     * @post Restituisce l'esito della validazione senza modificare lo stato.
     */
    public boolean controlloISBN(){
        return true;
    }

    /**
     * @brief Restituisce l'ID univoco del prestito.
     * @return Intero rappresentante l'ID.
     * @post Restituisce l'attributo richiesto.
     */
    public int getId(){ return id; }

    /**
     * @brief Calcola l'eventuale penale per il prestito.
     *
     * @return Valore intero della penale (calcolato sulla tempistica del prestito).
     *
     * @pre Le date di prestito e scadenza devono essere valide.
     * @post Restituisce un valore >= 0 calcolato sulla differenza date.
     */
    public int calcolaPenale(){
        return 0;
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
        return super.equals(obj);
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
        return 0;
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
        return 0;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del prestito.
     * @return Una stringa contenente i dettagli principali del prestito.
     *
     * @post Restituisce una stringa non nulla che descrive lo stato dell'oggetto.
     */
    @Override
    public String toString(){
        return "";
    }



}

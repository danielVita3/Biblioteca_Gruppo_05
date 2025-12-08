package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.ErroreISBNException;

import java.io.Serializable;
import java.time.LocalDate;
import java.lang.Comparable;

/**
 * @class Libro
 * @brief Rappresenta un libro della biblioteca
 *
 * Questa classe contiene tutti i dati bibliografici essenziali
 * (Titolo, Autore, ISBN, numero di copie, data di pubblicazione).
 * Implementa l'interfaccia Serializable per permettere la persistenza su file
 * e Comparable per definire un ordinamento naturale basato sull'ISBN.
 * Contiene i setter e i getter dei vari attributi
 * @author Lenovo
 * @see ArchivioLibri
 */
public class Libro implements Serializable,Comparable <Libro> {
    /** Titolo del libro */
    private String titolo;
    /** Autore del libro */
    private String autore;
    /** Codice univoco identificativo (ISBN) */
    private String ISBN;
    /** Numero di copie del libro disponibili nella biblioteca */
    private int numeroCopie;
    /** Anno di pubblicazione del libro */
    private LocalDate dataPubblicazione;

    /**
     * @brief Costruttore della classe Libro
     * @param titolo
     * @param autore
     * @param ISBN
     * @param dataPubblicazione
     * @pre Il parametro ISBN fornito non deve essere nullo né una stringa vuota.
     * @pre I parametri titolo,autore non devono essere nulli, la data di pubblicazione deve essere accettabile
     * @post Viene creata una nuova istanza valida della classe Libro.
     * @post Tutti gli attributi sono stati inizializzati con i valori passati.
     */
    public Libro(String titolo,String autore,String ISBN,LocalDate dataPubblicazione){
        this.titolo=titolo;
        this.autore=autore;
        this.ISBN=ISBN;
        this.numeroCopie=1;
        this.dataPubblicazione=dataPubblicazione;
    }
    /**
     * @brief Verifica la validità del formato dell'ISBN.
     *
     * @return true se l'ISBN è valida, false altrimenti.
     *
     * @pre L'ISBN deve essere stato inizializzato (non null).
     * @post Restituisce l'esito della validazione senza modificare lo stato dell'oggetto.
     */
    public static boolean controllaISBN(String ISBN) throws ErroreISBNException {
        final String ISBN_PATTERN = "^97[89]\\d{10}$";

        if (ISBN == null) {
            throw new ErroreISBNException("L'ISBN non può essere nullo.");
        }

        if (ISBN.matches(ISBN_PATTERN)) {
            return true;
        } else {
            String errorMessage;

            if (ISBN.length() != 13) {
                errorMessage = "Lunghezza non valida. L'ISBN deve essere composto da 13 cifre (trovati " + ISBN.length() + ").";
            } else if (!ISBN.substring(0, 2).equals("97")) {
                errorMessage = "Il prefisso non è valido. L'ISBN deve iniziare con '97'.";
            } else {
                errorMessage = "L'ISBN contiene caratteri non numerici o la terza cifra non è 8 o 9.";
            }

            throw new ErroreISBNException(errorMessage);
        }
    }
    /**
     * @brief Restituisce il titolo del libro.
     * @return Stringa rappresentante il titolo.
     * @post Restituisce l'attributo richiesto
     */

    public String getTitolo(){
        return this.titolo;
    }
    /**
     * @brief Restituisce l'autore del libro.
     * @return Stringa rappresentante l'autore.
     * @post Restituisce l'attributo richiesto
     */
    public String getAutore(){
        return this.autore;
    }
    /**
     * @brief Restituisce l'ISBN del libro.
     * @return Stringa univoca dell'ISBN'.
     * @post Restituisce l'attributo richiesto.
     */
    public String getISBN(){
        return this.ISBN;
    }
    /**
     * @brief Restituisce il numero di copie associate al libro.
     * @return Intero rappresentante il conteggio delle copie.
     * @post Restituisce l'attributo richiesto.
     */
    public int getNumeroCopie(){
        return this.numeroCopie;
    }
    /**
     * @brief Restituisce la data in cui il libro è stato pubblicato.
     * @return LocalDate nel formato anno-mese-giorno.
     * @post Restituisce l'attributo richiesto.
     */
    public LocalDate getDataPubblicazione(){
        return this.dataPubblicazione;
    }
    /**
     * @brief Imposta o modifica il titolo del libro.
     * @param titolo Il nuovo titolo da asseganre.
     *
     * @pre Il parametro titolo non deve essere null o vuoto.
     * @post L'attributo titolo viene aggiornato con il nuovo valore.
     */
    public void setTitolo(String titolo){
        this.titolo=titolo;
    }
    /**
     * @brief Imposta o modifica l'autore del libro.
     * @param autore Il nuovo autore da asseganre.
     *
     * @pre Il parametro autore non deve essere null o vuoto.
     * @post L'attributo autore viene aggiornato con il nuovo valore.
     */
    public void setAutore(String autore){
        this.autore=autore;
    }
    /**
     * @brief Imposta o modifica l'ISBN del libro.
     * @param ISBN Il nuovo ISBN da assegnare.
     *
     * @pre Il parametro ISBN non deve essere null e deve rispettare il formato univoco.
     * @post L'attributo ISBN viene aggiornato.
     */
    public void setISBN(String ISBN){
        this.ISBN=ISBN;
    }
    /**
     * @brief Aggiorna il numero delle copie del libro.
     * @param numeroCopie Il nuovo valore del contatore copie.
     *
     * @pre Il parametro numeroCopie deve essere un intero +1, -1.
     * @post L'attributo numeroCopie viene aggiornato con il valore fornito.
     */
    public void setNumeroCopie(int numeroCopie){
        this.numeroCopie+=numeroCopie;
    }
    /**
     * @brief Aggiorna la data di pubblicazione del libro.
     * @param dataPubblicazione La data di pubblicazione .
     *
     * @pre La stringa dataPubblicazione non deve essere nulla e deve essere convertibile in un formato data valido.
     * @post L'attributo interno dataPubblicazione viene aggiornato con il valore fornito.
     */
    public void setDataPubblicazione(LocalDate dataPubblicazione){
        this.dataPubblicazione=dataPubblicazione;
    }
    /**
     * @brief Confronta questo libro con un altro oggetto per verificarne l'uguaglianza.
     * @param obj L'oggetto con cui confrontare il libro corrente.
     * @return true se gli oggetti sono uguali, false altrimenti.
     *
     * @pre obj può essere null o un oggetto di qualsiasi tipo.
     * @post Restituisce true solo se obj è un'istanza di Libro con lo stesso ISBN.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj==this) return true;
        if(this.getClass()!=obj.getClass()) return false;
        Libro l=(Libro) obj;
        return this.ISBN.equals(l.getISBN());
    }
    /**
     * @brief Confronta due libri per l'ordinamento.
     * @param l1 Il libro da confrontare con l'istanza corrente.
     * @return Un intero negativo, zero o positivo se questo oggetto è minore, uguale o maggiore di l1 (in base ad ISBN).
     *
     * @pre l1 non deve essere null.
     * @post Viene restituito il valore dettato dall'ordine.
     */
    @Override
    public int compareTo(Libro l1){
        return this.ISBN.compareTo(l1.getISBN());
    }
    /**
     * @brief Calcola il codice hash del libro.
     * @return L'hash code calcolato (basato sull'ISBN).
     *
     * @pre L'oggetto deve essere inizializzato correttamente.
     * @post Restituisce un intero consistente con il metodo equals.
     */
    @Override
    public int hashCode(){
        return this.ISBN.hashCode();
    }
    /**
     * @brief Restituisce una rappresentazione testuale del plibro.
     * @return Una stringa contenente i dettagli principali del libro.
     *
     * @post Restituisce una stringa non nulla che descrive lo stato dell'oggetto.
     */
    @Override
    public String toString(){
        StringBuffer sb=new StringBuffer();
        sb.append("\nTitolo=");
        sb.append(titolo);
        sb.append("\nAutore=");
        sb.append(autore);
        sb.append("\nISBN=");
        sb.append(ISBN);
        sb.append("\nNumero di copie disponibili=");
        sb.append(numeroCopie);
        sb.append("\nData di pubblicazione=");
        sb.append(dataPubblicazione);
        return sb.toString();
    }
}
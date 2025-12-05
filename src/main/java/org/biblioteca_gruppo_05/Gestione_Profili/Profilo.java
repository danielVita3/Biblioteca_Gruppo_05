package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Eccezioni.ErroreMatricolaException;

import java.io.Serializable;
import java.lang.Comparable;

/**
 * @class Profilo
 * @brief Rappresenta un profilo utente del sistema bibliotecario.
 *
 * Questa classe modella l'entità Profilo. Contiene i dati anagrafici,
 * i contatti e le informazioni relative allo stato dell'utente all'interno
 * del sistema (es. numero di prestiti attivi).
 * Implementa l'interfaccia Comparable per permettere l'ordinamento (per matricola)
 * e Serializable per il salvataggio su file.
 */
public class Profilo implements Serializable, Comparable<Profilo> {

    /**  Nome dell'utente. */
    private String nome;

    /**  Cognome dell'utente. */
    private String cognome;

    /**  Codice univoco identificativo (Matricola). */
    private String matricola;

    /**  Indirizzo email istituzionale. */
    private String mail;

    /**  Contatore dei prestiti attualmente attivi */
    private int numeroPrestiti;

    /**
     * @brief Costruttore della classe Profilo.
     *
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param matricola Il codice matricola univoco.
     * @param mail L'indirizzo email dell'utente.
     *
     * @pre I parametri nome, cognome, matricola e mail non devono essere nulli o vuoti.
     * @post Viene istanziato un nuovo oggetto Profilo con i dati forniti e numeroPrestiti inizializzato a 0.
     */
    public Profilo(String nome, String cognome, String matricola, String mail){
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.mail = mail;
        this.numeroPrestiti = 0;
    }

    /**
     * @brief Verifica la validità del formato della matricola.
     *
     * @return true se la matricola è valida, false altrimenti.
     *
     * @pre La matricola deve essere stata inizializzata (non null).
     * @post Restituisce l'esito della validazione senza modificare lo stato dell'oggetto.
     */
    public static boolean controlloMatricola()throws ErroreMatricolaException {
        // Logica di validazione (omessa)
        return true;
    }

    /**
     * @brief Restituisce il nome dell'utente.
     * @return Stringa rappresentante il nome.
     * @post Restituisce l'attributo richiesto
     */
    public String getNome(){ return nome; }

    /**
     * @brief Restituisce il cognome dell'utente.
     * @return Stringa rappresentante il cognome.
     * @post Restituisce l'attributo richiesto
     */
    public String getCognome(){ return cognome; }

    /**
     * @brief Restituisce la matricola dell'utente.
     * @return Stringa univoca della matricola.
     * @post Restituisce l'attributo richiesto
     */
    public String getMatricola(){ return matricola; }

    /**
     * @brief Restituisce l'email dell'utente.
     * @return Stringa contenente l'indirizzo email.
     * @post Restituisce l'attributo richiesto
     */
    public String getMail(){ return mail; }

    /**
     * @brief Restituisce il numero di prestiti associati all'utente.
     * @return Intero rappresentante il conteggio dei prestiti.
     * @post Restituisce l'attributo richiesto
     */
    public int getNumeroPrestiti(){ return numeroPrestiti; }

    /**
     * @brief Imposta o modifica il nome dell'utente.
     * @param nome Il nuovo nome da assegnare.
     *
     * @pre Il parametro nome non deve essere null o vuoto.
     * @post L'attributo nome viene aggiornato con il nuovo valore.
     */
    public void setNome(String nome){ this.nome = nome; }

    /**
     * @brief Imposta o modifica il cognome dell'utente.
     * @param cognome Il nuovo cognome da assegnare.
     *
     * @pre Il parametro cognome non deve essere null o vuoto.
     * @post L'attributo cognome viene aggiornato con il nuovo valore.
     */
    public void setCognome(String cognome){ this.cognome = cognome; }

    /**
     * @brief Imposta o modifica la matricola dell'utente.
     * @param matricola La nuova matricola da assegnare.
     *
     * @pre Il parametro matricola non deve essere null e deve rispettare il formato univoco.
     * @post L'attributo matricola viene aggiornato.
     */
    public void setMatricola(String matricola){ this.matricola = matricola; }

    /**
     * @brief Imposta o modifica l'indirizzo email dell'utente.
     * @param mail La nuova email da assegnare.
     *
     * @pre Il parametro mail deve essere una stringa valida e non null.
     * @post L'attributo mail viene aggiornato.
     */
    public void setMail(String mail){ this.mail = mail; }

    /**
     * @brief Aggiorna il numero dei prestiti dell'utente.
     * @param prestito Il nuovo valore del contatore prestiti.
     *
     * @pre Il parametro prestito deve essere un intero +1, -1.
     * @post L'attributo numeroPrestiti viene aggiornato con il valore fornito.
     */
    public void setNumeroPrestiti(int prestito){ this.numeroPrestiti = prestito; }

    /**
     * @brief Confronta questo profilo con un altro oggetto per verificarne l'uguaglianza.
     * @param obj L'oggetto con cui confrontare il profilo corrente.
     * @return true se gli oggetti sono uguali, false altrimenti.
     *
     * @pre obj può essere null o un oggetto di qualsiasi tipo.
     * @post Restituisce true solo se obj è un'istanza di Profilo con la stessa matricola.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * @brief Confronta due profili per l'ordinamento.
     * @param p1 Il profilo da confrontare con l'istanza corrente.
     * @return Un intero negativo, zero o positivo se questo oggetto è minore, uguale o maggiore di p1.
     *
     * @pre p1 non deve essere null.
     * @post Viene restituito il valore dettato dall'ordine.
     */
    @Override
    public int compareTo(Profilo p1){
        return 0;
    }

    /**
     * @brief Calcola il codice hash del profilo.
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
     * @brief Restituisce una rappresentazione testuale del profilo.
     * @return Una stringa contenente i dettagli principali del profilo.
     *
     * @post Restituisce una stringa non nulla che descrive lo stato dell'oggetto.
     */
    @Override
    public String toString(){
        return "";
    }
}
package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Eccezioni.ErroreLetturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.ErroreScritturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.LibroNonTrovatoException;
import org.biblioteca_gruppo_05.Eccezioni.PrestitoNonTrovatoException;
import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @class ArchivioPrestiti
 * @brief Gestisce l'insieme dei prestiti attivi.
 *
 * Questa classe funge da registro centrale per tutte le operazioni di prestito.
 * Permette di registrare nuovi prestiti, gestire le restituzioni, effettuare ricerche
 * (per utente o per libro) e visualizzare lo stato corrente.
 * Gestisce inoltre la persistenza dei dati salvando e caricando la mappa dei prestiti su file.
 */
public class ArchivioPrestiti implements Serializable {

    /** @brief Mappa che contiene i prestiti. La chiave è l'ID univoco del prestito, il valore è l'oggetto Prestito. */
    private Map<Integer, Prestito> prestiti;

    /** @brief Percorso o nome del file utilizzato per il salvataggio dei dati. */
    private final String fileName;

    /**
     * @brief Costruttore della classe ArchivioPrestiti.
     *
     * Inizializza la struttura dati (LinkedHashMap) e imposta il nome del file per la persistenza.
     *
     * @param fileName Il nome del file su cui salvare/caricare i dati.
     *
     * @pre fileName non deve essere null o vuoto.
     * @post Viene creato un registro prestiti vuoto pronto per l'uso.
     */
    public ArchivioPrestiti(String fileName){
        this.prestiti = new LinkedHashMap<>();
        this.fileName = fileName;
    }

    /**
     * @brief Registra un nuovo prestito nel sistema.
     *
     * @param p L'oggetto Prestito da aggiungere.
     *
     * @pre p non deve essere null e deve contenere riferimenti validi a Libro e Profilo.
     * @post Il prestito viene aggiunto alla mappa, l'utente incrementa il numero di prestiti attivi e il libro decrementa il numero di copie disponibili.
     */
    public void registraPrestito(Prestito p){}

    /**
     * @brief Gestisce la restituzione di un prestito (chiusura della transazione).
     *
     * Rimuove il prestito dall'elenco dei prestiti attivi o ne aggiorna lo stato a "restituito".
     *
     * @param p L'oggetto Prestito da chiudere/restituire.
     *
     * @pre p deve esistere nell'archivio.
     * @post Il prestito viene rimosso o aggiornato, l'utente decrementa il numero di prestiti attivi e il libro incrementa il numero di copie disponibili.
     */
    public void restituzionPrestito(Prestito p){}

    /**
     * @brief Cerca tutti i prestiti associati a una specifica matricola utente.
     *
     * @param matricola La matricola dell'utente.
     * @return Una lista di oggetti Prestito associati all'utente.
     * @throws PrestitoNonTrovatoException Se non ci sono prestiti associati a questa matricola.
     *
     * @pre matricola non deve essere null.
     * @post Restituisce una lista senza modificare l'archivio.
     */
    public List<Prestito> ricercaPrestitoPerMatricola(String matricola) throws PrestitoNonTrovatoException {
        return null; // Implementazione omessa
    }

    /**
     * @brief Cerca tutti i prestiti associati a uno specifico libro (tramite ISBN).
     *
     * Utile per sapere chi ha in prestito una copia di un determinato libro o per vedere lo storico.
     *
     * @param ISBN Il codice ISBN del libro.
     * @return Una lista di oggetti Prestito che riguardano quel libro.
     * @throws PrestitoNonTrovatoException Se non ci sono prestiti per questo libro.
     *
     * @pre ISBN deve essere valido.
     * @post Restituisce una lista senza modificare l'archivio.
     */
    public List<Prestito> ricercaPrestitoPerISBN(String ISBN) throws PrestitoNonTrovatoException {
        List <Prestito> results=new ArrayList<>();
        for(Prestito p:prestiti.values()){
            if(p.getLibro().equalsIgnoreCase(ISBN))
                results.add(p);
        }
        if(results.isEmpty()){
            throw new PrestitoNonTrovatoException("Nessun libro trovato con ISBN: " + ISBN);
        }
        return results;
    }

    /**
     * @brief Stampa a video o visualizza l'elenco completo dei prestiti.
     *
     * @post L'archivio rimane invariato.
     */
    public void visualizzaPrestiti(){}

    /**
     * @brief Salva lo stato corrente dell'archivio su file.
     *
     * Serializza la mappa dei prestiti e la scrive sul file specificato.
     *
     * @throws ErroreScritturaFileException Se si verificano errori di I/O durante il salvataggio.
     *
     * @pre Il percorso del file deve essere scrivibile.
     * @post I dati attuali sono persistiti su disco.
     */
    private void salvaSuFile() throws ErroreScritturaFileException {}

    /**
     * @brief Carica lo stato dell'archivio da file.
     *
     * Deserializza la mappa dei prestiti dal file specificato.
     *
     * @throws ErroreLetturaFileException Se il file non esiste o si verificano errori di I/O.
     *
     * @pre Il file deve esistere e contenere dati validi.
     * @post La mappa 'prestiti' viene popolata con i dati letti.
     */
    private void leggiDaFile() throws ErroreLetturaFileException {}

    /**
     * @brief Restituisce una rappresentazione testuale dell'archivio prestiti.
     * @return Stringa descrittiva del contenuto dell'archivio.
     */
    @Override
    public String toString(){
        return "";
    }
}
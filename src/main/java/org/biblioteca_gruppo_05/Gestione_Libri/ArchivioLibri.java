package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @class ArchivioLibri
 * @brief Gestisce l'insieme dei libri presenti nella biblioteca.
 *
 * Questa classe funge da contenitore per tutti gli oggetti Libro.
 * Permette operazioni di aggiunta, rimozione, ricerca (per ISBN, titolo, autore) e visualizzazione.
 * Gestisce inoltre la persistenza dei dati salvando e caricando la mappa dei libri su file.
 */
public class ArchivioLibri implements Serializable {

    /** @brief Mappa che contiene i libri. La chiave è l'ISBN (univoco), il valore è l'oggetto Libro. */
    private Map<String, Libro> libri;

    /** @brief Percorso o nome del file utilizzato per il salvataggio dei dati. */
    private final String fileName;

    /**
     * @brief Costruttore della classe ArchivioLibri.
     * @param fileName Nome del file nel quale verrano salvati i dati
     * Inizializza la struttura dati (LinkedHashMap) e imposta il nome del file per la persistenza.
     * @pre Il parametro filename non deve essere null o inesistente
     * @post Crea l'oggetto ArchivioLibro
     */
    public ArchivioLibri(String fileName){
        this.libri = new LinkedHashMap<>();
        this.fileName = fileName;
    }

    /**
     * @brief Aggiunge un nuovo libro all'archivio.
     *
     * @param l L'oggetto Libro da aggiungere.
     * @throws LibroEsistenteException Se esiste già un libro con lo stesso ISBN.
     *
     * @pre l non deve essere null .
     * @post Il libro viene aggiunto alla mappa.
     */
    public void aggiungiLibro(Libro l) throws LibroEsistenteException {};

    /**
     * @brief Rimuove un libro esistente dall'archivio.
     *
     * @param l L'oggetto Libro da rimuovere.
     * @throws LibroNonTrovatoException Se il libro non è presente o non può essere rimosso.
     *
     * @pre l non deve essere null.
     * @post Il libro viene rimosso dalla mappa.
     */
    public void rimuoviLibro(Libro l) throws LibroNonTrovatoException {};

    /**
     * @brief Cerca un singolo libro tramite il codice ISBN.
     *
     * @param ISBN Il codice ISBN numerico da cercare.
     * @return L'oggetto Libro corrispondente.
     * @throws LibroNonTrovatoException Se nessun libro corrisponde all'ISBN fornito.
     *
     * @pre ISBN deve essere non null.
     * @post Restituisce il libro senza rimuoverlo o modificarlo.
     */
    public Libro ricercaLibroPerISBN(int ISBN) throws LibroNonTrovatoException {
        return null; // Implementazione omessa
    };

    /**
     * @brief Cerca libri tramite il titolo.
     *
     * @param titolo Il titolo da cercare.
     * @return Una lista di oggetti Libro che corrispondono al titolo.
     * @throws LibroNonTrovatoException Se la ricerca non produce risultati.
     *
     * @pre titolo non deve essere null.
     * @post Restituisce una lista senza modificare l'archivio.
     */
    public List<Libro> ricercaLibriPerTitolo(String titolo) throws LibroNonTrovatoException {
        return null; // Implementazione omessa
    };

    /**
     * @brief Cerca libri tramite l'autore.
     *
     * @param autore Il nome dell'autore da cercare.
     * @return Una lista di oggetti Libro scritti dall'autore specificato.
     * @throws LibroNonTrovatoException Se la ricerca non produce risultati.
     *
     * @pre autore non deve essere null.
     * @post Restituisce una lista senza modificare l'archivio.
     */
    public List<Libro> ricercaLibriPerAutore(String autore) throws LibroNonTrovatoException {
        return null; // Implementazione omessa
    };

    /**
     * @brief Stampa a video o visualizza l'elenco completo dei libri.
     *
     * @post L'archivio rimane invariato.
     */
    public void visualizzaLibri(){};

    /**
     * @brief Salva lo stato corrente dell'archivio su file.
     *
     * Serializza la mappa dei libri e la scrive sul file specificato nel costruttore.
     *
     * @throws ErroreScritturaFileException Se si verificano errori di I/O durante il salvataggio.
     *
     * @pre Il percorso del file deve essere scrivibile.
     * @post I dati attuali sono persistiti su disco.
     */
    private void salvaSuFile() throws ErroreScritturaFileException {};

    /**
     * @brief Carica lo stato dell'archivio da file.
     *
     * Deserializza la mappa dei libri dal file specificato, sovrascrivendo o popolando l'archivio corrente.
     *
     * @throws ErroreLetturaFileException Se il file non esiste o si verificano errori di I/O.
     *
     * @pre Il file deve esistere e contenere dati validi.
     * @post La mappa 'libri' viene popolata con i dati letti.
     */
    private void leggiDaFile() throws ErroreLetturaFileException {};

    /**
     * @brief Restituisce una rappresentazione testuale dell'intero archivio.
     * @return Stringa descrittiva del contenuto dell'archivio.
     */
    @Override
    public String toString(){
        return "";
    };
}
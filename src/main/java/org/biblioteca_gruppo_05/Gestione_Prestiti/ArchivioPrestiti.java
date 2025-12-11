package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Eccezioni.*;
import org.biblioteca_gruppo_05.Gestione_Libri.ArchivioLibri;
import org.biblioteca_gruppo_05.Eccezioni.ErroreLetturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.ErroreScritturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.LibroNonTrovatoException;
import org.biblioteca_gruppo_05.Eccezioni.PrestitoNonTrovatoException;
import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.ArchivioProfili;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

import java.io.*;
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
        try{
            leggiDaFile();
        }catch(ErroreLetturaFileException e){
            System.err.println(e.getMessage());
        }

    }

    /**
     * @brief Registra un nuovo prestito nel sistema.
     *
     * @param p L'oggetto Prestito da aggiungere.
     *
     * @pre p non deve essere null e deve contenere riferimenti validi a Libro e Profilo.
     * @post Il prestito viene aggiunto alla mappa, l'utente incrementa il numero di prestiti attivi e il libro decrementa il numero di copie disponibili.
     */
    public void registraPrestito(Prestito p){
        prestiti.put(p.getId(), p);
        ArchivioLibri l=new ArchivioLibri("libri.bin");
        ArchivioProfili pro=new ArchivioProfili("profili.bin");
        try {
            l.ricercaLibroPerISBN(p.getLibro()).decrementaNumeroCopie();
        }catch(LibroNonTrovatoException e){
            System.err.println(e.getMessage());
        }
        try {
            pro.ricercaProfiloPerMatricola(p.getProfilo()).setNumeroPrestiti(1);
        }catch(UtenteNonTrovatoException e){
            System.err.println(e.getMessage());
        }
        try{
            l.salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println("Errore di scrittura" +  e.getMessage());
        }
        try{
            pro.salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println("Errore di scrittura" +  e.getMessage());
        }
        try{
            salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println("Errore di scrittura" +  e.getMessage());
        }
    }

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
    public void restituzionPrestito(Prestito p){
        prestiti.remove(p.getId());
        ArchivioLibri l=new ArchivioLibri("libri.bin");
        ArchivioProfili pro=new ArchivioProfili("profili.bin");
        try {
            l.ricercaLibroPerISBN(p.getLibro()).incrementaNumCopie();
        }catch(LibroNonTrovatoException e){
            System.err.println(e.getMessage());
        }
        try {
            pro.ricercaProfiloPerMatricola(p.getProfilo()).setNumeroPrestiti(-1);
        }catch(UtenteNonTrovatoException e){
            System.err.println(e.getMessage());
        }
        try{
            l.salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println("Errore di scrittura" +  e.getMessage());
        }
        try{
            pro.salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println("Errore di scrittura" +  e.getMessage());
        }
        try{
            salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println("Errore di scrittura" +  e.getMessage());
        }

    }

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
        List <Prestito>listaMatricola= new ArrayList<>();
        for(Prestito p: prestiti.values()){
            if(p.getProfilo().equals(matricola)){
                listaMatricola.add(p);
            }
        }
        if(listaMatricola.isEmpty()){
            throw new PrestitoNonTrovatoException("Nessun Utente trovato con questa matricola: "+ matricola);
        }
        return listaMatricola;
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
        if (Libro.controllaISBN(ISBN)) {
        List<Prestito> results = new ArrayList<>();
        for (Prestito p : prestiti.values()) {
            if (p.getLibro().equalsIgnoreCase(ISBN))
                results.add(p);
        }
        if (results.isEmpty()) {
            throw new PrestitoNonTrovatoException("Nessun libro trovato con ISBN: " + ISBN);
        }
        return results;
    }
        return List.of();
    }

    /**
     * @brief Stampa a video o visualizza l'elenco completo dei prestiti.
     *
     * @post L'archivio rimane invariato.
     */
    public List<Prestito> visualizzaPrestiti() throws PrestitoNonTrovatoException {
        List<Prestito> tuttiPrestiti=new ArrayList<>();
        tuttiPrestiti.addAll(prestiti.values());
        if(tuttiPrestiti.isEmpty()){
            throw new PrestitoNonTrovatoException("Archivio vuoto");
        }
        return tuttiPrestiti;
    }

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
    public void salvaSuFile() throws ErroreScritturaFileException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(prestiti);
        } catch (IOException e) {
            throw new ErroreScritturaFileException("Errore durante il salvataggio dell'archivio libri: " + e.getMessage());
        }
    }

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
    private void leggiDaFile() throws ErroreLetturaFileException {
        try( ObjectInputStream ob= new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))){
            Object data=ob.readObject();
            if(data instanceof Map){
                prestiti=(Map <Integer,Prestito>) data;
            }else{
                throw new ErroreLetturaFileException("File non contiene nulla o non mappe");
            }
        }catch(FileNotFoundException e){
            throw new ErroreLetturaFileException("File Archivio non trovato");
        }catch(IOException|ClassNotFoundException e){
            throw new ErroreLetturaFileException("Errore di lettura o dati del file corrotti: " + e.getMessage());

        }
    }

    /**
     * @brief Restituisce una rappresentazione testuale dell'archivio prestiti.
     * @return Stringa descrittiva del contenuto dell'archivio.
     */
    @Override
    public String toString(){
        StringBuffer str=new StringBuffer();
        for(Prestito p: prestiti.values()){
            str.append(p.toString());
            str.append("\n");
        }
        return str.toString();
    }
}
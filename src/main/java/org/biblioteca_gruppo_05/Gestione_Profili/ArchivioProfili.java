package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Gestione_Libri.Libro; // Mantenuto se serve per riferimenti, ma non per i return type
import org.biblioteca_gruppo_05.Eccezioni.*;
import org.biblioteca_gruppo_05.Gestione_Prestiti.ArchivioPrestiti;
import org.biblioteca_gruppo_05.Gestione_Prestiti.Prestito;

import java.io.*;
import java.util.*;

import static org.biblioteca_gruppo_05.Gestione_Profili.Profilo.controlloMatricola;

/**
 * @class ArchivioProfili
 * @brief Gestisce l'insieme dei profili (utenti) iscritti alla biblioteca.
 *
 * Questa classe funge da contenitore per tutti gli oggetti Profilo.
 * Permette operazioni di aggiunta, rimozione, ricerca (per vari criteri) e visualizzazione.
 * Gestisce inoltre la persistenza dei dati salvando e caricando la mappa dei profili su file.
 */
public class ArchivioProfili implements Serializable {

    /** @brief Mappa che contiene i profili. La chiave è la matricola (univoca), il valore è l'oggetto Profilo. */
    private Map<String, Profilo> profili;

    /** @brief Percorso o nome del file utilizzato per il salvataggio dei dati. */
    private final String fileName;

    /**
     * @brief Costruttore della classe ArchivioProfili.
     *
     * Inizializza la struttura dati (LinkedHashMap per mantenere l'ordine di inserimento)
     * e imposta il nome del file per la persistenza.
     *
     * @param fileName Il nome del file su cui salvare/caricare i dati.
     *
     * @pre fileName non deve essere null o vuoto.
     * @post Viene creato un archivio vuoto pronto per l'uso.
     */
    public ArchivioProfili(String fileName){
        this.profili = new LinkedHashMap<>();
        this.fileName = fileName;
        try{
            leggiDaFile();
        }catch(ErroreLetturaFileException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * @brief Aggiunge un nuovo profilo all'archivio.
     *
     * @param p L'oggetto Profilo da aggiungere.
     * @throws UtenteEsitenteException Se esiste già un utente con la stessa matricola.
     *
     * @pre p non deve essere null e deve avere una matricola valida.
     * @post Il profilo viene aggiunto alla mappa.
     */
    public void aggiungiProfilo(Profilo p) throws UtenteEsitenteException {

       if(this.profili.containsKey(p.getMatricola())){
           throw new UtenteEsitenteException("Utente già esistente");
       }
           this.profili.put(p.getMatricola(),p);

        try {
            salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * @brief Rimuove un profilo esistente dall'archivio.
     *
     * @param matricola L'oggetto matricola da rimuovere.
     * @throws UtenteNonTrovatoException Se il profilo non è presente nell'archivio.
     *
     * @pre matricola non deve essere null.
     * @post Il profilo viene rimosso dalla mappa.
     */
    public void rimuoviProfilo(String matricola) throws UtenteNonTrovatoException {
        ArchivioPrestiti a=new ArchivioPrestiti("prestiti.bin");
        for (Prestito p : a.getPrestiti().values()) {
            if (p.getProfilo().equalsIgnoreCase(matricola))
                throw new RuntimeException("Prestiti attivi con questo profilo impossibile eliminare");
        }
        if(this.profili.containsKey(matricola)){
            this.profili.remove(matricola);
        }else{
            throw new UtenteNonTrovatoException("Utente non trovato con questa matricola: " +matricola);
        }
        try {
            salvaSuFile();
        }catch(ErroreScritturaFileException e){
            System.err.println(e.getMessage());
        }

    }

    /**
     * @brief Cerca un singolo profilo tramite la matricola.
     *
     * @param matricola La matricola univoca da cercare.
     * @return L'oggetto Profilo corrispondente.
     * @throws UtenteNonTrovatoException Se nessun utente corrisponde alla matricola fornita.
     *
     * @pre matricola non deve essere null o vuota.
     * @post Restituisce il profilo senza rimuoverlo o modificarlo.
     */
    public Profilo ricercaProfiloPerMatricola(String matricola) throws UtenteNonTrovatoException,ErroreMatricolaException {
        if(controlloMatricola(matricola)){
            if(this.profili.containsKey(matricola)){
                return profili.get(matricola);
            }else{
                throw new UtenteNonTrovatoException("Utente non trovato con questa matricola: "+matricola);
            }
        }


        return null;
    }

    /**
     * @brief Cerca profili tramite il nome.
     *
     * @param nome Il nome da cercare.
     * @return Una lista di oggetti Profilo che corrispondono al nome.
     * @throws UtenteNonTrovatoException Se la ricerca non produce risultati.
     *
     * @pre nome non deve essere null.
     * @post Restituisce una lista senza modificare l'archivio.
     */
    public List<Profilo> ricercaProfiloPerNome(String nome) throws UtenteNonTrovatoException {
        List <Profilo>listaNome= new ArrayList<>();
        for(Profilo p: profili.values()){
            if(p.getNome().equals(nome)){
                listaNome.add(p);
            }
        }
        if(listaNome.isEmpty()){
            throw new UtenteNonTrovatoException("Nessun Utente trovato con questo nome: "+ nome);
        }
        return listaNome;
    }

    /**
     * @brief Cerca profili tramite il cognome.
     *
     * @param cognome Il cognome da cercare.
     * @return Una lista di oggetti Profilo che corrispondono al cognome.
     * @throws UtenteNonTrovatoException Se la ricerca non produce risultati.
     *
     * @pre cognome non deve essere null.
     * @post Restituisce una lista senza modificare l'archivio.
     */
    public List<Profilo> ricercaProfiloPerCognome(String cognome) throws UtenteNonTrovatoException {
        List <Profilo>listaNome= new ArrayList<>();
        for(Profilo p: profili.values()){
            if(p.getCognome().equals(cognome)){
                listaNome.add(p);
            }
        }
        if(listaNome.isEmpty()){
            throw new UtenteNonTrovatoException("Nessun Utente trovato con questo cognome: "+ cognome);
        }
        return listaNome;
    }

    /**
     * @brief Stampa a video o visualizza l'elenco completo dei profili.
     *
     * @post L'archivio rimane invariato.
     */
    public List<Profilo> visualizzaProfili() throws UtenteNonTrovatoException {
        if (profili.isEmpty()) {
            throw new UtenteNonTrovatoException("Nessun profilo trovato");
        }
        List<Profilo> results = new ArrayList<>();
        for (Profilo l : profili.values()) {
            results.add(l);



        }
        return results;
    };

    /**
     * @brief Salva lo stato corrente dell'archivio su file.
     *
     * Serializza la mappa dei profili e la scrive sul file specificato nel costruttore.
     *
     * @throws ErroreScritturaFileException Se si verificano errori di I/O durante il salvataggio.
     *
     * @pre Il percorso del file deve essere scrivibile.
     * @post I dati attuali sono persistiti su disco.
     */
    public void salvaSuFile() throws ErroreScritturaFileException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(profili);
        } catch (IOException e) {
            throw new ErroreScritturaFileException("Errore durante il salvataggio dell'archivio libri: " + e.getMessage());
        }
    }

    /**
     * @brief Carica lo stato dell'archivio da file.
     *
     * Deserializza la mappa dei profili dal file specificato, sovrascrivendo o popolando l'archivio corrente.
     *
     * @throws ErroreLetturaFileException Se il file non esiste o si verificano errori di I/O.
     *
     * @pre Il file deve esistere e contenere dati validi.
     * @post La mappa 'profilo' viene popolata con i dati letti.
     */
    public void leggiDaFile() throws ErroreLetturaFileException {
        try( ObjectInputStream ob= new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))){
            Object data=ob.readObject();
            if(data instanceof Map){
               profili=(Map <String,Profilo>) data;
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
     * @brief Restituisce una rappresentazione testuale dell'intero archivio.
     * @return Stringa descrittiva del contenuto dell'archivio.
     */
    @Override
    public String toString(){
        StringBuffer str=new StringBuffer();
        for(Profilo p: profili.values()){
            str.append(p.toString());
            str.append("\n");
        }
        return str.toString();
    }
}
package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioLibriTest {

    private ArchivioLibri archivio;
    private final String FILE_TEST = "archivio_test_temp.dat"; // Nome del file temporaneo
    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    void setUp() throws ErroreISBNException {
        // 1. Assicuriamoci che il file non esista prima di iniziare
        File f = new File(FILE_TEST);
        if (f.exists()) {
            f.delete();
        }

        // 2. Inizializziamo l'archivio
        archivio = new ArchivioLibri(FILE_TEST);

        // 3. Creiamo due libri di prova
        libro1 = new Libro("Harry Potter", "J.K. Rowling", "9788869183157", LocalDate.of(1997, 6, 26));
        libro2 = new Libro("Il Signore degli Anelli", "Tolkien", "9788845294044", LocalDate.of(1954, 7, 29));
    }

    @AfterEach
    void tearDown() {
        // Pulizia: cancelliamo il file creato dal test per non lasciare sporcizia
        File f = new File(FILE_TEST);
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    @DisplayName("Aggiunta libro: deve inserire il libro e salvarlo")
    void testAggiungiLibro() throws LibroEsistenteException, LibroNonTrovatoException, ErroreISBNException {
        archivio.aggiungiLibro(libro1);

        // Verifica che lo trovi
        Libro trovato = archivio.ricercaLibroPerISBN(libro1.getISBN());
        assertEquals(libro1, trovato);
    }

    @Test
    @DisplayName("Aggiunta duplicato: deve lanciare eccezione")
    void testAggiungiLibroDuplicato() throws LibroEsistenteException {
        archivio.aggiungiLibro(libro1);

        // Provo ad aggiungere lo stesso libro
        assertThrows(LibroEsistenteException.class, () -> {
            archivio.aggiungiLibro(libro1);
        });
    }

    @Test
    @DisplayName("Rimozione libro: deve eliminare il libro dall'archivio")
    void testRimuoviLibro() throws LibroEsistenteException, LibroNonTrovatoException, ErroreISBNException {
        archivio.aggiungiLibro(libro1);

        // Rimuovo
        archivio.rimuoviLibro(libro1.getISBN());

        // Verifico che cercandolo ora lanci eccezione o dia null (in base alla tua implementazione)
        assertThrows(LibroNonTrovatoException.class, () -> {
            archivio.ricercaLibroPerISBN(libro1.getISBN());
        });
    }

    @Test
    @DisplayName("Rimozione libro inesistente: deve lanciare eccezione")
    void testRimuoviLibroInesistente() {
        assertThrows(LibroNonTrovatoException.class, () -> {
            archivio.rimuoviLibro("9780000000000"); // ISBN valido ma non presente
        });
    }

    @Test
    @DisplayName("Ricerca per Titolo: deve trovare i libri corretti")
    void testRicercaPerTitolo() throws LibroEsistenteException, LibroNonTrovatoException {
        archivio.aggiungiLibro(libro1); // Harry Potter
        archivio.aggiungiLibro(libro2); // Signore degli Anelli

        List<Libro> risultati = archivio.ricercaLibriPerTitolo("Harry Potter");

        assertEquals(1, risultati.size());
        assertEquals(libro1.getISBN(), risultati.get(0).getISBN());
    }

    @Test
    @DisplayName("Ricerca per Autore: deve trovare i libri corretti")
    void testRicercaPerAutore() throws LibroEsistenteException, LibroNonTrovatoException {
        archivio.aggiungiLibro(libro1);

        List<Libro> risultati = archivio.ricercaLibriPerAutore("J.K. Rowling");

        assertFalse(risultati.isEmpty());
        assertEquals("J.K. Rowling", risultati.get(0).getAutore());
    }

    @Test
    @DisplayName("Test Persistenza: chiudendo e riaprendo l'archivio i dati devono esserci")
    void testPersistenzaDati() throws LibroEsistenteException, LibroNonTrovatoException, ErroreISBNException {
        // 1. Aggiungo un libro al primo archivio
        archivio.aggiungiLibro(libro1);

        // Il metodo aggiungiLibro chiama salvaSuFile() internamente.

        // 2. Simulo il riavvio dell'applicazione creando un NUOVO oggetto archivio
        // che legge dallo STESSO file.
        ArchivioLibri archivioRicaricato = new ArchivioLibri(FILE_TEST);

        // 3. Verifico che il libro ci sia nel nuovo oggetto
        Libro libroCaricato = archivioRicaricato.ricercaLibroPerISBN(libro1.getISBN());

        assertNotNull(libroCaricato, "Il libro dovrebbe essere stato caricato dal file");
        assertEquals(libro1.getTitolo(), libroCaricato.getTitolo());
    }

    @Test
    @DisplayName("Visualizza libri: deve ritornare la lista completa")
    void testVisualizzaLibri() throws LibroEsistenteException, LibroNonTrovatoException {
        archivio.aggiungiLibro(libro1);
        archivio.aggiungiLibro(libro2);

        List<Libro> lista = archivio.visualizzaLibri();
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Visualizza libri vuoto: deve lanciare eccezione")
    void testVisualizzaLibriVuoto() {
        assertThrows(LibroNonTrovatoException.class, () -> {
            archivio.visualizzaLibri();
        });
    }
}
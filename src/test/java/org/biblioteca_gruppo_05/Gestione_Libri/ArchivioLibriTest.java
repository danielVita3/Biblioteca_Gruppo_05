package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioLibriTest {

    private ArchivioLibri archivio;
    // Usa un nome file diverso per evitare conflitti se il sistema operativo blocca il file
    private final String FILE_TEST = "archivioTestUnitario.bin";
    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    void setUp() throws Exception {
        // 1. PULIZIA AGGRESSIVA
        // Prima di ogni test, distruggiamo il file precedente.
        File f = new File(FILE_TEST);
        if (f.exists()) {
            // Proviamo a cancellare
            boolean deleted = f.delete();
            if (!deleted) {
                // Se non riusciamo a cancellarlo (file bloccato), lo svuotiamo forzatamente
                new FileWriter(f).close();
            }
        }

        // 2. Inizializziamo l'archivio (ora troverà o nulla o file vuoto)
        archivio = new ArchivioLibri(FILE_TEST);

        // 3. Creiamo i libri per i test
        libro1 = new Libro("Harry Potter", "J.K. Rowling", "9788869183157", LocalDate.of(1997, 6, 26));
        libro2 = new Libro("Il Signore degli Anelli", "Tolkien", "9788845294044", LocalDate.of(1954, 7, 29));
    }

    @AfterEach
    void tearDown() {
        // Pulizia finale
        File f = new File(FILE_TEST);
        if (f.exists()) {
            f.delete();
        }
    }

    // --- TEST FUNZIONALI BASE ---

    @Test
    @DisplayName("Aggiunta libro: inserimento e verifica presenza")
    void testAggiungiLibro() throws Exception {
        archivio.aggiungiLibro(libro1);
        Libro trovato = archivio.ricercaLibroPerISBN(libro1.getISBN());
        assertEquals(libro1.getTitolo(), trovato.getTitolo());
    }

    @Test
    @DisplayName("Aggiunta duplicato: deve lanciare eccezione")
    void testAggiungiLibroDuplicato() throws Exception {
        archivio.aggiungiLibro(libro1);
        assertThrows(LibroEsistenteException.class, () -> {
            archivio.aggiungiLibro(libro1);
        });
    }

    @Test
    @DisplayName("Rimozione libro: elimina e verifica assenza")
    void testRimuoviLibro() throws Exception {
        archivio.aggiungiLibro(libro1);
        archivio.rimuoviLibro(libro1.getISBN());

        assertThrows(LibroNonTrovatoException.class, () -> {
            archivio.ricercaLibroPerISBN(libro1.getISBN());
        });
    }

    @Test
    @DisplayName("Ricerca Titolo: deve trovare libro corretto")
    void testRicercaPerTitolo() throws Exception {
        archivio.aggiungiLibro(libro1);
        List<Libro> risultati = archivio.ricercaLibriPerTitolo("Harry Potter");
        assertEquals(1, risultati.size());
        assertEquals(libro1.getISBN(), risultati.get(0).getISBN());
    }

    @Test
    @DisplayName("Ricerca Autore: deve trovare libro corretto")
    void testRicercaPerAutore() throws Exception {
        archivio.aggiungiLibro(libro1);
        List<Libro> risultati = archivio.ricercaLibriPerAutore("J.K. Rowling");
        assertFalse(risultati.isEmpty());
        assertEquals(libro1.getAutore(), risultati.get(0).getAutore());
    }

    @Test
    @DisplayName("Persistenza: dati salvati su disco e ricaricati")
    void testPersistenzaDati() throws Exception {
        archivio.aggiungiLibro(libro1);

        // Simulo riavvio app ricaricando dal file
        ArchivioLibri archivioRicaricato = new ArchivioLibri(FILE_TEST);
        Libro caricato = archivioRicaricato.ricercaLibroPerISBN(libro1.getISBN());

        assertNotNull(caricato);
        assertEquals(libro1.getTitolo(), caricato.getTitolo());
    }

    @Test
    @DisplayName("Visualizza libri: eccezione se vuoto")
    void testVisualizzaLibriVuoto() {
        // Appena creato nel setUp, l'archivio è vuoto.
        assertThrows(LibroNonTrovatoException.class, () -> {
            archivio.visualizzaLibri();
        });
    }

    // --- NUOVI CASI LIMITE E CRITICI ---

    @Test
    @DisplayName("Caso Limite: Ricerca Case Insensitive (maiuscole/minuscole)")
    void testRicercaCaseInsensitive() throws Exception {
        archivio.aggiungiLibro(libro1); // "Harry Potter"

        // Cerco tutto minuscolo
        List<Libro> risultati = archivio.ricercaLibriPerTitolo("harry potter");
        assertEquals(1, risultati.size(), "Deve trovare il libro anche se cercato in minuscolo");
    }

    @Test
    @DisplayName("Caso Critico: Ricerca con ISBN nullo o vuoto")
    void testInputInvalidi() {
        assertThrows(ErroreISBNException.class, () -> archivio.ricercaLibroPerISBN(null));
        assertThrows(ErroreISBNException.class, () -> archivio.ricercaLibroPerISBN(""));

        // Verifica rimozione con ISBN non esistente
        assertThrows(LibroNonTrovatoException.class, () -> archivio.rimuoviLibro("9780000000000"));
    }

    @Test
    @DisplayName("Caso Critico: Aggiunta libro nullo (NullPointerException)")
    void testAggiungiNull() {
        assertThrows(NullPointerException.class, () -> archivio.aggiungiLibro(null));
    }

    @Test
    @DisplayName("Caso Critico: File Corrotto (Stream Header Invalid)")
    void testFileCorrotto() throws IOException {
        // 1. Corrompiamo il file scrivendoci testo semplice invece di oggetti
        try (FileWriter fw = new FileWriter(FILE_TEST)) {
            fw.write("QUESTO_NON_E_UN_OGGETTO_JAVA");
        }

        // 2. Creiamo un nuovo archivio che proverà a leggere il file rotto
        // Il costruttore di ArchivioLibri cattura l'eccezione internamente e stampa l'errore,
        // ma NON deve crashare l'applicazione. L'archivio deve partire vuoto.
        ArchivioLibri archivioCorrotto = new ArchivioLibri(FILE_TEST);

        // 3. Verifichiamo che sia utilizzabile (vuoto ma funzionante)
        assertThrows(LibroNonTrovatoException.class, () -> archivioCorrotto.visualizzaLibri());

        // Deve permettermi di aggiungere nuovi libri sovrascrivendo il file rotto
        assertDoesNotThrow(() -> archivioCorrotto.aggiungiLibro(libro1));
    }

    @Test
    @DisplayName("Caso Limite: Ciclo Aggiungi -> Rimuovi -> Riaggiungi")
    void testCicloVitaLibro() throws Exception {
        // Aggiungo
        archivio.aggiungiLibro(libro1);
        // Rimuovo
        archivio.rimuoviLibro(libro1.getISBN());
        // Riaggiungo (Non deve dare errore di duplicato)
        assertDoesNotThrow(() -> archivio.aggiungiLibro(libro1));

        assertEquals(libro1, archivio.ricercaLibroPerISBN(libro1.getISBN()));
    }
}
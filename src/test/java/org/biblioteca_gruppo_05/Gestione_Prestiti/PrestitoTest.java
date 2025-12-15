package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Eccezioni.*;
import org.biblioteca_gruppo_05.Gestione_Libri.ArchivioLibri;
import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Profili.ArchivioProfili;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PrestitoTest {

    // Nomi file HARDCODED nelle tue classi. Non modificarli qui.
    private final String FILE_LIBRI = "libri.bin";
    private final String FILE_PROFILI = "profili.bin";

    private final String ISBN_VALIDO = "9788812345678";
    private final String MATRICOLA_VALIDA = "0612345678";

    @BeforeEach
    void setUp() throws Exception {
        // 1. Pulizia preventiva
        cleanFiles();

        // 2. Creiamo l'ambiente necessario: Un Archivio Libri con 1 libro
        ArchivioLibri archLibri = new ArchivioLibri(FILE_LIBRI);
        Libro l = new Libro("Java", "Autore", ISBN_VALIDO, LocalDate.now());
        l.setNumeroCopie(1); // Importante: deve esserci almeno 1 copia
        archLibri.aggiungiLibro(l);

        // 3. Creiamo l'ambiente necessario: Un Archivio Profili con 1 utente
        ArchivioProfili archProfili = new ArchivioProfili(FILE_PROFILI);
        Profilo p = new Profilo("Mario", "Rossi", MATRICOLA_VALIDA, "email@test.it");
        archProfili.aggiungiProfilo(p);
    }

    @AfterEach
    void tearDown() {
        cleanFiles();
    }

    private void cleanFiles() {
        new File(FILE_LIBRI).delete();
        new File(FILE_PROFILI).delete();
    }

    @Test
    @DisplayName("Costruttore: Creazione prestito valido")
    void testCreazionePrestito() throws Exception {
        LocalDate inizio = LocalDate.now();
        LocalDate scadenza = LocalDate.now().plusDays(30);

        Prestito prestito = new Prestito(inizio, scadenza, MATRICOLA_VALIDA, ISBN_VALIDO);

        assertNotNull(prestito);
        assertEquals(MATRICOLA_VALIDA, prestito.getProfilo());
        assertEquals(ISBN_VALIDO, prestito.getLibro());
        assertEquals(0, prestito.getCostoPenale());
    }

    @Test
    @DisplayName("Caso Critico: Creazione prestito con copie esaurite")
    void testCopieEsaurite() throws Exception {
        // Modifico il libro per avere 0 copie
        ArchivioLibri archLibri = new ArchivioLibri(FILE_LIBRI);
        Libro l = archLibri.ricercaLibroPerISBN(ISBN_VALIDO);
        l.setNumeroCopie(1);
        l.decrementaNumeroCopie(); // Ora è 0
        // Salvo la modifica su file (fondamentale perché Prestito legge dal file)
        archLibri.salvaSuFile(); // ATTENZIONE: Assicurati che ArchivioLibri abbia un metodo pubblico per salvare o lo faccia in automatico

        // Nota: Poiché ArchivioLibri.salvaSuFile() nel tuo codice precedente era pubblico, lo chiamo.
        // Se nel tuo codice attuale è protected/private, devi modificare il libro e usare aggiungiLibro sovrascrivendo.

        LocalDate inizio = LocalDate.now();
        LocalDate scadenza = LocalDate.now().plusDays(30);

        assertThrows(ErroreNumeroCopieLibro.class, () -> {
            new Prestito(inizio, scadenza, MATRICOLA_VALIDA, ISBN_VALIDO);
        });
    }

    @Test
    @DisplayName("Caso Critico: Utente o Libro non trovati")
    void testEntitaNonTrovate() {
        LocalDate d = LocalDate.now();

        // ISBN inesistente
        assertThrows(LibroNonTrovatoException.class, () ->
                new Prestito(d, d.plusDays(30), MATRICOLA_VALIDA, "9780000000000")
        );

        // Matricola inesistente
        assertThrows(UtenteNonTrovatoException.class, () ->
                new Prestito(d, d.plusDays(30), "0600000000", ISBN_VALIDO)
        );
    }

    @Test
    @DisplayName("Caso Limite: Calcolo Penale con ritardo")
    void testCalcoloPenale() throws Exception {
        // Creo un prestito scaduto un mese fa
        LocalDate inizio = LocalDate.now().minusMonths(2);
        LocalDate scadenza = LocalDate.now().minusMonths(1); // Scaduto da 1 mese

        Prestito p = new Prestito(inizio, scadenza, MATRICOLA_VALIDA, ISBN_VALIDO);

        // Calcolo penale (oggi - scadenza = 1 mese)
        // La tua logica: mesi * 10
        int penale = p.calcolaPenale();

        // Verifica (Nota: ChronoUnit.MONTHS potrebbe arrotondare, verifichiamo che sia > 0)
        assertTrue(penale >= 10, "La penale dovrebbe essere almeno 10 euro per un mese di ritardo");
    }

    @Test
    @DisplayName("Caso Limite: Calcolo Penale senza ritardo (0)")
    void testCalcoloPenalePuntuale() throws Exception {
        LocalDate inizio = LocalDate.now();
        LocalDate scadenza = LocalDate.now().plusDays(30); // Scade in futuro

        Prestito p = new Prestito(inizio, scadenza, MATRICOLA_VALIDA, ISBN_VALIDO);

        assertEquals(0, p.calcolaPenale(), "Se non è scaduto, la penale deve essere 0");
    }
}
package org.biblioteca_gruppo_05.Gestione_Prestiti;

import org.biblioteca_gruppo_05.Eccezioni.PrestitoNonTrovatoException;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioPrestitiTest {

    private ArchivioPrestiti archivioPrestiti;
    private final String FILE_PRESTITI = "prestiti_test.bin";
    private final String FILE_LIBRI = "libri.bin";   // Hardcoded nella classe
    private final String FILE_PROFILI = "profili.bin"; // Hardcoded nella classe

    private final String ISBN = "9788812345678";
    private final String MATRICOLA = "0612345678";
    private Prestito prestitoValido;

    @BeforeEach
    void setUp() throws Exception {
        cleanFiles();

        // 1. Popolo Libri (1 copia)
        ArchivioLibri al = new ArchivioLibri(FILE_LIBRI);
        Libro l = new Libro("Java", "Autore", ISBN, LocalDate.now());
        l.setNumeroCopie(1);
        al.aggiungiLibro(l); // Salva su file

        // 2. Popolo Profili (0 prestiti)
        ArchivioProfili ap = new ArchivioProfili(FILE_PROFILI);
        Profilo p = new Profilo("Nome", "Cognome", MATRICOLA, "email@test.it");
        ap.aggiungiProfilo(p); // Salva su file

        // 3. Inizializzo Archivio Prestiti
        archivioPrestiti = new ArchivioPrestiti(FILE_PRESTITI);

        // 4. Creo un oggetto Prestito (legge dai file creati sopra)
        prestitoValido = new Prestito(LocalDate.now(), LocalDate.now().plusDays(30), MATRICOLA, ISBN);
    }

    @AfterEach
    void tearDown() {
        cleanFiles();
    }

    private void cleanFiles() {
        new File(FILE_PRESTITI).delete();
        new File(FILE_LIBRI).delete();
        new File(FILE_PROFILI).delete();
    }

    @Test
    @DisplayName("Registra Prestito: Verifica aggiornamento copie e profilo")
    void testRegistraPrestito() throws Exception {
        archivioPrestiti.registraPrestito(prestitoValido);

        // 1. Verifica che il prestito sia nell'archivio
        List<Prestito> trovati = archivioPrestiti.ricercaPrestitoPerMatricola(MATRICOLA);
        assertEquals(1, trovati.size());

        // 2. VERIFICA INTEGRITÀ: Le copie del libro sono diminuite?
        ArchivioLibri al = new ArchivioLibri(FILE_LIBRI); // Ricarico da file
        Libro l = al.ricercaLibroPerISBN(ISBN);
        assertEquals(0, l.getNumeroCopie(), "Registrando il prestito, le copie devono scendere a 0");

        // 3. VERIFICA INTEGRITÀ: I prestiti dell'utente sono aumentati?
        ArchivioProfili ap = new ArchivioProfili(FILE_PROFILI); // Ricarico da file
        Profilo p = ap.ricercaProfiloPerMatricola(MATRICOLA);
        assertEquals(1, p.getNumeroPrestiti(), "Registrando il prestito, il contatore utente deve salire a 1");
    }

    @Test
    @DisplayName("Restituzione Prestito: Ripristino stato")
    void testRestituzionePrestito() throws Exception {
        // Prima registro
        archivioPrestiti.registraPrestito(prestitoValido);

        // Poi restituisco
        archivioPrestiti.restituzionPrestito(prestitoValido);

        // 1. Verifica prestito rimosso
        assertThrows(PrestitoNonTrovatoException.class, () ->
                archivioPrestiti.ricercaPrestitoPerMatricola(MATRICOLA)
        );

        // 2. Copie tornate a 1?
        ArchivioLibri al = new ArchivioLibri(FILE_LIBRI);
        assertEquals(1, al.ricercaLibroPerISBN(ISBN).getNumeroCopie());

        // 3. Utente tornato a 0 prestiti?
        ArchivioProfili ap = new ArchivioProfili(FILE_PROFILI);
        assertEquals(0, ap.ricercaProfiloPerMatricola(MATRICOLA).getNumeroPrestiti());
    }

    @Test
    @DisplayName("Persistenza: Salvataggio e Caricamento")
    void testPersistenza() throws Exception {
        archivioPrestiti.registraPrestito(prestitoValido);

        // Ricarico archivio da zero
        ArchivioPrestiti nuovoArchivio = new ArchivioPrestiti(FILE_PRESTITI);

        List<Prestito> lista = nuovoArchivio.ricercaPrestitoPerISBN(ISBN);
        assertFalse(lista.isEmpty());
        assertEquals(prestitoValido.getId(), lista.get(0).getId());
    }

    @Test
    @DisplayName("Caso Limite: Ricerca Prestito Inesistente")
    void testRicercaFallita() {
        assertThrows(PrestitoNonTrovatoException.class, () ->
                archivioPrestiti.ricercaPrestitoPerISBN("9780000000000")
        );
        assertThrows(PrestitoNonTrovatoException.class, () ->
                archivioPrestiti.ricercaPrestitoPerMatricola("0699999999")
        );
    }
}
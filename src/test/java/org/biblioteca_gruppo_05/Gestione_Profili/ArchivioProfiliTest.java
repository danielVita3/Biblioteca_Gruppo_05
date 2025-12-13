package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Eccezioni.ErroreMatricolaException;
import org.biblioteca_gruppo_05.Eccezioni.ErroreLetturaFileException;
import org.biblioteca_gruppo_05.Eccezioni.UtenteEsitenteException;
import org.biblioteca_gruppo_05.Eccezioni.UtenteNonTrovatoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioProfiliTest {

    private ArchivioProfili archivio;
    private final String FILE_TEST = "archivioProfiliTest.bin";
    private Profilo p1;
    private Profilo p2;

    @BeforeEach
    void setUp() throws Exception {
        // 1. PULIZIA FILE (Per evitare conflitti tra test)
        File f = new File(FILE_TEST);
        if (f.exists()) {
            // Tentativo di eliminazione, altrimenti sovrascrivi
            if (!f.delete()) {
                new FileWriter(f).close();
            }
        }

        // 2. Inizializzazione Archivio
        archivio = new ArchivioProfili(FILE_TEST);

        // 3. Creazione Profili (Matricola DEVE iniziare con 06 ed essere lunga 10)
        // Assumo che la classe Profilo e il metodo 'controlloMatricola' funzionino
        p1 = new Profilo("Mario", "Rossi", "0612345678", "mario.rossi@email.com");
        p2 = new Profilo("Luigi", "Verdi", "0687654321", "luigi.verdi@email.com");
    }

    @AfterEach
    void tearDown() {
        File f = new File(FILE_TEST);
        if (f.exists()) {
            f.delete();
        }
    }

    // --- TEST BASE E PERSISTENZA (GIÀ ESISTENTI) ---

    @Test
    @DisplayName("Aggiunta Profilo e Ricerca per Matricola")
    void testAggiungiProfilo() throws Exception {
        archivio.aggiungiProfilo(p1);

        Profilo trovato = archivio.ricercaProfiloPerMatricola(p1.getMatricola());
        assertNotNull(trovato);
        assertEquals(p1.getNome(), trovato.getNome());
    }

    @Test
    @DisplayName("Aggiunta Duplicato: Eccezione")
    void testAggiungiProfiloDuplicato() throws Exception {
        archivio.aggiungiProfilo(p1);
        assertThrows(UtenteEsitenteException.class, () -> archivio.aggiungiProfilo(p1));
    }

    @Test
    @DisplayName("Rimuovi Profilo")
    void testRimuoviProfilo() throws Exception {
        archivio.aggiungiProfilo(p1);
        archivio.rimuoviProfilo(p1.getMatricola());

        assertThrows(UtenteNonTrovatoException.class, () -> {
            archivio.ricercaProfiloPerMatricola(p1.getMatricola());
        });
    }

    @Test
    @DisplayName("Ricerca per Nome (Lista)")
    void testRicercaPerNome() throws Exception {
        archivio.aggiungiProfilo(p1); // Mario
        // Aggiungo un altro Mario con matricola diversa (06...) valida
        Profilo p3 = new Profilo("Mario", "Bianchi", "0600000003", "mario.bianchi@email.com");
        archivio.aggiungiProfilo(p3);

        List<Profilo> risultati = archivio.ricercaProfiloPerNome("Mario");
        assertEquals(2, risultati.size());
    }

    @Test
    @DisplayName("Persistenza Dati")
    void testPersistenza() throws Exception {
        archivio.aggiungiProfilo(p1);

        // Ricarico da file
        ArchivioProfili archivioRicaricato = new ArchivioProfili(FILE_TEST);
        Profilo caricato = archivioRicaricato.ricercaProfiloPerMatricola(p1.getMatricola());

        assertNotNull(caricato);
        assertEquals(p1.getMail(), caricato.getMail());
    }

    @Test
    @DisplayName("File Corrotto: Fail-safe")
    void testFileCorrotto() throws IOException {
        try (FileWriter fw = new FileWriter(FILE_TEST)) {
            fw.write("DATI INVALIDI");
        }
        ArchivioProfili archivioCorrotto = new ArchivioProfili(FILE_TEST);
        // La lettura fallisce, quindi l'archivio interno è vuoto:
        assertThrows(UtenteNonTrovatoException.class, () -> archivioCorrotto.visualizzaProfili());
        assertDoesNotThrow(() -> archivioCorrotto.aggiungiProfilo(p1));
    }

    // --- TEST AGGIUNTIVI (FOCUS SU ECCEZIONI E RICERCA COGNOME) ---

    @Test
    @DisplayName("Ricerca per Matricola Inesistente: Eccezione")
    void testRicercaProfiloNonTrovato() throws ErroreMatricolaException {
        // Precondizione non rispettata: Utente non esiste
        // La chiamata alla ricerca lancia UtenteNonTrovatoException
        assertThrows(UtenteNonTrovatoException.class, () -> {
            archivio.ricercaProfiloPerMatricola("0699999999");
        });
    }

    @Test
    @DisplayName("Ricerca per Matricola non Valida: Eccezione")
    void testRicercaProfiloMatricolaNonValida() {
        // Precondizione non rispettata: La matricola non rispetta il formato atteso
        // Si aspetta che il controlloMatricola() interno lanci l'eccezione
        assertThrows(ErroreMatricolaException.class, () -> {
            archivio.ricercaProfiloPerMatricola("NON_VALIDO");
        });
    }

    @Test
    @DisplayName("Rimozione Profilo Inesistente: Eccezione")
    void testRimuoviProfiloNonTrovato() {
        // Precondizione non rispettata: Utente non esiste nell'archivio
        assertThrows(UtenteNonTrovatoException.class, () -> {
            archivio.rimuoviProfilo("0699999999");
        });
    }

    @Test
    @DisplayName("Ricerca per Cognome (Lista)")
    void testRicercaPerCognome() throws Exception {
        archivio.aggiungiProfilo(p1); // Rossi
        archivio.aggiungiProfilo(p2); // Verdi

        // Aggiungo un altro utente con cognome "Rossi" valido
        Profilo p4 = new Profilo("Luca", "Rossi", "0600000004", "luca.rossi@email.com");
        archivio.aggiungiProfilo(p4);

        List<Profilo> risultati = archivio.ricercaProfiloPerCognome("Rossi");
        assertEquals(2, risultati.size());
        assertEquals("Mario", risultati.get(0).getNome());
        assertEquals("Luca", risultati.get(1).getNome());
    }

    @Test
    @DisplayName("Ricerca per Cognome Inesistente: Eccezione")
    void testRicercaPerCognomeNonTrovato() {
        // Precondizione non rispettata: Nessun utente con questo cognome
        assertThrows(UtenteNonTrovatoException.class, () -> {
            archivio.ricercaProfiloPerCognome("Bianchi");
        });
    }

    @Test
    @DisplayName("Visualizza Profili: Archivio Pieno")
    void testVisualizzaProfiliPieno() throws Exception {
        archivio.aggiungiProfilo(p1);
        archivio.aggiungiProfilo(p2);

        List<Profilo> tutti = archivio.visualizzaProfili();
        // Postcondizione: Restituisce l'elenco completo
        assertEquals(2, tutti.size());
        assertTrue(tutti.contains(p1));
        assertTrue(tutti.contains(p2));
    }

    @Test
    @DisplayName("Visualizza Profili: Archivio Vuoto")
    void testVisualizzaProfiliVuoto() {
        // Precondizione non rispettata: L'archivio è stato appena inizializzato ed è vuoto
        assertThrows(UtenteNonTrovatoException.class, () -> archivio.visualizzaProfili());
    }
}
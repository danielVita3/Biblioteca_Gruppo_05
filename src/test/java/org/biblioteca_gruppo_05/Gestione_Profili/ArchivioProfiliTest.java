package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Eccezioni.*;
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
    private final String FILE_TEST = "archivio_profili_test.dat";
    private Profilo p1;
    private Profilo p2;

    @BeforeEach
    void setUp() throws Exception {
        // 1. PULIZIA FILE (Per evitare conflitti tra test)
        File f = new File(FILE_TEST);
        if (f.exists()) {
            if (!f.delete()) {
                new FileWriter(f).close();
            }
        }

        // 2. Inizializzazione Archivio
        archivio = new ArchivioProfili(FILE_TEST);

        // 3. Creazione Profili (Matricola DEVE iniziare con 06 ed essere lunga 10)
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
        assertThrows(UtenteNonTrovatoException.class, () -> archivioCorrotto.visualizzaProfili());
        assertDoesNotThrow(() -> archivioCorrotto.aggiungiProfilo(p1));
    }
}
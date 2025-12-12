package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Eccezioni.ErroreMatricolaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProfiloTest {

    // Matricola valida: 10 cifre, inizia con 06
    private final String MATRICOLA_VALIDA = "0612345678";
    private final String NOME = "Mario";
    private final String COGNOME = "Rossi";
    private final String EMAIL = "mario.rossi@studio.unibo.it";

    @Test
    @DisplayName("Costruttore: deve creare profilo valido con prestiti a 0")
    void testCostruttoreValido() throws ErroreMatricolaException {
        Profilo p = new Profilo(NOME, COGNOME, MATRICOLA_VALIDA, EMAIL);

        assertAll("Verifica attributi",
                () -> assertEquals(NOME, p.getNome()),
                () -> assertEquals(COGNOME, p.getCognome()),
                () -> assertEquals(MATRICOLA_VALIDA, p.getMatricola()),
                () -> assertEquals(EMAIL, p.getMail()),
                () -> assertEquals(0, p.getNumeroPrestiti(), "I prestiti iniziali devono essere 0")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890",   // Non inizia con 06
            "06123",        // Troppo corta
            "061234567899", // Troppo lunga
            "061234567A",   // Contiene lettere
            "",             // Vuota
            "          "    // Spazi
    })
    @DisplayName("Controllo Matricola: deve lanciare eccezione per formati errati")
    void testMatricolaInvalida(String matricolaErrata) {
        assertThrows(ErroreMatricolaException.class, () -> {
            new Profilo(NOME, COGNOME, matricolaErrata, EMAIL);
        });
    }

    @Test
    @DisplayName("Static Controllo Matricola: test diretto")
    void testStaticControlloMatricola() {
        assertDoesNotThrow(() -> Profilo.controlloMatricola("0600000001"));
        assertThrows(ErroreMatricolaException.class, () -> Profilo.controlloMatricola("0700000000"));
    }

    @Test
    @DisplayName("Setters: devono aggiornare lo stato")
    void testSetters() throws ErroreMatricolaException {
        Profilo p = new Profilo(NOME, COGNOME, MATRICOLA_VALIDA, EMAIL);

        p.setNome("Luigi");
        p.setMail("nuova@email.it");

        // Test setNumeroPrestiti (che nel tuo codice fa += prestito)
        p.setNumeroPrestiti(1); // Incrementa di 1

        assertAll(
                () -> assertEquals("Luigi", p.getNome()),
                () -> assertEquals("nuova@email.it", p.getMail()),
                () -> assertEquals(1, p.getNumeroPrestiti())
        );
    }

    @Test
    @DisplayName("Equals e HashCode: devono basarsi sulla matricola")
    void testEqualsHashCode() throws ErroreMatricolaException {
        Profilo p1 = new Profilo(NOME, COGNOME, MATRICOLA_VALIDA, EMAIL);
        Profilo p2 = new Profilo("Altro", "Tizio", MATRICOLA_VALIDA, "altro@email.it");
        Profilo p3 = new Profilo(NOME, COGNOME, "0699999999", EMAIL);

        // Se hai corretto il bug del ==, questo passerà
        assertEquals(p1, p2, "Utenti con stessa matricola devono essere uguali");
        assertNotEquals(p1, p3, "Utenti con matricola diversa devono essere diversi");

        assertEquals(p1.hashCode(), p2.hashCode(), "HashCode deve essere uguale per oggetti uguali");
    }

    @Test
    @DisplayName("CompareTo: ordinamento per matricola")
    void testCompareTo() throws ErroreMatricolaException {
        Profilo p1 = new Profilo(NOME, COGNOME, "0600000001", EMAIL);
        Profilo p2 = new Profilo(NOME, COGNOME, "0600000002", EMAIL);

        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p2.compareTo(p1) > 0);
    }
}
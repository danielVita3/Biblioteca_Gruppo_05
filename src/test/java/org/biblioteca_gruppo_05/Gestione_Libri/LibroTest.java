package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.ErroreISBNException;
import org.biblioteca_gruppo_05.Eccezioni.ErroreNumeroCopieLibro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    // Costanti per i test per evitare ripetizioni
    private final String ISBN_VALIDO = "9781234567890";
    private final String TITOLO = "Il Signore degli Anelli";
    private final String AUTORE = "J.R.R. Tolkien";
    private final LocalDate DATA = LocalDate.of(1954, 7, 29);

    @Test
    @DisplayName("Il costruttore deve creare un libro valido e inizializzare le copie a 1")
    void testCostruttoreValido() throws ErroreISBNException {
        Libro libro = new Libro(TITOLO, AUTORE, ISBN_VALIDO, DATA);

        assertAll("Verifica attributi libro",
                () -> assertEquals(TITOLO, libro.getTitolo()),
                () -> assertEquals(AUTORE, libro.getAutore()),
                () -> assertEquals(ISBN_VALIDO, libro.getISBN()),
                () -> assertEquals(DATA, libro.getDataPubblicazione()),
                () -> assertEquals(1, libro.getNumeroCopie(), "Il numero di copie predefinito deve essere 1")
        );
    }

    @Test
    @DisplayName("Il costruttore deve lanciare eccezione se ISBN è invalido")
    void testCostruttoreConISBNInvalido() {
        String isbnErrato = "123"; // Troppo corto

        ErroreISBNException exception = assertThrows(ErroreISBNException.class, () -> {
            new Libro(TITOLO, AUTORE, isbnErrato, DATA);
        });

        // Opzionale: verificare che il messaggio dell'errore contenga dettagli utili
        assertTrue(exception.getMessage().contains("Lunghezza non valida"));
    }

    @Test
    @DisplayName("setNumeroCopie deve lanciare eccezione se il valore è <= 0")
    void testSetNumeroCopieErrore() throws ErroreISBNException {
        Libro libro = new Libro(TITOLO, AUTORE, ISBN_VALIDO, DATA);

        assertThrows(ErroreNumeroCopieLibro.class, () -> {
            libro.setNumeroCopie(0);
        });

        assertThrows(ErroreNumeroCopieLibro.class, () -> {
            libro.setNumeroCopie(-5);
        });
    }

    @Test
    @DisplayName("Incrementa e Decrementa copie devono funzionare correttamente")
    void testGestioneCopie() throws ErroreISBNException, ErroreNumeroCopieLibro {
        Libro libro = new Libro(TITOLO, AUTORE, ISBN_VALIDO, DATA);

        // Test Incremento
        libro.incrementaNumCopie();
        assertEquals(2, libro.getNumeroCopie());

        // Test Decremento
        libro.decrementaNumeroCopie();
        assertEquals(1, libro.getNumeroCopie());

        // Test Set diretto
        libro.setNumeroCopie(10);
        assertEquals(10, libro.getNumeroCopie());
    }

    // Qui usiamo @ParameterizedTest per provare tanti ISBN diversi velocemente
    // Se non ti funziona, assicurati di aver risolto il problema della dipendenza 'junit-jupiter-params'
    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890123",  // Prefisso errato (non 978/979)
            "978123456789A",  // Lettera invece di numero
            "978123",         // Corto
            "",               // Vuoto
            "   "             // Spazi
    })
    @DisplayName("Il controllo ISBN statico deve rifiutare formati non validi")
    void testControllaISBNInvalidi(String isbnSbagliato) {
        assertThrows(ErroreISBNException.class, () -> {
            Libro.controllaISBN(isbnSbagliato);
        });
    }

    @Test
    @DisplayName("Il controllo ISBN deve lanciare eccezione se nullo")
    void testControllaISBNNull() {
        assertThrows(ErroreISBNException.class, () -> Libro.controllaISBN(null));
    }

    @Test
    @DisplayName("Equals deve basarsi solo sull'ISBN")
    void testEquals() throws ErroreISBNException {
        Libro libro1 = new Libro("Titolo A", "Autore A", ISBN_VALIDO, DATA);
        // Libro diverso per titolo/autore ma stesso ISBN
        Libro libro2 = new Libro("Titolo B", "Autore B", ISBN_VALIDO, LocalDate.now());
        // Libro con ISBN diverso
        Libro libro3 = new Libro("Titolo A", "Autore A", "9791234567890", DATA);

        assertEquals(libro1, libro2, "Due libri con stesso ISBN devono essere uguali");
        assertNotEquals(libro1, libro3, "Due libri con ISBN diverso devono essere diversi");
    }

    @Test
    @DisplayName("CompareTo deve ordinare in base all'ISBN")
    void testCompareTo() throws ErroreISBNException {
        Libro l1 = new Libro(TITOLO, AUTORE, "9780000000001", DATA);
        Libro l2 = new Libro(TITOLO, AUTORE, "9780000000002", DATA);

        // l1 viene prima di l2 (risultato negativo)
        assertTrue(l1.compareTo(l2) < 0);
        // l2 viene dopo l1 (risultato positivo)
        assertTrue(l2.compareTo(l1) > 0);
    }
}
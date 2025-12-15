package org.biblioteca_gruppo_05.Gestione_Libri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.biblioteca_gruppo_05.Eccezioni.ErroreISBNException;
import org.biblioteca_gruppo_05.Eccezioni.LibroEsistenteException;
import org.biblioteca_gruppo_05.Eccezioni.LibroNonTrovatoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.Test; // CORRETTO per JUnit 5
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import javafx.application.Platform;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibriControllerTest {

    private LibriControllerTestable controller;
    private ArchivioLibri archivioMock;

    // Componenti UI simulati (presi dai tuoi FXML)
    private TextField addTitoloField;
    private TextField addAutoreField;
    private TextField addIsbnField;
    private DatePicker dataPubblicazionePicker;
    private TextField searchIsbnField;
    private TableView<Libro> tableLibri;
    private Label errorLabel;

    // Per i test di ricerca/visualizzazione
    private TableView<Libro> tableLibriVisualizza;
    private TextField searchLibriField;
    private ComboBox<String> filtroLibriCombo;

    @BeforeAll
    static void initJavaFX() {
        // Avvia il toolkit JavaFX senza usare JFXPanel (che causa errori su Java 17+)
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Se il toolkit è già stato avviato da un test precedente, ignoriamo l'errore.
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        // 1. Creiamo un Archivio finto (Mock)
        archivioMock = mock(ArchivioLibri.class);

        // 2. Creiamo il controller "speciale" per i test
        controller = new LibriControllerTestable(archivioMock);

        // 3. Inizializziamo i componenti grafici (come se FXML li avesse caricati)
        addTitoloField = new TextField();
        addAutoreField = new TextField();
        addIsbnField = new TextField();
        dataPubblicazionePicker = new DatePicker();
        searchIsbnField = new TextField();
        tableLibri = new TableView<>();
        errorLabel = new Label();

        tableLibriVisualizza = new TableView<>();
        searchLibriField = new TextField();
        filtroLibriCombo = new ComboBox<>();
        filtroLibriCombo.setItems(FXCollections.observableArrayList("ISBN", "Titolo", "Autore"));
        filtroLibriCombo.getSelectionModel().selectFirst(); // Seleziona "ISBN" di default

        // 4. INIEZIONE: Inseriamo i componenti nel controller privato usando la Reflection
        injectField("addTitoloField", addTitoloField);
        injectField("addAutoreField", addAutoreField);
        injectField("addIsbnField", addIsbnField);
        injectField("dataPubblicazionePicker", dataPubblicazionePicker);
        injectField("searchIsbnField", searchIsbnField);
        injectField("tableLibri", tableLibri);
        injectField("errorLabel", errorLabel);

        injectField("tableLibriVisualizza", tableLibriVisualizza);
        injectField("searchLibriField", searchLibriField);
        injectField("filtroLibriCombo", filtroLibriCombo);
    }

    // ==========================================
    // TEST 1: AGGIUNTA LIBRO (Aggiungi-libro.fxml)
    // ==========================================

    @Test
    @DisplayName("Aggiungi Libro: Successo")
    void testAggiungiLibroSuccesso() throws Exception {
        // SETUP: Riempiamo i campi
        addTitoloField.setText("Java Programming");
        addAutoreField.setText("Mario Rossi");
        addIsbnField.setText("9788812345678");
        dataPubblicazionePicker.setValue(LocalDate.of(2023, 1, 1));

        // EXECUTE: Chiamiamo il metodo privato
        invokePrivateMethod("handleConfermaAggiunta");

        // VERIFY: Controlliamo che l'archivio abbia ricevuto il comando giusto
        ArgumentCaptor<Libro> captor = ArgumentCaptor.forClass(Libro.class);
        verify(archivioMock).aggiungiLibro(captor.capture());

        Libro libroSalvato = captor.getValue();
        assertEquals("Java Programming", libroSalvato.getTitolo());
        assertEquals("9788812345678", libroSalvato.getISBN());

        // Controlliamo che sia apparso l'Alert di successo
        assertEquals(Alert.AlertType.INFORMATION, controller.lastAlertType);
        assertEquals("Successo", controller.lastAlertTitle);

        // Controlliamo che i campi siano stati puliti
        assertTrue(addTitoloField.getText().isEmpty());
    }

    @Test
    @DisplayName("Aggiungi Libro: Fallimento (Campi vuoti)")
    void testAggiungiLibroCampiVuoti() throws Exception {
        addTitoloField.setText(""); // Vuoto

        invokePrivateMethod("handleConfermaAggiunta");

        // Verify: NON deve chiamare l'archivio
        verify(archivioMock, never()).aggiungiLibro(any());

        // Verify: Deve mostrare errore
        assertEquals(Alert.AlertType.ERROR, controller.lastAlertType);
        assertTrue(controller.lastAlertContent.contains("Devi inserire"));
    }

    // ==========================================
    // TEST 2: CERCA LIBRO (Per Modifica/Elimina)
    // ==========================================

    @Test
    @DisplayName("Cerca Libro per ISBN: Trovato")
    void testCercaLibroTrovato() throws Exception {
        // MOCK: Diciamo all'archivio cosa restituire
        Libro libroFinto = new Libro("Test", "Autore", "9781234567890", LocalDate.now());
        when(archivioMock.ricercaLibroPerISBN("9781234567890")).thenReturn(libroFinto);

        // SETUP
        searchIsbnField.setText("9781234567890");

        // EXECUTE
        invokePrivateMethod("handleCercaLibro");

        // VERIFY: La tabella deve contenere il libro
        assertFalse(tableLibri.getItems().isEmpty());
        assertEquals(libroFinto, tableLibri.getItems().get(0));
        assertEquals(Alert.AlertType.INFORMATION, controller.lastAlertType);
    }

    @Test
    @DisplayName("Cerca Libro per ISBN: Non Trovato")
    void testCercaLibroNonTrovato() throws Exception {
        // MOCK: Lancia eccezione
        when(archivioMock.ricercaLibroPerISBN(anyString()))
                .thenThrow(new LibroNonTrovatoException("Non trovato"));

        searchIsbnField.setText("9780000000000");

        invokePrivateMethod("handleCercaLibro");

        // VERIFY: Tabella vuota e Alert Warning
        assertTrue(tableLibri.getItems().isEmpty());
        assertEquals(Alert.AlertType.WARNING, controller.lastAlertType);
    }

    // ==========================================
    // TEST 3: ELIMINA LIBRO (Elimina-Libro.fxml)
    // ==========================================

    @Test
    @DisplayName("Elimina Libro: Successo")
    void testEliminaLibro() throws Exception {
        searchIsbnField.setText("9781234567890");

        invokePrivateMethod("handleConfermaElimina");

        verify(archivioMock).rimuoviLibro("9781234567890");
        assertEquals(Alert.AlertType.INFORMATION, controller.lastAlertType);
    }

    // ==========================================
    // TEST 4: RICERCA E VISUALIZZA (Ricerca-Visualizzazione.fxml)
    // ==========================================

    @Test
    @DisplayName("Ricerca Visualizza: Per Titolo")
    void testCercaVisualizzaPerTitolo() throws Exception {
        // Setup Combo su "Titolo"
        filtroLibriCombo.setValue("Titolo");
        searchLibriField.setText("Harry Potter");

        invokePrivateMethod("handleCercaVisualizza");

        // Verifica chiamata all'archivio
        verify(archivioMock).ricercaLibriPerTitolo("Harry Potter");
    }

    // ==========================================
    // UTILITIES (Reflection & Mocking)
    // ==========================================

    /**
     * Sottoclasse del controller per intercettare gli Alert
     * senza aprire finestre vere che bloccherebbero il test.
     */
    static class LibriControllerTestable extends LibriController {
        Alert.AlertType lastAlertType;
        String lastAlertTitle;
        String lastAlertContent;

        public LibriControllerTestable(ArchivioLibri archivio) {
            super(archivio);
        }


        protected void showAlert(Alert.AlertType type, String title, String header, String content) {
            this.lastAlertType = type;
            this.lastAlertTitle = title;
            this.lastAlertContent = content;
        }
    }

    /**
     * Inietta un oggetto in un campo privato del controller usando la Reflection.
     */
    private void injectField(String fieldName, Object value) throws Exception {
        // Cerca il campo nella classe LibriController
        Field field = LibriController.class.getDeclaredField(fieldName);
        field.setAccessible(true); // Rende il campo privato accessibile
        field.set(controller, value); // Imposta il valore
    }

    /**
     * Invoca un metodo privato del controller (es. i metodi @FXML)
     */
    private void invokePrivateMethod(String methodName) throws Exception {
        Method method = LibriController.class.getDeclaredMethod(methodName, ActionEvent.class);
        method.setAccessible(true); // Rende il metodo privato accessibile
        method.invoke(controller, (ActionEvent) null); // Chiama il metodo passando un evento null
    }
}
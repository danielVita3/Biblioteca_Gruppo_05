package org.biblioteca_gruppo_05.Gestione_Libri;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LibriController implements Initializable {

    private ArchivioLibri archivioLibri;

    @FXML private TextField addTitoloField;
    @FXML private TextField addAutoreField;
    @FXML private TextField addIsbnField;
    @FXML private DatePicker dataPubblicazionePicker;

    @FXML private TextField searchIsbnField;
    @FXML private Label errorLabel;
    @FXML private VBox previewContainer;
    @FXML private Label lblTitolo;
    @FXML private Label lblAutore;
    @FXML private Label lblCopie;

    @FXML private VBox searchContainer;
    @FXML private VBox editFormContainer;
    @FXML private Button btnCerca;
    @FXML private TextField titoloField;
    @FXML private TextField autoreField;
    @FXML private TextField annoField;
    @FXML private TextField isbnDisplayField;
    @FXML private Spinner<Integer> copieSpinner;

    @FXML private ComboBox<String> filtroLibriCombo;
    @FXML private TextField searchLibriField;
    @FXML private TableView<Object> tableLibri;

    public LibriController(ArchivioLibri manager) {
        this.archivioLibri = manager;
    }

    @FXML private void handleIndietro(ActionEvent event) {}
    @FXML private void handleVaiARicerca(ActionEvent event) {}
    @FXML private void handleVaiAAggiungi(ActionEvent event) {}
    @FXML private void handleVaiAModifica(ActionEvent event) {}
    @FXML private void handleVaiAElimina(ActionEvent event) {}
    @FXML private void handlePulisciCampi(ActionEvent event) {}
    @FXML private void handleConfermaAggiunta(ActionEvent event) {}
    @FXML private void handleCercaLibro(ActionEvent event) {}
    @FXML private void handleAnnulla(ActionEvent event) {}
    @FXML private void handleConfermaElimina(ActionEvent event) {}
    @FXML private void handleSalvaModifiche(ActionEvent event) {}
    @FXML private void handleResetLibri(ActionEvent event) {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
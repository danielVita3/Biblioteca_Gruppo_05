
package org.biblioteca_gruppo_05.Gestione_Profili;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;


    public class ProfiliController implements Initializable {

        private final ArchivioProfili archivioProfili;

        @FXML private TextField nomeField;
        @FXML private TextField cognomeField;
        @FXML private TextField matricolaField;
        @FXML private TextField emailField;

        @FXML private ComboBox<String> filtroUtentiCombo;
        @FXML private TextField searchUtentiField;
        @FXML private TableView tableUtenti;

        @FXML private TextField searchMatricolaField;

        @FXML private VBox editFormContainer;
        @FXML private TextField editMatricolaField;
        @FXML private TextField editNomeField;
        @FXML private TextField editCognomeField;
        @FXML private TextField editEmailField;
        @FXML private TextField storicoField;

        @FXML private VBox confirmContainer;
        @FXML private Label lblNomeCognome;
        @FXML private Label lblMatricola;

        public ProfiliController(ArchivioProfili manager) {
            this.archivioProfili = manager;
        }

        @FXML private void handleIndietro(ActionEvent event) {}
        @FXML private void handleVaiARicercaProfili(ActionEvent event) {}
        @FXML private void handleVaiAAggiungiProfilo(ActionEvent event) {}
        @FXML private void handleVaiAModificaProfilo(ActionEvent event) {}
        @FXML private void handleVaiAEliminaProfilo(ActionEvent event) {}

        @FXML private void handleCercaUtente(ActionEvent event) {}
        @FXML private void handleCercaPerEliminare(ActionEvent event) {}
        @FXML private void handleResetUtenti(ActionEvent event) {}

        @FXML private void handlePulisci(ActionEvent event) {}
        @FXML private void handleAnnulla(ActionEvent event) {}
        @FXML private void handleSalvaProfilo(ActionEvent event) {}
        @FXML private void handleSalvaModifiche(ActionEvent event) {}
        @FXML private void handleConfermaElimina(ActionEvent event) {}

        @Override
        public void initialize(URL url, ResourceBundle rb) {
        }
    }


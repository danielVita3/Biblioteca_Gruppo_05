
package org.biblioteca_gruppo_05.Gestione_Profili;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


    public class ProfiliController implements Initializable {

        private ArchivioProfili archivioProfili;

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
        public ProfiliController(){

        }
        public ProfiliController(ArchivioProfili manager) {
            this.archivioProfili = manager;
        }
        @FXML private void handleTornaHomePage(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.centerOnScreen();
            stage.setTitle("Home Page!");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
        @FXML private void handleIndietro(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Profili_View/Profili.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.centerOnScreen();
            stage.setTitle("Gestione Archivio Profili!");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
        @FXML private void handleVaiARicercaProfili(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Profili_View/Ricerca-Visualizzazione-Profili.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.centerOnScreen();
            stage.setTitle("Ricerca e visualizza profili!");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
        @FXML private void handleVaiAAggiungiProfilo(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Profili_View/Aggiungi-Profilo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.centerOnScreen();
            stage.setTitle("Aggiungi profilo!");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
        @FXML private void handleVaiAModificaProfilo(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Profili_View/Modifica-Profilo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.centerOnScreen();
            stage.setTitle("Modifica profilo!");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
        @FXML private void handleVaiAEliminaProfilo(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Profili_View/Elimina-Profilo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.centerOnScreen();
            stage.setTitle("Elimina profilo!");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }

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


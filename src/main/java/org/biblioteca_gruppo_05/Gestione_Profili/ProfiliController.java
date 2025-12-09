
package org.biblioteca_gruppo_05.Gestione_Profili;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
            switchScene(event,"/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml");
        }
        @FXML private void handleIndietro(ActionEvent event) throws IOException{
            switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Profili_View/Profili.fxml");
        }
        @FXML private void handleVaiARicercaProfili(ActionEvent event) throws IOException{
            switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Profili_View/Ricerca-Visualizzazione-Profili.fxml");
        }
        @FXML private void handleVaiAAggiungiProfilo(ActionEvent event) throws IOException{
            switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Profili_View/Aggiungi-Profilo.fxml");
        }
        @FXML private void handleVaiAModificaProfilo(ActionEvent event) throws IOException{
            switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Profili_View/Modifica-Profilo.fxml");
        }
        @FXML private void handleVaiAEliminaProfilo(ActionEvent event) throws IOException{
            switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Profili_View/Elimina-Profilo.fxml");
        }

        @FXML private void handleCercaUtente(ActionEvent event) {}
        @FXML private void handleCercaPerEliminare(ActionEvent event) {}
        @FXML private void handleResetUtenti(ActionEvent event) {}

        @FXML private void handlePulisci(ActionEvent event) {}
        @FXML private void handleAnnulla(ActionEvent event) {}
        @FXML private void handleSalvaProfilo(ActionEvent event) {}
        @FXML private void handleSalvaModifiche(ActionEvent event) {}
        @FXML private void handleConfermaElimina(ActionEvent event) {}
        private void showAlert(Alert.AlertType type, String title, String header, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
        private void switchScene(ActionEvent event,String fxmlPath){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root=loader.load();
                Scene stageAttuale=((Node) event.getSource()).getScene();
                stageAttuale.setRoot(root);
            }catch(IOException e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,"Errore Critico!","Errore nel caricamento della scena",e.getMessage());
            }
        }
        @Override
        public void initialize(URL url, ResourceBundle rb) {
        }
    }


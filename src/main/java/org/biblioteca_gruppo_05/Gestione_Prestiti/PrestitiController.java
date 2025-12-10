package org.biblioteca_gruppo_05.Gestione_Prestiti;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import org.biblioteca_gruppo_05.Eccezioni.*;
import org.biblioteca_gruppo_05.Gestione_Libri.*;
import org.biblioteca_gruppo_05.Gestione_Profili.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrestitiController implements Initializable {

    private  ArchivioPrestiti archivioPrestiti;
    private  ArchivioLibri archivioLibri;
    private  ArchivioProfili archivioProfili;

    @FXML private TextField prestitoMatricolaField;
    @FXML private TextField prestitoIsbnField;
    @FXML private DatePicker dataInizioPicker;
    @FXML private DatePicker dataFinePicker;

    @FXML private TextField searchPrestitoField;
    @FXML private TableView tablePrestitiAttivi;

    @FXML private ComboBox<String> filtroVisualizzazioneCombo;
    @FXML private TextField searchVisualizzazioneField;
    @FXML private TableView tableVisualizzazione;
    public PrestitiController(){

    }
    public PrestitiController(ArchivioPrestiti archivioPrestiti, ArchivioLibri archivioLibri, ArchivioProfili archivioProfili) {
        this.archivioPrestiti = archivioPrestiti;
        this.archivioLibri = archivioLibri;
        this.archivioProfili = archivioProfili;
    }
    private boolean isInputValido(){
        String errorMessage="";
        if(prestitoMatricolaField.getText()==null || prestitoMatricolaField.getText().trim().isEmpty()){
            errorMessage+="Devi inserire la matricola\n";
        }
        if(prestitoIsbnField.getText()==null || prestitoIsbnField.getText().trim().isEmpty()){
            errorMessage+="Devi inserire l'ISBN\n";
        }
        if(dataInizioPicker.getValue()==null ){
            errorMessage+="Devi inserire una data di prestito\n";
        }
        if(dataFinePicker.getValue()==null){
            errorMessage+="Devi inserire la data di restituzione\n";
        }
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Errore di Validazione", "Correggi i seguenti campi:", errorMessage);
            return false;
        }
    }
    @FXML private void handleIndietro(ActionEvent event) throws IOException {
        switchScene(event,"/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml");
    }

    @FXML private void handlePulisciCampi(ActionEvent event) {
        prestitoMatricolaField.setText("");
        prestitoIsbnField.setText("");
        dataInizioPicker.setValue(null);
        dataFinePicker.setValue(null);
    }

    @FXML private void handleConfermaPrestito(ActionEvent event) {
        if (isInputValido()) {
            try {
                Prestito p = new Prestito(dataInizioPicker.getValue(),dataFinePicker.getValue(),prestitoMatricolaField.getText(), prestitoIsbnField.getText());
                archivioPrestiti.registraPrestito(p);
                showAlert(Alert.AlertType.INFORMATION, "Successo", "Prestito Aggiunto", "Il prestito è stato aggiunto all'archivio.");
                handlePulisciCampi(null);
            } catch (ErroreISBNException e) {
                showAlert(Alert.AlertType.ERROR, "Errore ISBN", "Formato ISBN non valido.", e.getMessage());
            } catch (ErroreMatricolaException e) {
                showAlert(Alert.AlertType.WARNING, "Errore di Duplicazione", "Libro già esistente", e.getMessage());
            } catch (LibroNonTrovatoException e) {
                showAlert(Alert.AlertType.WARNING, "Libro non esistente", "Libro non presente in archivio", e.getMessage());
            } catch (UtenteNonTrovatoException e) {
                showAlert(Alert.AlertType.WARNING, "Utente non iscritto", "Utente non presente in archivio", e.getMessage());
            } catch (ErroreNumeroCopieLibro e) {
                showAlert(Alert.AlertType.WARNING, "Numero copie non disponibile", "Numero copie non disponibili", e.getMessage());
            }
        }
    }

    @FXML private void handleRegistraRestituzione(ActionEvent event) {}

    @FXML private void handleCercaVisualizzazione(ActionEvent event) {}
    @FXML private void handleResetVisualizzazione(ActionEvent event) {}

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

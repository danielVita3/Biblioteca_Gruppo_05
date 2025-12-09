package org.biblioteca_gruppo_05.Gestione_Libri;

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
import org.biblioteca_gruppo_05.Eccezioni.ErroreISBNException;
import org.biblioteca_gruppo_05.Eccezioni.LibroEsistenteException;

import java.io.IOException;
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
    public LibriController(){

    }
    public LibriController(ArchivioLibri manager) {
        this.archivioLibri = manager;
    }
    @FXML private void handleTornaHomePage(ActionEvent event) throws IOException {
        switchScene(event,"/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml");
    }
    @FXML private void handleIndietro(ActionEvent event) throws IOException {
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Libri_View/Archivio.fxml");
    }
    @FXML private void handleVaiARicerca(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Libri_View/Ricerca-Visualizzazione-Libri.fxml");
    }
    @FXML private void handleVaiAAggiungi(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Libri_View/Aggiungi-libro.fxml");
    }
    @FXML private void handleVaiAModifica(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Libri_View/Modifica-Libro.fxml");
    }
    @FXML private void handleVaiAElimina(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Libri_View/Elimina-Libro.fxml");
    }
    private boolean isInputValido(){
        String errorMessage="";
        if(addAutoreField.getText()==null || addAutoreField.getText().trim().isEmpty()){
            errorMessage+="Devi inserire l'autore\n";
        }
        if(addTitoloField.getText()==null || addTitoloField.getText().trim().isEmpty()){
            errorMessage+="Devi inserire il titoolo\n";
        }
        if(addIsbnField.getText()==null || addIsbnField.getText().trim().isEmpty()){
            errorMessage+="Devi inserire l'ISBN\n";
        }
        if(dataPubblicazionePicker.getValue()==null){
            errorMessage+="Devi inserire la data\n";
        }
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Errore di Validazione", "Correggi i seguenti campi:", errorMessage);
            return false;
        }
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML private void handlePulisciCampi(ActionEvent event) {
        addTitoloField.setText("");
        addAutoreField.setText("");
        addIsbnField.setText("");
        dataPubblicazionePicker.setValue(null);
    }
    @FXML private void handleConfermaAggiunta(ActionEvent event) {
        if(isInputValido()){
            try{
                Libro l=new Libro(addTitoloField.getText(),addAutoreField.getText(),addIsbnField.getText(),dataPubblicazionePicker.getValue());
                archivioLibri.aggiungiLibro(l);
                showAlert(Alert.AlertType.INFORMATION, "Successo", "Libro Aggiunto", "Il libro è stato aggiunto all'archivio.");
                handlePulisciCampi(null);
            } catch (ErroreISBNException e) {
                showAlert(Alert.AlertType.ERROR, "Errore ISBN", "Formato ISBN non valido.", e.getMessage());
            } catch (LibroEsistenteException e) {
                showAlert(Alert.AlertType.WARNING, "Errore di Duplicazione", "Libro già esistente", e.getMessage());
            }catch (Exception e ){
                showAlert(Alert.AlertType.ERROR, "Errore di Sistema", "Impossibile salvare il libro.", e.getMessage());
            }
        }


    }
    @FXML private void handleCercaLibro(ActionEvent event) {}
    @FXML private void handleAnnulla(ActionEvent event) {}
    @FXML private void handleConfermaElimina(ActionEvent event) {}
    @FXML private void handleSalvaModifiche(ActionEvent event) {}
    @FXML private void handleResetLibri(ActionEvent event) {}
    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == LibriController.class) {
                    return new LibriController(this.archivioLibri);
                }
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Impossibile creare il controller: " + controllerClass.getName(), e);
                }
            });

            Parent root = loader.load();
            Scene stageAttuale = ((Node) event.getSource()).getScene();

            Stage stage = (Stage) stageAttuale.getWindow();
            stage.centerOnScreen();
            stage.setMaximized(true);

            stageAttuale.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore Critico!", "Errore nel caricamento della scena: ", e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
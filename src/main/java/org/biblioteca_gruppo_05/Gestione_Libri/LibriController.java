package org.biblioteca_gruppo_05.Gestione_Libri;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Home Page!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML private void handleIndietro(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Libri_View/Archivio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Gestione Archivio Libri!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML private void handleVaiARicerca(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Libri_View/Ricerca-Visualizzazione-Libri.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Ricerca e Visualizza Libri!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML private void handleVaiAAggiungi(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Libri_View/Aggiungi-libro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Aggiungi libro!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML private void handleVaiAModifica(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Libri_View/Modifica-Libro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Modifica libro!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML private void handleVaiAElimina(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Libri_View/Elimina-Libro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Elimina libro!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    private boolean isInputValido(){
        String errorMessage="";
        if(addAutoreField.getText()==null || addAutoreField.getText().trim().isEmpty()){
            errorMessage+="Devi inserire l'autore\n";
        }
        if(errorMessage=="")
            return true;
        else
            return false;
    }
    @FXML private void handlePulisciCampi(ActionEvent event) {
        addTitoloField.setText("");
        addAutoreField.setText("");
        addIsbnField.setText("");
        dataPubblicazionePicker.setValue(null);
    }
    @FXML private void handleConfermaAggiunta(ActionEvent event) {
        Libro l=new Libro(addTitoloField.getText(),addAutoreField.getText(),addIsbnField.getText(),dataPubblicazionePicker.getValue());
    }
    @FXML private void handleCercaLibro(ActionEvent event) {}
    @FXML private void handleAnnulla(ActionEvent event) {}
    @FXML private void handleConfermaElimina(ActionEvent event) {}
    @FXML private void handleSalvaModifiche(ActionEvent event) {}
    @FXML private void handleResetLibri(ActionEvent event) {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
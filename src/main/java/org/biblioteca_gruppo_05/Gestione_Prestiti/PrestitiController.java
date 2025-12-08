package org.biblioteca_gruppo_05.Gestione_Prestiti;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
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

    @FXML private void handleIndietro(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Home Page!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void handleConfermaPrestito(ActionEvent event) {}

    @FXML private void handleRegistraRestituzione(ActionEvent event) {}

    @FXML private void handleCercaVisualizzazione(ActionEvent event) {}
    @FXML private void handleResetVisualizzazione(ActionEvent event) {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

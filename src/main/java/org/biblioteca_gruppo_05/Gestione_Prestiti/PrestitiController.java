package org.biblioteca_gruppo_05.Gestione_Prestiti;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.biblioteca_gruppo_05.Eccezioni.*;
import org.biblioteca_gruppo_05.Gestione_Libri.*;
import org.biblioteca_gruppo_05.Gestione_Profili.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PrestitiController implements Initializable {

    private  ArchivioPrestiti archivioPrestiti;
    private  ArchivioLibri archivioLibri;
    private  ArchivioProfili archivioProfili;

    @FXML private TextField prestitoMatricolaField;
    @FXML private TextField prestitoIsbnField;
    @FXML private DatePicker dataInizioPicker;
    @FXML private DatePicker dataFinePicker;
    @FXML private TableColumn<Prestito,String> colMatricola;
    @FXML private TableColumn<Prestito,String> colIsbn;
    @FXML private TableColumn<Prestito, LocalDate> colDataInizio;
    @FXML private TableColumn<Prestito,LocalDate> colScadenza;

    @FXML private TextField searchPrestitoField;
    @FXML private TableView <Prestito> tablePrestitiAttivi;
    @FXML private ComboBox<String> filtroPrestitiCombo;
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
            LocalDate dataInizio = dataInizioPicker.getValue();
            LocalDate dataFine = dataFinePicker.getValue();

            if (dataFine.isBefore(dataInizio)) {
                showAlert(Alert.AlertType.ERROR, "Errore di Validazione Data", "Data di scadenza non valida.", "La data di restituzione non può essere precedente alla data di inizio prestito.");
                return;
            }
            try {
                Prestito p = new Prestito(dataInizioPicker.getValue(),dataFinePicker.getValue(),prestitoMatricolaField.getText(), prestitoIsbnField.getText());
                archivioPrestiti.registraPrestito(p);
                refreshPrestitiTable();
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

    @FXML private void handleRegistraRestituzione(ActionEvent event) {
        Prestito p = (Prestito) tablePrestitiAttivi.getSelectionModel().getSelectedItem();

        if(p == null){
            showAlert(Alert.AlertType.WARNING, "Prestito non selezionato", "Seleziona un Prestito", "Devi selezionare un prestito dalla tabella.");
            return;
        }

        try {
            int penale = p.calcolaPenale();

            if(penale > 0){
                showAlert(Alert.AlertType.WARNING, "Penale Dovuta", "Pagare penale", "L'utente deve pagare una penale di: " + penale + " €");
            }

            archivioPrestiti.restituzionPrestito(p);

            tablePrestitiAttivi.getItems().remove(p);

            showAlert(Alert.AlertType.INFORMATION, "Restituzione Registrata", "Successo", "Il prestito è stato registrato come restituito.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore di Sistema", "Impossibile registrare la restituzione.", e.getMessage());
        }
    }

    @FXML private void handleModifica(ActionEvent event){
        try {
            archivioPrestiti.salvaSuFile();
        }catch(ErroreScritturaFileException e){
            showAlert(Alert.AlertType.ERROR, "Salvataggio fallito", "Errore di scrittura sul file.", e.getMessage());

        }
    }
    @FXML private void handleCerca(ActionEvent event){
        tablePrestitiAttivi.getItems().clear();

        String criterio = filtroPrestitiCombo.getValue(); // "ISBN", "Titolo"
        String query = searchPrestitoField.getText().trim();

        if (query.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attenzione", "Campo vuoto", "Inserisci un termine di ricerca.");
            return;
        }

        try{
            ObservableList<Prestito> risultati= FXCollections.observableArrayList();
            switch(criterio){
                case "ISBN":
                    risultati = FXCollections.observableArrayList( archivioPrestiti.ricercaPrestitoPerISBN(query));
                    break;
                case "Matricola":
                    risultati = FXCollections.observableArrayList( archivioPrestiti.ricercaPrestitoPerMatricola(query));
                    break;
            }
            tablePrestitiAttivi.setItems(risultati);

        }catch(PrestitoNonTrovatoException e){
            showAlert(Alert.AlertType.WARNING,"Attenzione", "Libri non trovati", e.getMessage());
        }
    }

    @FXML private void handleResetPrestiti(ActionEvent event) {
        try{
            List<Prestito> tuttiPrestiti = archivioPrestiti.visualizzaPrestiti();
            ObservableList<Prestito> l = FXCollections.observableArrayList(tuttiPrestiti);
            tablePrestitiAttivi.setItems(l);
        } catch (PrestitoNonTrovatoException e){
            showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Prestito non presente nell'archivio.", e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
        }
    }
    private void refreshPrestitiTable() {
        if (tablePrestitiAttivi != null) {
            try{
                List<Prestito> tuttiPrestiti = archivioPrestiti.visualizzaPrestiti();
                ObservableList<Prestito> l = FXCollections.observableArrayList(tuttiPrestiti);
                tablePrestitiAttivi.setItems(l);
            } catch (PrestitoNonTrovatoException e){

                tablePrestitiAttivi.getItems().clear();
            } catch(Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
            }
        }
    }
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
        if (filtroPrestitiCombo != null) {
            ObservableList<String> opzioniRicerca = FXCollections.observableArrayList(
                    "ISBN", "Matricola"
            );
            filtroPrestitiCombo.setItems(opzioniRicerca);
            filtroPrestitiCombo.getSelectionModel().selectFirst();
        }
        if(tablePrestitiAttivi!=null){
            tablePrestitiAttivi.setEditable(true);
            if (colMatricola != null) {
                colMatricola.setCellValueFactory(new PropertyValueFactory<>("profilo"));

            }

            if (colIsbn != null) {
                colIsbn.setCellValueFactory(new PropertyValueFactory<>("libro"));

            }

            if (colDataInizio != null) {
                colDataInizio.setCellValueFactory(new PropertyValueFactory<>("dataPrestito"));
                colDataInizio.setEditable(true);
                colDataInizio.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

                colDataInizio.setOnEditCommit(event -> {
                    event.getRowValue().setDataPrestito(event.getNewValue());
                });
            }

            if (colScadenza != null) {
                colScadenza.setCellValueFactory(new PropertyValueFactory<>("dataScadenza"));
                colScadenza.setEditable(true);
                colScadenza.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
                colScadenza.setOnEditCommit(event -> {
                    event.getRowValue().setDataScadenza(event.getNewValue());
                });
            }

            try{
                List<Prestito> tuttiPrestiti = archivioPrestiti.visualizzaPrestiti();
                ObservableList<Prestito> l = FXCollections.observableArrayList(tuttiPrestiti);
                tablePrestitiAttivi.setItems(l);
            } catch (PrestitoNonTrovatoException e){
                showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Prestito non presente nell'archivio.", e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
            }
        }

}
    }


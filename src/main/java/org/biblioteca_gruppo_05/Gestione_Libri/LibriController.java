package org.biblioteca_gruppo_05.Gestione_Libri;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.biblioteca_gruppo_05.Eccezioni.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class LibriController implements Initializable {

    private ArchivioLibri archivioLibri;
    public static String ISBNtemporaneo;

    @FXML private TextField addTitoloField;
    @FXML private TextField addAutoreField;
    @FXML private TextField addIsbnField;
    @FXML private DatePicker dataPubblicazionePicker;

    @FXML private TextField searchIsbnField;
    @FXML private VBox tableViewContainer;
    @FXML private TableView<Libro> tableLibri;
    @FXML private TableColumn<Libro, String> titoloColumn;
    @FXML private TableColumn<Libro, String> autoreColumn;
    @FXML private TableColumn<Libro, String> isbnColumn;
    @FXML private TableColumn<Libro, LocalDate> dataPubblicazioneColumn;
    @FXML private TableColumn<Libro, Integer> copieColumn;
    @FXML private Label errorLabel;
    @FXML private VBox previewContainer;
    @FXML private Label lblTitolo;
    @FXML private Label lblAutore;
    @FXML private Label lblCopie;

    @FXML private TextField searchIsbnFielde;
    @FXML private VBox tableViewContainere;
    @FXML private TableView<Libro> tableLibrie;
    @FXML private TableColumn<Libro, String> titoloColumne;
    @FXML private TableColumn<Libro, String> autoreColumne;
    @FXML private TableColumn<Libro, String> isbnColumne;
    @FXML private TableColumn<Libro, LocalDate> dataPubblicazioneColumne;
    @FXML private TableColumn<Libro, Integer> copieColumne;

    @FXML private VBox searchContainer;
    @FXML private VBox editFormContainer;
    @FXML private Button btnCerca;
    @FXML private TextField titoloField;
    @FXML private TextField autoreField;
    @FXML private TextField annoField;
    @FXML private TextField isbnDisplayField;
    @FXML private Spinner<Integer> copieSpinner;

    @FXML private TableView<Libro> tableLibriVisualizza;
    @FXML private TableColumn<Libro, String> titoloColumnVisualizza;
    @FXML private TableColumn<Libro, String> autoreColumnVisualizza;
    @FXML private TableColumn<Libro, String> isbnColumnVisualizza;
    @FXML private TableColumn<Libro, LocalDate> dataPubblicazioneColumnVisualizza;
    @FXML private TableColumn<Libro, Integer> copieColumnVisualizza;
    @FXML private ComboBox<String> filtroLibriCombo;
    @FXML private TextField searchLibriField;
    public LibriController(){

    }
    public LibriController(ArchivioLibri manager) {
        this.archivioLibri = manager;
    }
    public static void setIsbnDaCaricare(String ISBN){
        ISBNtemporaneo=ISBN;
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

    @FXML private void handleCercaVisualizza(ActionEvent event)  {
        tableLibriVisualizza.getItems().clear();

        String criterio = filtroLibriCombo.getValue(); // "ISBN", "Titolo" o "Autore"
        String query = searchLibriField.getText().trim();

        if (query.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attenzione", "Campo vuoto", "Inserisci un termine di ricerca.");
            return;
        }

        try{
            ObservableList<Libro> risultati= FXCollections.observableArrayList();
            switch(criterio){
                case "ISBN":
                    Libro libroTrovato= archivioLibri.ricercaLibroPerISBN(query);
                    if (libroTrovato != null) {
                        risultati.add(libroTrovato);
                    }
                    break;
                case "Titolo":
                    risultati = FXCollections.observableArrayList( archivioLibri.ricercaLibriPerTitolo(query));
                    break;
                case "Autore":

                    risultati = FXCollections.observableArrayList( archivioLibri.ricercaLibriPerAutore(query));
                    break;
            }
            tableLibriVisualizza.setItems(risultati);

        }catch(LibroNonTrovatoException e){
            showAlert(Alert.AlertType.WARNING,"Attenzione", "Libri non trovati", e.getMessage());
        }

    }
    @FXML private void handleCercaLibro(ActionEvent event) {
        if (tableLibri != null) {
            tableLibri.getItems().clear();
        }
        if (tableViewContainer != null) {
            tableViewContainer.setVisible(false);
            tableViewContainer.setManaged(false);
        }
        if (previewContainer != null) {
            previewContainer.setVisible(false);
            previewContainer.setManaged(false);
        }

        try{
            String ISBN = searchIsbnField.getText();

            Libro libroTrovato = archivioLibri.ricercaLibroPerISBN(ISBN);

            ObservableList<Libro> risultati = FXCollections.observableArrayList(libroTrovato);
            tableLibri.setItems(risultati);

            if (tableViewContainer != null) {
                tableViewContainer.setVisible(true);
                tableViewContainer.setManaged(true);
            }

            if (previewContainer != null) {
                previewContainer.setVisible(true);
                previewContainer.setManaged(true);
            }

            showAlert(Alert.AlertType.INFORMATION, "Ricerca Completata", "Successo", "Il libro è stato trovato e visualizzato.");

        } catch(ErroreISBNException e){
            showAlert(Alert.AlertType.ERROR, "Errore di Input", "Formato ISBN non valido.", e.getMessage());
        } catch (LibroNonTrovatoException e) {
            showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Libro non presente nell'archivio.", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
        }
    }
    @FXML private void handleConfermaElimina(ActionEvent event) {
        try{

            archivioLibri.rimuoviLibro(searchIsbnField.getText());
            showAlert(Alert.AlertType.INFORMATION, "Modifica ", "Successo.", "il libro è stato eliminato");
        }catch(LibroNonTrovatoException e){
            showAlert(Alert.AlertType.WARNING, "Ricerca fallita", "Errore.","il libro non è stato eliminato");
        }
    }
    @FXML private void handleConfermaModifica(ActionEvent event) {
        try{
            archivioLibri.salvaSuFile();
            showAlert(Alert.AlertType.INFORMATION, "Modifica ", "Successo.", "il libro è stato modificato correttamente");
        } catch (ErroreScritturaFileException e) {
            showAlert(Alert.AlertType.ERROR, "Salvataggio fallito", "Errore di scrittura sul file.", e.getMessage());
        }
    }
    @FXML private void handleResetLibri(ActionEvent event) {
        try{
            List<Libro> tuttiLibri = archivioLibri.visualizzaLibri();
            ObservableList<Libro> l = FXCollections.observableArrayList(tuttiLibri);
            tableLibriVisualizza.setItems(l);
        } catch (LibroNonTrovatoException e){
            showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Libro non presente nell'archivio.", e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
        }
    }
    @FXML private void handleEliminaVisualizza(ActionEvent event){
        Libro libroSelezionato = tableLibriVisualizza.getSelectionModel().getSelectedItem();
        if (libroSelezionato != null) {
            LibriController.setIsbnDaCaricare(libroSelezionato.getISBN());
        }else{
            LibriController.setIsbnDaCaricare(null);
        }
        switchScene(event, "/org/biblioteca_gruppo_05/Gestione_Libri_View/Elimina-Libro.fxml");
    }
    @FXML private void handleModificaVisualizza(ActionEvent event){

        Libro libroSelezionato = tableLibriVisualizza.getSelectionModel().getSelectedItem();
        if (libroSelezionato != null) {
            LibriController.setIsbnDaCaricare(libroSelezionato.getISBN());
        }else{
            LibriController.setIsbnDaCaricare(null);
        }
        switchScene(event, "/org/biblioteca_gruppo_05/Gestione_Libri_View/Modifica-Libro.fxml");

    }

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
        if(ISBNtemporaneo!=null && searchIsbnField.getText()!=null){
            searchIsbnField.setText(ISBNtemporaneo);
            ISBNtemporaneo=null;
        }
        if(tableLibriVisualizza!=null){
            tableLibriVisualizza.setEditable(false);
            if(titoloColumnVisualizza!=null)
                titoloColumnVisualizza.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            if(autoreColumnVisualizza!=null)
                autoreColumnVisualizza.setCellValueFactory(new PropertyValueFactory<>("autore"));
            if(isbnColumnVisualizza!=null){
                isbnColumnVisualizza.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
                isbnColumnVisualizza.setEditable(true);
            }

            if(dataPubblicazioneColumnVisualizza!=null)
                dataPubblicazioneColumnVisualizza.setCellValueFactory(new PropertyValueFactory<>("dataPubblicazione"));
            if(copieColumnVisualizza!=null)
                copieColumnVisualizza.setCellValueFactory(new PropertyValueFactory<>("numeroCopie"));
            try{
                List<Libro> tuttiLibri = archivioLibri.visualizzaLibri();
                ObservableList<Libro> l = FXCollections.observableArrayList(tuttiLibri);
                tableLibriVisualizza.setItems(l);
            } catch (LibroNonTrovatoException e){
                showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Libro non presente nell'archivio.", e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
            }
        }
        if (filtroLibriCombo != null) {
            ObservableList<String> opzioniRicerca = FXCollections.observableArrayList(
                    "ISBN", "Titolo", "Autore"
            );
            filtroLibriCombo.setItems(opzioniRicerca);
            filtroLibriCombo.getSelectionModel().selectFirst();
        }
        if (tableLibri != null) {
            tableLibri.setEditable(true);

            if (titoloColumn != null) {
                titoloColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
                titoloColumn.setEditable(true);
                titoloColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                titoloColumn.setOnEditCommit(event -> {
                    event.getRowValue().setTitolo(event.getNewValue());
                });
            }

            if (autoreColumn != null) {
                autoreColumn.setCellValueFactory(new PropertyValueFactory<>("autore"));
                autoreColumn.setEditable(true);
                autoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                autoreColumn.setOnEditCommit(event -> {
                    event.getRowValue().setAutore(event.getNewValue());
                });
            }

            if (isbnColumn != null) {
                isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
                isbnColumn.setEditable(false);
                isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                isbnColumn.setOnEditCommit(event -> {
                    event.getRowValue().setISBN(event.getNewValue());
                });
            }

            if (dataPubblicazioneColumn != null) {
                dataPubblicazioneColumn.setCellValueFactory(new PropertyValueFactory<>("dataPubblicazione"));
                dataPubblicazioneColumn.setEditable(true);
                dataPubblicazioneColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
                dataPubblicazioneColumn.setOnEditCommit(event -> {
                    event.getRowValue().setDataPubblicazione(event.getNewValue());
                });
            }

            if (copieColumn != null) {
                copieColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCopie"));
                copieColumn.setEditable(true);
                copieColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

                    copieColumn.setOnEditCommit(event -> {
                        try {
                            event.getRowValue().setNumeroCopie(event.getNewValue());
                        } catch (ErroreNumeroCopieLibro e) {
                            showAlert(Alert.AlertType.WARNING, "Numero copie errato", "Inserimento numero copie non corretto", e.getMessage());
                            tableLibri.refresh();
                        }
                    });




            }
        }

        if (tableViewContainer != null) {
            tableViewContainer.setVisible(false);
            tableViewContainer.setManaged(false);
        }

    }
}

package org.biblioteca_gruppo_05.Gestione_Profili;
import javafx.application.Platform;
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
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.biblioteca_gruppo_05.Eccezioni.*;
import org.biblioteca_gruppo_05.Gestione_Libri.ArchivioLibri;
import org.biblioteca_gruppo_05.Gestione_Libri.LibriController;
import org.biblioteca_gruppo_05.Gestione_Libri.Libro;
import org.biblioteca_gruppo_05.Gestione_Prestiti.ArchivioPrestiti;
import org.biblioteca_gruppo_05.Gestione_Prestiti.PrestitiController;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfiliController implements Initializable {

        private ArchivioProfili archivioProfili;
        public static String matricolaTemporanea;

        @FXML private TextField nomeField;
        @FXML private TextField cognomeField;
        @FXML private TextField matricolaField;
        @FXML private TextField emailField;

        @FXML private ComboBox<String> filtroUtentiCombo;
        @FXML private TextField searchUtentiField;


        // Container della tabella (per nasconderlo/mostrarlo)
        @FXML private VBox tableViewContainer;

        // La tabella principale
        @FXML private TableView<Profilo> tableProfili;

        // Le singole colonne (assicurati che <Utente, String> sia corretto in base alla tua classe modello)
        @FXML private TableColumn<Profilo, String> colonnaMatricolaP;
        @FXML private TableColumn<Profilo, String> colonnaNomeP;
        @FXML private TableColumn<Profilo, String> colonnaCognomeP;
        @FXML private TableColumn<Profilo, String> colonnaEmailP;
        @FXML private TableColumn<Profilo, Integer> colonnaCopieP;
        @FXML private TextField searchMatricolaField;

        @FXML private VBox editFormContainer;
        @FXML private TextField editMatricolaField;
        @FXML private TextField editNomeField;
        @FXML private TextField editCognomeField;
        @FXML private TextField editEmailField;
        @FXML private TextField storicoField;

        @FXML private TableView<Profilo> tableProfiliVisualizza;

        @FXML private TableColumn<Profilo, String> colonnaNome;
        @FXML private TableColumn<Profilo, String> colonnaCognome;
        @FXML private TableColumn<Profilo, String> colonnaMatricola;
        @FXML private TableColumn<Profilo, String> colonnaEmail;
        @FXML private TableColumn<Profilo, Integer> colonnaCopie;

        @FXML private VBox confirmContainer;
        @FXML private Label lblNomeCognome;
        @FXML private Label lblMatricola;
        public ProfiliController(){

        }
        public ProfiliController(ArchivioProfili manager) {
            this.archivioProfili = manager;
        }
        public static void setMatricolaDaCaricare(String matricola){
            matricolaTemporanea=matricola;
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
        private boolean isInputValido(){
            String errorMessage="";
            if(nomeField.getText()==null || nomeField.getText().trim().isEmpty()){
                errorMessage+="Devi inserire il nome\n";
            }
            if(cognomeField.getText()==null || cognomeField.getText().trim().isEmpty()){
                errorMessage+="Devi inserire il cognome\n";
            }
            if(matricolaField.getText()==null || matricolaField.getText().trim().isEmpty()){
                errorMessage+="Devi inserire la matricola\n";
            }
            if(emailField.getText()==null || emailField.getText().trim().isEmpty()){
                errorMessage+="Devi inserire l'email\n";
            }
            if (errorMessage.isEmpty()) {
                return true;
            } else {
                showAlert(Alert.AlertType.ERROR, "Errore di Validazione", "Correggi i seguenti campi:", errorMessage);
                return false;
            }
        }

        @FXML private void handleCercaUtente(ActionEvent event) {
            if (tableProfili != null) {
                tableProfili.getItems().clear();
            }
            // Usa solo tableViewContainer per la visibilità in entrambe le scene
            if (tableViewContainer != null) {
                tableViewContainer.setVisible(false);
                tableViewContainer.setManaged(false);
            }



            try{
                String matricola = searchMatricolaField.getText();
                Profilo profiloTrovato = archivioProfili.ricercaProfiloPerMatricola(matricola);
                ObservableList<Profilo> risultati = FXCollections.observableArrayList(profiloTrovato);
                tableProfili.setItems(risultati);


                if (tableViewContainer != null) {
                    tableViewContainer.setVisible(true);
                    tableViewContainer.setManaged(true);
                }

                showAlert(Alert.AlertType.INFORMATION, "Ricerca Completata", "Successo", "Il profilo è stato trovato e visualizzato.");

            } catch(ErroreMatricolaException e){
                showAlert(Alert.AlertType.ERROR, "Errore di Input", "Formato matricola non valido.", e.getMessage());
            } catch (UtenteNonTrovatoException e) {
                showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Utente non presente nell'archivio.", e.getMessage());
            } catch (Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
            }
        }
        @FXML private void handleCercaVisualizza(ActionEvent event)  {
            tableProfiliVisualizza.getItems().clear();

            String criterio = filtroUtentiCombo.getValue(); // "Matricola", "Nome", "Cognome"
            String query = searchUtentiField.getText().trim();

            if (query.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Attenzione", "Campo vuoto", "Inserisci un termine di ricerca.");
                return;
            }

            try{
                ObservableList<Profilo> risultati= FXCollections.observableArrayList();
                switch(criterio){
                    case "Matricola":
                        Profilo profiloTrovato= archivioProfili.ricercaProfiloPerMatricola(query);
                        if (profiloTrovato != null) {
                            risultati.add(profiloTrovato);
                        }
                        break;
                    case "Nome":
                        risultati = FXCollections.observableArrayList( archivioProfili.ricercaProfiloPerNome(query));
                        break;
                    case "Cognome":

                        risultati = FXCollections.observableArrayList( archivioProfili.ricercaProfiloPerCognome(query));
                        break;
                }
                tableProfiliVisualizza.setItems(risultati);

            }catch(UtenteNonTrovatoException e){
                showAlert(Alert.AlertType.WARNING,"Attenzione", "Profili non trovati", e.getMessage());
            }

        }
        @FXML private void handleResetUtenti(ActionEvent event) {
            try{
                List<Profilo> tuttiProfili = archivioProfili.visualizzaProfili();
                ObservableList<Profilo> l = FXCollections.observableArrayList(tuttiProfili);
                tableProfiliVisualizza.setItems(l);
            } catch (UtenteNonTrovatoException e){
                showAlert(Alert.AlertType.WARNING, "Ricerca Fallita", "Profilo non presente nell'archivio.", e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
            }
        }

        @FXML private void handlePulisci(ActionEvent event) {
            matricolaField.setText("");
            nomeField.setText("");
            cognomeField.setText("");
            emailField.setText("");
        }

        @FXML private void handleSalvaProfilo(ActionEvent event) {
            if (isInputValido()) {
                String mail=emailField.getText();
                if(!controllaMail(mail)){
                    showAlert(Alert.AlertType.ERROR, "Errore di Validazione Mail", "La mail deve essere nel formato testo@testo.", "Mail sbagliata.");
                    return;
                }
                try {
                    Profilo l = new Profilo(nomeField.getText(),cognomeField.getText(),matricolaField.getText(),  emailField.getText());
                    archivioProfili.aggiungiProfilo(l);
                    showAlert(Alert.AlertType.INFORMATION, "Successo", "Profilo Aggiunto", "Il profilo è stato aggiunto all'archivio.");
                    handlePulisci(null);
                } catch (ErroreMatricolaException e) {
                    showAlert(Alert.AlertType.ERROR, "Errore matricola", "Formato matricola non valido.", e.getMessage());
                } catch (UtenteEsitenteException e) {
                    showAlert(Alert.AlertType.WARNING, "Errore di Duplicazione", "Profilo già esistente", e.getMessage());
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Errore di Sistema", "Impossibile salvare il profilo.", e.getMessage());
                }
            }
        }
        @FXML private void handleSalvaModifiche(ActionEvent event) {
            try{
                archivioProfili.salvaSuFile();
                showAlert(Alert.AlertType.INFORMATION, "Modifica ", "Successo.", "il profilo è stato modificato correttamente");
            } catch (ErroreScritturaFileException e) {
                showAlert(Alert.AlertType.ERROR, "Salvataggio fallito", "Errore di scrittura sul file.", e.getMessage());
            }
        }
        @FXML private void handleConfermaElimina(ActionEvent event) {
            try{

                archivioProfili.rimuoviProfilo(searchMatricolaField.getText());
                showAlert(Alert.AlertType.INFORMATION, "Elimina ", "Successo.", "il profilo è stato eliminato");
            }catch(UtenteNonTrovatoException e){
                showAlert(Alert.AlertType.WARNING, "Ricerca fallita", "Errore.","il profilo non è stato eliminato");
            }catch (RuntimeException e) {
                showAlert(Alert.AlertType.WARNING, "Annullata", "Errore.",e.getMessage());

            }
        }
        private void showAlert(Alert.AlertType type, String title, String header, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
        private void switchScene(ActionEvent event, String fxmlPath) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

                // AGGIUNTA FONDAMENTALE: Passiamo l'archivio al prossimo controller
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ProfiliController.class) {
                        return new ProfiliController(this.archivioProfili);
                    }
                    try {
                        return controllerClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Impossibile creare il controller: " + controllerClass.getName(), e);
                    }
                });

                Parent root = loader.load();
                Scene stageAttuale = ((Node) event.getSource()).getScene();

                // Opzionale: Mantiene le dimensioni/impostazioni dello stage
                Stage stage = (Stage) stageAttuale.getWindow();

                stageAttuale.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Errore Critico!", "Errore nel caricamento della scena", e.getMessage());
            }
        }
        @FXML private void handleEliminaVisualizza(ActionEvent event){
            Profilo profiloSelezionato = tableProfiliVisualizza.getSelectionModel().getSelectedItem();
            if (profiloSelezionato != null) {
                ProfiliController.setMatricolaDaCaricare(profiloSelezionato.getMatricola());
            }else{
                ProfiliController.setMatricolaDaCaricare(null);
            }
            switchScene(event, "/org/biblioteca_gruppo_05/Gestione_Profili_View/Elimina-Profilo.fxml");
        }
        @FXML private void handleModificaVisualizza(ActionEvent event){

            Profilo profiloSelezionato = tableProfiliVisualizza.getSelectionModel().getSelectedItem();
            if (profiloSelezionato != null) {
                ProfiliController.setMatricolaDaCaricare(profiloSelezionato.getMatricola());
            }else{
                ProfiliController.setMatricolaDaCaricare(null);
            }
            switchScene(event, "/org/biblioteca_gruppo_05/Gestione_Profili_View/Modifica-Profilo.fxml");
        }
        public boolean controllaMail(String email) {
            if (email == null) {
                return false;
            }
            String regexPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    private void switchScene2(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            loader.setControllerFactory(controllerClass -> {

                if (controllerClass == ProfiliController.class) {
                    return new ProfiliController(this.archivioProfili);
                }

                if (controllerClass == PrestitiController.class) {

                    ArchivioPrestiti nuovoArchivioPrestiti = new ArchivioPrestiti("prestiti.bin");
                    ArchivioLibri nuovoArchivioLibri = new ArchivioLibri("libri.bin");


                    return new PrestitiController(nuovoArchivioPrestiti, nuovoArchivioLibri, this.archivioProfili);
                }

                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Impossibile creare il controller: " + controllerClass.getName(), e);
                }
            });

            Parent root = loader.load();
            Scene stageAttuale = ((Node) event.getSource()).getScene();
            stageAttuale.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore Critico!", "Errore nel caricamento della scena", e.getMessage());
        }
    }
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            if(matricolaTemporanea!=null && searchMatricolaField.getText()!=null){
                searchMatricolaField.setText(matricolaTemporanea);
                matricolaTemporanea=null;
                Platform.runLater(() -> {
                    handleCercaUtente(new ActionEvent());
                });

            }
            if(tableProfiliVisualizza!=null){
                tableProfiliVisualizza.setEditable(false);
                if (colonnaMatricola != null) {
                    // Cerca getMatricola() nella classe Utente
                    colonnaMatricola.setCellValueFactory(new PropertyValueFactory<>("matricola"));
                }

                if (colonnaNome != null) {
                    // Cerca getNome()
                    colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                }

                if (colonnaCognome != null) {
                    // Cerca getCognome()
                    colonnaCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
                }

                if (colonnaEmail != null) {
                    // Cerca getEmail()
                    colonnaEmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
                }

                if (colonnaCopie != null) {
                    colonnaCopie.setCellValueFactory(new PropertyValueFactory<>("numeroPrestiti"));

                    // Creiamo la CellFactory personalizzata
                    colonnaCopie.setCellFactory(new Callback<TableColumn<Profilo, Integer>, TableCell<Profilo, Integer>>() {
                        @Override
                        public TableCell<Profilo, Integer> call(TableColumn<Profilo, Integer> param) {
                            return new TableCell<Profilo, Integer>() {
                                final Button btn = new Button();

                                @Override
                                public void updateItem(Integer item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty || item == null) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        btn.setText(item.toString());
                                        btn.setStyle("-fx-background-color: #8d6e63; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                                        btn.setOnAction(event -> {
                                            Profilo profilo = getTableView().getItems().get(getIndex());

                                            PrestitiController.matricolaDaRicercareAutom = profilo.getMatricola();

                                            switchScene2(event, "/org/biblioteca_gruppo_05/Gestione_Prestiti_View/Prestiti.fxml");
                                        });

                                        setGraphic(btn);
                                        setText(null);
                                    }
                                }
                            };
                        }
                    });
                }

                try{
                    List<Profilo> tuttiProfili = archivioProfili.visualizzaProfili();
                    ObservableList<Profilo> l = FXCollections.observableArrayList(tuttiProfili);
                    tableProfiliVisualizza.setItems(l);
                } catch (UtenteNonTrovatoException e){
                    if (tableProfiliVisualizza.getItems() != null) {
                        tableProfiliVisualizza.getItems().clear();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Errore Critico", "Si è verificato un errore imprevisto.", e.getMessage());
                }
            }
            if (filtroUtentiCombo != null) {
                ObservableList<String> opzioniRicerca = FXCollections.observableArrayList(
                        "Matricola", "Nome", "Cognome"
                );
                filtroUtentiCombo.setItems(opzioniRicerca);
                filtroUtentiCombo.getSelectionModel().selectFirst();
            }
            if (tableProfili != null) {
                tableProfili.setEditable(true);

                if (colonnaMatricolaP != null) {
                    colonnaMatricolaP.setCellValueFactory(new PropertyValueFactory<>("matricola"));
                    colonnaMatricolaP.setEditable(false);
                    colonnaMatricolaP.setCellFactory(TextFieldTableCell.forTableColumn());
                    colonnaMatricolaP.setOnEditCommit(event -> {
                        event.getRowValue().setMatricola(event.getNewValue());
                    });
                }

                if (colonnaNomeP != null) {
                    colonnaNomeP.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    colonnaNomeP.setEditable(true);
                    colonnaNomeP.setCellFactory(TextFieldTableCell.forTableColumn());
                    colonnaNomeP.setOnEditCommit(event -> {
                        event.getRowValue().setNome(event.getNewValue());
                    });
                }

                if (colonnaCognomeP != null) {
                    colonnaCognomeP.setCellValueFactory(new PropertyValueFactory<>("cognome"));
                    colonnaCognomeP.setEditable(true);
                    colonnaCognomeP.setCellFactory(TextFieldTableCell.forTableColumn());
                    colonnaCognomeP.setOnEditCommit(event -> {
                        event.getRowValue().setCognome(event.getNewValue());
                    });
                }

                if (colonnaEmailP != null) {
                    colonnaEmailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
                    colonnaEmailP.setEditable(true);
                    colonnaEmailP.setCellFactory(TextFieldTableCell.forTableColumn());
                    colonnaEmailP.setOnEditCommit(event -> {
                        event.getRowValue().setMail(event.getNewValue());
                    });
                }

                if (colonnaCopieP != null) {
                    colonnaCopieP.setCellValueFactory(new PropertyValueFactory<>("numeroPrestiti"));
                    colonnaCopieP.setEditable(false);
                    colonnaCopieP.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
                    colonnaCopieP.setOnEditCommit(event -> {
                        event.getRowValue().setNumeroPrestiti(event.getNewValue());
                    });
                }
            }

            if (tableViewContainer != null) {
                tableViewContainer.setVisible(false);
                tableViewContainer.setManaged(false);
            }
        }
    }


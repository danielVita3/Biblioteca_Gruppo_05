package org.biblioteca_gruppo_05.Application;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.biblioteca_gruppo_05.Gestione_Libri.*;
import org.biblioteca_gruppo_05.Gestione_Prestiti.*;
import org.biblioteca_gruppo_05.Gestione_Profili.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private final ArchivioLibri archivioLibri = new ArchivioLibri("libri.bin");
    private final ArchivioProfili archivioProfili = new ArchivioProfili("profili.bin");
    private final ArchivioPrestiti archivioPrestiti = new ArchivioPrestiti("prestiti.bin");
    @FXML
    private Label Wlcomtext2;

    @FXML
    private Button apriButton;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnArchivio;

    @FXML
    private Button btnProfili;

    @FXML
    private Button btnPrestiti;

    @FXML
    private void onApriButton(ActionEvent event) throws IOException {
        switchScene(event,"/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml");
    }

    @FXML
    private void handleIndietro(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Application_View/hello-view.fxml");
    }

    @FXML
    private void handleVaiArchivio(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Libri_View/Archivio.fxml");
    }

    @FXML
    private void handleVaiProfili(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Profili_View/Profili.fxml");
    }

    @FXML
    private void handleVaiPrestiti(ActionEvent event) throws IOException{
        switchScene(event,"/org/biblioteca_gruppo_05/Gestione_Prestiti_View/Prestiti.fxml");
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

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == LibriController.class) {
                    return new LibriController(archivioLibri);
                } else if (controllerClass == ProfiliController.class) {
                    return new ProfiliController(archivioProfili);
                } else if (controllerClass == PrestitiController.class) {
                    return new PrestitiController(archivioPrestiti,archivioLibri,archivioProfili);
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
            showAlert(Alert.AlertType.ERROR,"Errore Critico!","Errore nel caricamento della scena: ",e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
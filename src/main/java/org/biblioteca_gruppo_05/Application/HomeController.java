package org.biblioteca_gruppo_05.Application;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Application_View/Home-Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home Page!");
        stage.centerOnScreen();
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleIndietro(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Application_View/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Biblioteca Gruppo 05!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleVaiArchivio(ActionEvent event) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Libri_View/Archivio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Gestione Archivio Libri");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleVaiProfili(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Profili_View/Profili.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Gestione Archivio Profili!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleVaiPrestiti(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/biblioteca_gruppo_05/Gestione_Prestiti_View/Prestiti.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("Gestione Archivio Prestiti!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
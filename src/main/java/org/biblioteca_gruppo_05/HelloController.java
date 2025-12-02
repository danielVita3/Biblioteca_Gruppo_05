package org.biblioteca_gruppo_05;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    private Button apriButton;

    // Questo metodo viene chiamato quando clicchi su "SFOGLIA IL CATALOGO"
    @FXML
    protected void onApriButton(ActionEvent event) {
        try {
            // 1. Carica il file FXML della schermata successiva (il Menu principale)
            // Assicurati che il file si chiami "Menu.fxml" (o cambia il nome qui sotto)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home Page.fxml"));
            Parent root = loader.load();

            // 2. Recupera la finestra corrente (Stage) dal bottone cliccato
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Imposta la nuova scena e mostrala
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            // Se c'è un errore (es. non trova il file), lo stampa nella console
            e.printStackTrace();
            System.err.println("ERRORE CRITICO: Non riesco a trovare il file 'Menu.fxml'. Controlla il nome nella cartella resources!");
        }
    }
}
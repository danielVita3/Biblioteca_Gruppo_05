module gruppo_05.biblioteca_gruppo_05 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.biblioteca_gruppo_05 to javafx.fxml;
    exports org.biblioteca_gruppo_05;
}
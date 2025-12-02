module gruppo_05.biblioteca_gruppo_05 {
    requires javafx.controls;
    requires javafx.fxml;
    requires gruppo_05.biblioteca_gruppo_05;


    opens org.biblioteca_gruppo_05 to javafx.fxml;
    exports org.biblioteca_gruppo_05;
    exports org.biblioteca_gruppo_05.Gestione_Libri;
    opens org.biblioteca_gruppo_05.Gestione_Libri to javafx.fxml;
    exports org.biblioteca_gruppo_05.Gestione_Profili;
    opens org.biblioteca_gruppo_05.Gestione_Profili to javafx.fxml;
    exports org.biblioteca_gruppo_05.Gestione_Prestiti;
    opens org.biblioteca_gruppo_05.Gestione_Prestiti to javafx.fxml;
    exports org.biblioteca_gruppo_05.Application;
    opens org.biblioteca_gruppo_05.Application to javafx.fxml;
}
module org.biblioteca_gruppo_05 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;



    exports org.biblioteca_gruppo_05.Gestione_Libri;
    opens org.biblioteca_gruppo_05.Gestione_Libri to javafx.fxml;
    exports org.biblioteca_gruppo_05.Gestione_Profili;
    opens org.biblioteca_gruppo_05.Gestione_Profili to javafx.fxml;
    exports org.biblioteca_gruppo_05.Gestione_Prestiti;
    opens org.biblioteca_gruppo_05.Gestione_Prestiti to javafx.fxml;
    exports org.biblioteca_gruppo_05.Application;
    opens org.biblioteca_gruppo_05.Application to javafx.fxml;
    exports org.biblioteca_gruppo_05.Eccezioni;
    opens org.biblioteca_gruppo_05.Eccezioni to javafx.fxml;
}
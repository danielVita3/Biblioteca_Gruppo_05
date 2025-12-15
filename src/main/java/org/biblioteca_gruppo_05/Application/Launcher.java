package org.biblioteca_gruppo_05.Application;

import org.biblioteca_gruppo_05.Eccezioni.LibroEsistenteException;
import org.biblioteca_gruppo_05.Eccezioni.UtenteEsitenteException;
import org.biblioteca_gruppo_05.Gestione_Libri.ArchivioLibri;
import org.biblioteca_gruppo_05.Gestione_Libri.PopolatoreArchivio;
import org.biblioteca_gruppo_05.Gestione_Profili.ArchivioProfili;
import org.biblioteca_gruppo_05.Gestione_Profili.PopolatoreProfili;

public class Launcher {
    public static void main(String[] args) throws LibroEsistenteException, UtenteEsitenteException {
        ArchivioProfili a=new ArchivioProfili("profili.bin");
        ArchivioLibri b=new ArchivioLibri("libri.bin");
        PopolatoreArchivio.riempiArchivio(b);
        PopolatoreProfili.riempiArchivio(a);
        HelloApplication.main(args);
    }
}
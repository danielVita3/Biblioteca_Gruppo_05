package org.biblioteca_gruppo_05.Gestione_Profili;

import org.biblioteca_gruppo_05.Eccezioni.UtenteEsitenteException;
import org.biblioteca_gruppo_05.Gestione_Profili.ArchivioProfili;
import org.biblioteca_gruppo_05.Gestione_Profili.Profilo;

public class PopolatoreProfili {

    public static void riempiArchivio(ArchivioProfili archivio) throws UtenteEsitenteException {

        // Generiamo 30 profili realistici
        archivio.aggiungiProfilo(new Profilo("Mario", "Rossi", "0610000001", "mario.rossi@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Giulia", "Bianchi", "0610000002", "giulia.bianchi@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Luca", "Verdi", "0610000003", "luca.verdi@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Sofia", "Esposito", "0610000004", "sofia.esposito@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Alessandro", "Ricci", "0610000005", "alessandro.ricci@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Martina", "Rizzo", "0610000006", "martina.rizzo@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Matteo", "Moretti", "0610000007", "matteo.moretti@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Chiara", "Barbieri", "0610000008", "chiara.barbieri@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Francesco", "Ferrari", "0610000009", "francesco.ferrari@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Sara", "Romano", "0610000010", "sara.romano@istituto.it"));

        // Blocco 2
        archivio.aggiungiProfilo(new Profilo("Andrea", "Colombo", "0610000011", "andrea.colombo@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Giorgia", "Bruno", "0610000012", "giorgia.bruno@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Davide", "De Luca", "0610000013", "davide.deluca@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Francesca", "Gallo", "0610000014", "francesca.gallo@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Simone", "Costa", "0610000015", "simone.costa@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Alice", "Fontana", "0610000016", "alice.fontana@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Federico", "Conti", "0610000017", "federico.conti@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Elena", "Greco", "0610000018", "elena.greco@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Marco", "Marino", "0610000019", "marco.marino@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Valentina", "Giordano", "0610000020", "valentina.giordano@istituto.it"));

        // Blocco 3
        archivio.aggiungiProfilo(new Profilo("Antonio", "Russo", "0610000021", "antonio.russo@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Elisa", "Lombardi", "0610000022", "elisa.lombardi@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Giovanni", "Mancini", "0610000023", "giovanni.mancini@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Beatrice", "Amato", "0610000024", "beatrice.amato@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Riccardo", "Gatti", "0610000025", "riccardo.gatti@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Laura", "Pellegrini", "0610000026", "laura.pellegrini@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Daniele", "Sartori", "0610000027", "daniele.sartori@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Camilla", "D'Angelo", "0610000028", "camilla.dangelo@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Michele", "Ferri", "0610000029", "michele.ferri@istituto.it"));
        archivio.aggiungiProfilo(new Profilo("Alessia", "Monti", "0610000030", "alessia.monti@istituto.it"));   }
}
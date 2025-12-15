package org.biblioteca_gruppo_05.Gestione_Libri;

import org.biblioteca_gruppo_05.Eccezioni.LibroEsistenteException;

import java.time.LocalDate;

public class PopolatoreArchivio {

    public static void riempiArchivio(ArchivioLibri archivio) throws LibroEsistenteException {

        // 1. Classici della letteratura
        archivio.aggiungiLibro(new Libro("Il Nome della Rosa", "Umberto Eco", "9788845267330", LocalDate.of(1980, 1, 1)));
        archivio.aggiungiLibro(new Libro("1984", "George Orwell", "9780451524935", LocalDate.of(1949, 6, 8)));
        archivio.aggiungiLibro(new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", "9788845294046", LocalDate.of(1954, 7, 29)));
        archivio.aggiungiLibro(new Libro("Harry Potter e la Pietra Filosofale", "J.K. Rowling", "9788869183157", LocalDate.of(1997, 6, 26)));
        archivio.aggiungiLibro(new Libro("La Divina Commedia", "Dante Alighieri", "9788804667232", LocalDate.of(1320, 1, 1)));
        archivio.aggiungiLibro(new Libro("Il Piccolo Principe", "Antoine de Saint-Exupéry", "9788845292769", LocalDate.of(1943, 4, 6)));
        archivio.aggiungiLibro(new Libro("Orgoglio e Pregiudizio", "Jane Austen", "9780141439518", LocalDate.of(1813, 1, 28)));
        archivio.aggiungiLibro(new Libro("Il Grande Gatsby", "F. Scott Fitzgerald", "9780743273565", LocalDate.of(1925, 4, 10)));
        archivio.aggiungiLibro(new Libro("Delitto e Castigo", "Fëdor Dostoevskij", "9788806219354", LocalDate.of(1866, 1, 1)));
        archivio.aggiungiLibro(new Libro("Il Processo", "Franz Kafka", "9788806225256", LocalDate.of(1925, 4, 26)));

        // 2. Thriller e Horror
        archivio.aggiungiLibro(new Libro("Shining", "Stephen King", "9780307743657", LocalDate.of(1977, 1, 28)));
        archivio.aggiungiLibro(new Libro("Il Codice da Vinci", "Dan Brown", "9780307474278", LocalDate.of(2003, 3, 18)));
        archivio.aggiungiLibro(new Libro("Dieci Piccoli Indiani", "Agatha Christie", "9780062073488", LocalDate.of(1939, 11, 6)));
        archivio.aggiungiLibro(new Libro("Il Silenzio degli Innocenti", "Thomas Harris", "9780312924584", LocalDate.of(1988, 5, 1)));
        archivio.aggiungiLibro(new Libro("Dracula", "Bram Stoker", "9780486411095", LocalDate.of(1897, 5, 26)));
        archivio.aggiungiLibro(new Libro("IT", "Stephen King", "9781501142970", LocalDate.of(1986, 9, 15)));
        archivio.aggiungiLibro(new Libro("Uomini che odiano le donne", "Stieg Larsson", "9780307949486", LocalDate.of(2005, 8, 1)));

        // 3. Romanzi Storici e Avventura
        archivio.aggiungiLibro(new Libro("I Promessi Sposi", "Alessandro Manzoni", "9788806206675", LocalDate.of(1827, 6, 15)));
        archivio.aggiungiLibro(new Libro("I Tre Moschettieri", "Alexandre Dumas", "9780670059343", LocalDate.of(1844, 3, 14)));
        archivio.aggiungiLibro(new Libro("L'Alchimista", "Paulo Coelho", "9780062315007", LocalDate.of(1988, 1, 1)));
        archivio.aggiungiLibro(new Libro("I Pilastri della Terra", "Ken Follett", "9780451166890", LocalDate.of(1989, 10, 2)));
        archivio.aggiungiLibro(new Libro("Moby Dick", "Herman Melville", "9781503280786", LocalDate.of(1851, 10, 18)));
        archivio.aggiungiLibro(new Libro("Il Conte di Montecristo", "Alexandre Dumas", "9780140449266", LocalDate.of(1844, 8, 28)));

        // 4. Fantascienza e Distopia
        archivio.aggiungiLibro(new Libro("Fahrenheit 451", "Ray Bradbury", "9781451673319", LocalDate.of(1953, 10, 19)));
        archivio.aggiungiLibro(new Libro("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1)));
        archivio.aggiungiLibro(new Libro("Guida galattica per gli autostoppisti", "Douglas Adams", "9780345391803", LocalDate.of(1979, 10, 12)));
        archivio.aggiungiLibro(new Libro("La Macchina del Tempo", "H.G. Wells", "9780451530707", LocalDate.of(1895, 1, 1)));

        // 5. Contemporanei
        archivio.aggiungiLibro(new Libro("L'amica geniale", "Elena Ferrante", "9781609450786", LocalDate.of(2011, 10, 26)));
        archivio.aggiungiLibro(new Libro("Il cacciatore di aquiloni", "Khaled Hosseini", "9781594631931", LocalDate.of(2003, 5, 29)));
        archivio.aggiungiLibro(new Libro("La solitudine dei numeri primi", "Paolo Giordano", "9788804577029", LocalDate.of(2008, 1, 15)));
    }
}
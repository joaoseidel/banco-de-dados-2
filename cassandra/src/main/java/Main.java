import com.datastax.driver.core.Session;

import java.util.Optional;

public class Main {
    private Session session;

    public static void main(String[] args) {
        // connecta um cluster
        final CassandraConnector client = new CassandraConnector();
        client.connect("localhost", 7000);

        //
        DatabaseSetup databaseSetup = new DatabaseSetup(client);
        databaseSetup.createTable();

        MoviePersistence moviePersistence = new MoviePersistence(client);

        // persistindo um filme
        moviePersistence.persistMovie(
                "Submarine",
                2010,
                "Submarine is a 2010 coming-of-age comedy-drama film written and directed by Richard Ayoade"
        );

        // obtendo um filme
        Optional<Movie> submarine = moviePersistence.queryMovieByTitleAndYear("Submarine", 2010);
        System.out.println(submarine.get().toString());

        // removendo um filme
        moviePersistence.deleteMovieWithTitleAndYear("Submarine", 2010);

        // fecha a sessão com um cluster
        client.close();
    }
}

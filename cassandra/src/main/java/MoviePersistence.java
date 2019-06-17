import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.util.Optional;


public class MoviePersistence {
    private final CassandraConnector client;

    public MoviePersistence(CassandraConnector cassandraConnector) {
        client = cassandraConnector;
    }

    public void persistMovie(
            final String title, final int year, final String description) {
        client.getSession().execute(
                "INSERT INTO movies_keyspace.movies (title, year, description) VALUES (?, ?, ?)",
                title, year, description);
    }

    public Optional<Movie> queryMovieByTitleAndYear(final String title, final int year) {
        final ResultSet movieResults = client.getSession().execute(
                "SELECT * from movies_keyspace.movies WHERE title = ? AND year = ?", title, year);
        final Row movieRow = movieResults.one();
        final Optional<Movie> movie =
                movieRow != null
                        ? Optional.of(
                                new Movie(
                                    movieRow.getString("title"),
                                    movieRow.getInt("year"),
                                    movieRow.getString("description")
                                ))
                        : Optional.empty();
        return movie;
    }

    public void deleteMovieWithTitleAndYear(final String title, final int year) {
        final String deleteString = "DELETE FROM movies_keyspace.movies WHERE title = ? and year = ?";
        client.getSession().execute(deleteString, title, year);
    }
}


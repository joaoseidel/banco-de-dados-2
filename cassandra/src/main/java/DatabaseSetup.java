import com.datastax.driver.core.Session;

public class DatabaseSetup {
    private CassandraConnector cassandraConnector;
    private Session session;

    public DatabaseSetup(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
        this.session = cassandraConnector.getSession();
    }

    public void createTable() {
        final String createMovieCql =
                "CREATE TABLE movies_keyspace.movies (title varchar, year int, description varchar"
                        + " PRIMARY KEY (title, year))";
        session.execute(createMovieCql);
    }
}

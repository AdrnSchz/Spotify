package persistence.db;

import persistence.exceptions.PersistenceException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class allows communication to the database.
 *
 * @author Group 6
 * @version 1.0
 */
public class Database {

    /**
     * Represents the session with the database.
     */
    private final Connection db;

    /**
     * Constructor method that initializes the db attribute with a new Connection instance.
     * It establishes a connection to the database using the DBConfiguration class.
     *
     * @throws PersistenceException if the connection was unsuccessful.
     */
    public Database() throws PersistenceException {
        DBConfiguration config = new DBConfiguration();
        try {
            db = DriverManager.getConnection(
                    "jdbc:postgresql://%s:%d/%s".formatted(config.getAddress(), config.getPort(), config.getName()),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException | IOException e) {
            throw new PersistenceException("Failed to establish connection to the database", e);
        }
    }

    /**
     * Method that performs a given query to the database.
     *
     * @param query the query to perform.
     * @return ResultSet with the results of the query.
     * @throws PersistenceException if there was an error in the query execution
     */
    protected ResultSet query(String query) throws PersistenceException {
        try {
            return db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
        } catch (SQLException e) {
            throw new PersistenceException("Failed to retrieve data from the database", e);
        }
    }

    /**
     * Method that performs a given update to the database.
     * @param cmd the update to perform.
     * @return the number of rows affected by the update.
     * @throws PersistenceException if there was an error in the update execution
     */
    protected int update(String cmd) throws PersistenceException {
        try {
            return db.createStatement().executeUpdate(cmd);
        } catch (SQLException e) {
            throw new PersistenceException("Failed to update data from the database", e);
        }
    }
}
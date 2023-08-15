package persistence.db;

import business.entities.User;
import persistence.UserDAO;
import persistence.exceptions.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for {@link User} entity that connects {@link UserDAO} with the database.
 *
 * @author Group 6
 * @version 1.0
 */
public class DBUserDAO implements UserDAO {

    /**
     * Database instance to communicate with the database.
     */
    private final Database db;

    /**
     * Constructor method that initializes the db attribute.
     *
     * @param db database instance to communicate with the database.
     */
    public DBUserDAO(Database db) {
        this.db = db;
    }

    /**
     * Method that creates a new user in the database.
     *
     * @param user user with the data to add.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public void createUser(User user) throws PersistenceException {
        db.update("INSERT INTO users VALUES('%s','%s','%s')".formatted(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        ));
    }

    /**
     * Method that deletes a user from the database.
     *
     * @param username username of the user to delete.
     * @return true if the user was deleted, false otherwise.
     * @throws PersistenceException if there was an error in the database.º
     */
    @Override
    public boolean deleteUser(String username) throws PersistenceException {
        return 0 < db.update("DELETE FROM users WHERE username LIKE '%s'".formatted(username));
    }

    /**
     * Method that retrieves a user from the database.
     *
     * @param user username or email of the user to retrieve.
     * @return the user if it exists, null otherwise.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public User getUser(String user) throws PersistenceException {

        try (
            ResultSet queryByUsername = db.query("SELECT * FROM users WHERE username LIKE '%s'".formatted(user));
            ResultSet queryByEmail = db.query("SELECT * FROM users WHERE email LIKE '%s'".formatted(user))
        ) {
            ResultSet query;
            if (queryByUsername.first()) {
                query = queryByUsername;
            } else if (queryByEmail.first()) {
                query = queryByEmail;
            } else {
                return null;
            }
            return new User(
                    query.getString(1),
                    query.getString(2),
                    query.getString(3)
            );
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't read the user's data", e);
        }
    }
}
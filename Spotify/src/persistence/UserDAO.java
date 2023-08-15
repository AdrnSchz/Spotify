package persistence;

import business.entities.User;
import persistence.exceptions.PersistenceException;

/**
 * DAO interface to manage {@link User}  that defines the methods that must be implemented by the persistence layer.
 */
public interface UserDAO {
    void createUser(User user) throws PersistenceException;

    boolean deleteUser(String username) throws PersistenceException;

    User getUser(String user) throws PersistenceException;
}

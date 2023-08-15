package persistence;

import persistence.exceptions.PersistenceException;

/**
 * DAO interface to obtain Lyrics that defines the methods that must be implemented by the persistence layer.
 */
public interface LyricsDAO {

    String fetchLyrics(String artist, String title) throws PersistenceException;
}

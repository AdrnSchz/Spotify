package persistence;

import business.entities.Song;
import persistence.exceptions.PersistenceException;

import javax.sound.sampled.AudioInputStream;
import java.util.List;

/**
 * DAO interface to manage {@link Song}  that defines the methods that must be implemented by the persistence layer.
 */
public interface SongDAO {
    void createSong(Song song, String songPath) throws PersistenceException;

    void deleteSong(int songId) throws PersistenceException;

    void deleteSongsByUser(String user) throws PersistenceException;

    Song getSong(int id) throws PersistenceException;

    List<Song> getSongs(String title) throws PersistenceException;

    List<Song> getAllSongs() throws PersistenceException;

    AudioInputStream getAudio(int songId) throws PersistenceException;
}
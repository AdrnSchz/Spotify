package persistence;

import business.entities.Playlist;
import persistence.exceptions.PersistenceException;

import java.util.List;

/**
 * DAO interface to manage {@link Playlist}  that defines the methods that must be implemented by the persistence layer.
 */
public interface PlaylistDAO {
    void createPlaylist(Playlist playlist) throws PersistenceException;

    void deletePlaylist(int playlistId) throws PersistenceException;

    void deletePlaylistsByUser(String user) throws PersistenceException;

    boolean addSong(int playlistId, int songId) throws PersistenceException;

    void removeSong(int playlistId, int songId) throws PersistenceException;

    void setPosition(int playlistId, int songId, int position) throws PersistenceException;

    List<Playlist> getPlaylists() throws PersistenceException;

    Playlist getPlaylistWithSongs(int id) throws PersistenceException;
}

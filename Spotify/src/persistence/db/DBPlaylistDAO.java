package persistence.db;

import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAO;
import persistence.exceptions.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for {@link Playlist} entity that connects {@link PlaylistDAO} with the database.
 *
 * @author Group 6
 * @version 1.0
 */
public class DBPlaylistDAO implements PlaylistDAO {

    /**
     * Database instance to communicate with the database.
     */
    private final Database db;

    /**
     * Constructor method that initializes the db attribute.
     *
     * @param db database instance to communicate with the database.
     */
    public DBPlaylistDAO(Database db) {
        this.db = db;
    }

    /**
     * Method that creates a new Playlist in the database.
     *
     * @param playlist playlist with the data to add.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public void createPlaylist(Playlist playlist) throws PersistenceException {

        db.update("INSERT INTO playlist(title, owner, description) VALUES('%s','%s','%s')".formatted(
                playlist.getName().replaceAll("'", "''"),
                playlist.getOwner().replaceAll("'", "''"),
                playlist.getDescription().replaceAll("'", "''")
        ));
    }

    /**
     * Method that deletes a playlist from the database.
     *
     * @param playlistId id of the playlist to delete.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public void deletePlaylist(int playlistId) throws PersistenceException {
        db.update("DELETE FROM playlist_song WHERE playlist_id = %d".formatted(playlistId));
        db.update("DELETE FROM playlist WHERE id = %d".formatted(playlistId));
    }

    /**
     * Method that deletes all playlists created by a specific user.
     *
     * @param user user whose playlists will be deleted.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public void deletePlaylistsByUser(String user) throws PersistenceException {
        try (ResultSet query = db.query("SELECT id FROM playlist WHERE owner LIKE '%s'".formatted(user.replaceAll("'", "''")))) {
            while (query.next()) {
                deletePlaylist(query.getInt(1));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't read the playlists' data", e);
        }
    }

    /**
     * Method that adds a song to a playlist.
     *
     * @param playlistId id of the playlist to add the song to.
     * @param songId id of the song to add to the playlist.
     * @return true if the song was added, false if it was already in the playlist.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public boolean addSong(int playlistId, int songId) throws PersistenceException {

        try (ResultSet query = db.query("SELECT * FROM playlist_song WHERE playlist_id = %d AND song_id = %d".formatted(playlistId, songId))) {
            query.next();
            if (0 < query.getRow()) {
                return false;
            }
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't access the playlist's songs", e);
        }

        int position;
        try (ResultSet query = db.query("SELECT COUNT(*) FROM playlist_song")) {
            query.next();
            position = query.getInt(1);
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't assign the song position in the playlist", e);
        }

        db.update("INSERT INTO playlist_song (playlist_id, song_id, position) VALUES(%d,%d,%d)".formatted(playlistId, songId, position));
        return true;
    }

    /**
     * Method that removes a song from a playlist.
     *
     * @param playlistId id of the playlist to remove the song from.
     * @param songId id of the song to remove from the playlist.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public void removeSong(int playlistId, int songId) throws PersistenceException {
        db.update("DELETE FROM playlist_song WHERE playlist_id = %d AND song_id = %d".formatted(playlistId, songId));
    }

    /**
     * Method that changes the position of a song in a playlist.
     *
     * @param playlistId id of the playlist to change the song's position in.
     * @param songId id of the song to change the position of.
     * @param position new position of the song.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public void setPosition(int playlistId, int songId, int position) throws PersistenceException {
        db.update("UPDATE playlist_song SET position = %d WHERE playlist_id = %d AND song_id = %d"
                .formatted(position, playlistId, songId));
    }

    /**
     * Method that returns all the playlists in the database.
     * Their songs are not fetched.
     *
     * @return list of all the playlists in the database.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public List<Playlist> getPlaylists() throws PersistenceException {

        List<Playlist> playlists = new ArrayList<>();

        try (ResultSet query = db.query("SELECT * FROM playlist")) {
            while (query.next()) {
                playlists.add(new Playlist(
                        query.getInt(1),
                        query.getString(2),
                        query.getString(3),
                        query.getString(4)
                ));
            }
            return playlists.size() > 0 ? playlists : null;
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't read the playlists' data", e);
        }
    }

    /**
     * Method that returns a playlist with all its songs.
     *
     * @param id id of the playlist to fetch.
     * @return playlist with all its songs.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public Playlist getPlaylistWithSongs(int id) throws PersistenceException {

        List<Playlist> playlists = getPlaylists();

        if (playlists == null) {
            return null;
        }

        Playlist playlist = playlists.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if (playlist != null) {
            try (ResultSet querySongs = db.query(
                    "SELECT * FROM song s JOIN playlist_song ps ON ps.song_id = s.id WHERE ps.playlist_id = %d ORDER BY ps.position"
                            .formatted(playlist.getId()))) {
                while (querySongs.next()) {
                    playlist.addSong(new Song(
                            querySongs.getInt(1),
                            querySongs.getString(2),
                            querySongs.getString(3),
                            querySongs.getString(4),
                            querySongs.getString(5),
                            querySongs.getFloat(6),
                            querySongs.getString(7),
                            querySongs.getInt(10)
                    ));
                }
            } catch (SQLException e) {
                throw new PersistenceException("Couldn't read the playlist's songs' data", e);
            }
        }
        return playlist;
    }
}
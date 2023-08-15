package persistence.db;

import business.entities.Song;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;
import persistence.SongDAO;
import persistence.exceptions.PersistenceException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * DAO class for {@link Song} entity that connects {@link SongDAO} with the database.
 *
 * @author Group 6
 * @version 1.0
 */
public class DBSongDAO implements SongDAO {

    /**
     * Constant path to the song directory.
     */
    private static final Path SONGS_DIR = Paths.get("files/songs/");
    /**
     * Database instance to communicate with the database.
     */
    private final Database db;

    /**
     * Constructor method that initializes the db attribute.
     *
     * @param db database instance to communicate with the database.
     * @throws PersistenceException if there was an error creating the song directory.
     */
    public DBSongDAO(Database db) throws PersistenceException {

        this.db = db;

        try {
            Files.createDirectories(SONGS_DIR);
        } catch (IOException e) {
            throw new PersistenceException("Failed to create the songs directory", e);
        }
    }

    /**
     * Method that stores a song in the database and copies the song file to the song directory.
     *
     * @param song song to store.
     * @param songPath path to the song file.
     * @throws PersistenceException if there was an error in the database or with the file system.
     */
    @Override
    public void createSong(Song song, String songPath) throws PersistenceException {

        Path tmpFile = SONGS_DIR.resolve("tmp.wav").toAbsolutePath();

        if (songPath.endsWith(".mp3")) {
            Converter converter = new Converter();
            try {
                converter.convert(songPath, tmpFile.toString());
            } catch (JavaLayerException e) {
                throw new PersistenceException("Failed to convert the mp3 file", e);
            }
        }
        else if (songPath.endsWith(".wav")) {
            Path songFile = Paths.get(songPath);
            try {
                Files.copy(songFile, tmpFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new PersistenceException("Failed to import song file", e);
            }
        }
        else {
            throw new PersistenceException("Unsupported file format, only .mp3 and .wav are supported");
        }

        File songFile = new File(tmpFile.toString());

        float duration;
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile)) {
            AudioFormat format = audioInputStream.getFormat();
            duration = (songFile.length() / (format.getFrameSize() * format.getFrameRate()));
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new PersistenceException("Failed to read song file", e);
        }

        db.update(
                String.format(
                        Locale.UK,
                        "INSERT INTO song(title, artist, album, genre, duration, owner) VALUES('%s','%s','%s','%s',%f,'%s')",
                        song.getTitle().replaceAll("'", "''"),
                        song.getArtist().replaceAll("'", "''"),
                        song.getAlbum().replaceAll("'", "''"),
                        song.getGenre(),
                        duration,
                        song.getOwner().replaceAll("'", "''")
                ));

        List<Song> songs = getSongs(song.getTitle());
        int songId = songs.get(songs.size() - 1).getId();

        try {
            Files.move(tmpFile, tmpFile.resolveSibling(songId + ".wav"));
        } catch (IOException e) {
            deleteSong(songId);
            try {
                Files.deleteIfExists(tmpFile);
            } catch (IOException ignored) {}

            throw new PersistenceException("Failed to internally rename song file", e);
        }
    }

    /**
     * Method that deletes a song from the database and the song file from the song directory.
     *
     * @param songId id of the song to delete.
     * @throws PersistenceException if there was an error in the database or with the file system.
     */
    @Override
    public void deleteSong(int songId) throws PersistenceException {

        File song = new File(SONGS_DIR.resolve(songId + ".wav").toUri());

        db.update("DELETE FROM playlist_song WHERE song_id = %d".formatted(songId));
        db.update("DELETE FROM song WHERE id = %d".formatted(songId));
    }

    /**
     * Method that deletes the songs created by a given user.
     *
     * @param user user whose songs will be deleted.
     * @throws PersistenceException if there was an error in the database or with the file system.
     */
    @Override
    public void deleteSongsByUser(String user) throws PersistenceException {
        try (ResultSet query = db.query("SELECT id FROM song WHERE owner LIKE '%s'".formatted(user.replaceAll("'", "''")))) {
            while (query.next()) {
                deleteSong(query.getInt(1));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't read the songs' data", e);
        }
    }

    /**
     * Method that fetches a song from the database.
     *
     * @param id id of the song to fetch.
     * @return the fetched song, null if no song found.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public Song getSong(int id) throws PersistenceException {
        List<Song> songs = fetchSongs(null, id);
        return songs.isEmpty() ? null : songs.get(0);
    }

    /**
     * Method that fetches songs matching the given title from the database.
     *
     * @param title title of the songs to fetch.
     * @return List with the fetched songs, null if no songs found.
     * @throws PersistenceException
     */
    @Override
    public List<Song> getSongs(String title) throws PersistenceException {
        List<Song> songs = fetchSongs(title, -1);
        return songs.isEmpty() ? null : songs;
    }

    /**
     * Method that fetches all the songs from the database.
     *
     * @return List with the fetched songs, null if no songs found.
     * @throws PersistenceException if there was an error in the database.
     */
    @Override
    public List<Song> getAllSongs() throws PersistenceException {
        List<Song> songs = fetchSongs("", -1);
        return songs.isEmpty() ? null : songs;
    }

    /**
     * Private method that performs the query to fetch songs from the database.
     *
     * @param title title of the songs to fetch, if null, id is used.
     * @param id id of the song to fetch, if title is not used.
     * @return List with the fetched songs.
     * @throws PersistenceException if there was an error in the database.
     */
    private List<Song> fetchSongs(String title, int id) throws PersistenceException {

        List<Song> songs = new ArrayList<>();

        String queryStr = "SELECT * FROM song WHERE ";
        if (title == null) {
            queryStr += "id = " + id;
        }
        else {
            queryStr += "title LIKE '%%%s%%'".formatted(title.replaceAll("'", "''"));
        }

        try (ResultSet query = db.query(queryStr)) {
            while (query.next()) {
                songs.add(new Song(
                        query.getInt(1),
                        query.getString(2),
                        query.getString(3),
                        query.getString(4),
                        query.getString(5),
                        query.getFloat(6),
                        query.getString(7)
                ));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Couldn't read the songs' data", e);
        }
        return songs;
    }

    /**
     * Method that fetches the audio data of a song from the song directory.
     *
     * @param songId id of the song whose audio data will be fetched.
     * @return AudioInputStream with the audio data of the song.
     * @throws PersistenceException if there was an error with the file system.
     */
    @Override
    public AudioInputStream getAudio(int songId) throws PersistenceException {

        File songWav = new File(SONGS_DIR.toAbsolutePath() + "/" + songId + ".wav");

        try {
            return AudioSystem.getAudioInputStream(songWav);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new PersistenceException("Failed to read song audio data", e);
        }
    }
}
package business;

import business.entities.Song;
import business.entities.Statistic;
import business.exceptions.BusinessException;
import persistence.LyricsDAO;
import persistence.SongDAO;
import persistence.api.APILyricsDAO;
import persistence.db.DBSongDAO;
import persistence.db.Database;
import persistence.exceptions.PersistenceException;

import javax.sound.sampled.AudioInputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Manager of the Songs.
 *
 * @author Group 6
 * @version 1.0
 */
public class SongManager {

    /**
     * SongDAO instance to retrieve and manage the song.
     */
    private final SongDAO songDAO;
    /**
     * LyricsDAO instance to retrieve and manage the lyrics.
     */
    private final LyricsDAO lyricsDAO;

    /**
     * Constructor method for SongManager.
     *
     * @param db Database instance to be used.
     * @throws BusinessException if there's an error with the database.
     */
    public SongManager(Database db) throws BusinessException {
        try {
            songDAO = new DBSongDAO(db);
            lyricsDAO = new APILyricsDAO();
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Adds a song to the database.
     *
     * @param song Song to be added.
     * @param path Path of the song to be added.
     * @throws BusinessException if there's an error when adding the song.
     */
    public void addSong(Song song, String path) throws BusinessException {
        try {
            songDAO.createSong(song, path);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Retrieves all the songs from the database.
     *
     * @return List of songs in the database.
     * @throws BusinessException if there's an error with the database.
     */
    public List<String[]> getAllSongs() throws BusinessException {
        try {
            List<String[]> list = new ArrayList<>();
            List<Song> listSongs = songDAO.getAllSongs();
            if(listSongs != null) {
                for (int i = 0; i < listSongs.size(); i++) {
                    String[] song = new String[6];
                    song[0] = String.valueOf(listSongs.get(i).getId());
                    song[1] = listSongs.get(i).getTitle();
                    song[2] = listSongs.get(i).getGenre();
                    song[3] = listSongs.get(i).getAlbum();
                    song[4] = listSongs.get(i).getArtist();
                    song[5] = listSongs.get(i).getOwner();
                    list.add(song);
                }
            }
            return list;
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Deletes a song through its id from the database.
     *
     * @param song Song to delete
     * @throws BusinessException if there's an error when deleting the song.
     */
    public void deleteSong(Song song) throws BusinessException {
        try {
            songDAO.deleteSong(song.getId());
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Deletes all the user's songs from the database.
     *
     * @param username the user's username.
     * @throws BusinessException if there's an error when deleting the songs.
     */
    public void deleteUserSongs(String username) throws BusinessException {
        try {
            songDAO.deleteSongsByUser(username);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Retrieves all the lyrics from a song
     *
     * @param song Song to get the lyrics from.
     * @return the song's lyrics.
     */
    public String getLyrics(Song song) {
        try {
            return lyricsDAO.fetchLyrics(song.getArtist(), song.getTitle());
        } catch (PersistenceException e) {
            return e.getMessage();
        }
    }

    /**
     * Generates the stats of all the songs in the database.
     *
     * @return List of statistics of all the songs in the database.
     */
    public ArrayList<Statistic> getStats() {
        ArrayList<Statistic> stats = new ArrayList<>();
        List<Song> songs;
        boolean found = false;
        try {
            songs = songDAO.getAllSongs();
        } catch (PersistenceException e) {
            songs = null;
        }
        if (songs == null) {
            return null;
        }

        for (int i = 0; i < songs.size(); i++) {
            for (int j = 0; j < stats.size(); j++) {
                if (songs.get(i).getGenre().equals(stats.get(j).getGenre())) {
                    stats.get(j).addOne();
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
            } else {
                stats.add(new Statistic(songs.get(i).getGenre(), 1));
            }
        }

        stats.sort((o1, o2) -> o2.getNumSongs() - o1.getNumSongs());
        return stats;
    }

    /**
     * Gets a song through the id.
     *
     * @param id the song's id.
     * @return the song.
     * @throws BusinessException if there's an error when getting the song.
     */
    public Song getSong(int id) throws BusinessException{
        try {
            return songDAO.getSong(id);
        }
        catch (PersistenceException e){
            throw new BusinessException(e);
        }
    }

    /**
     * Gets the audio from song through the id.
     *
     * @param id the song's id.
     * @return the audio of the song.
     * @throws PersistenceException if there's an error when getting the song.
     */
    public AudioInputStream getSongAudio(int id) throws PersistenceException {
        return songDAO.getAudio(id);
    }
}

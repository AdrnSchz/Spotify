package business;

import business.entities.Playlist;
import business.entities.Song;
import business.exceptions.BusinessException;
import persistence.exceptions.PersistenceException;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Runnable class representing a Player.
 *
 * @author Group 6
 * @version 1.0
 */
public class Player implements Runnable, Observable, LineListener {

    /**
     * Constants representing the different states of the player.
     */
    public static final String SONG_START = "SONG_START";
    public static final String SONG_RESUME = "SONG_RESUME";
    public static final String SONG_PAUSE = "SONG_PAUSE";
    public static final String ORIGIN_CHANGE = "ORIGIN_CHANGE";
    public static final String STOP = "STOP";
    /**
     * SongManager instance to obtain audio data from songs.
     */
    private final SongManager songManager;
    /**
     * Clip instance to play audio.
     */
    private final Clip clip;
    /**
     * Queue of songs to play.
     */
    private final LinkedList<Song> queue;
    /**
     * Current song being played.
     */
    private Song currentSong;
    /**
     * Current playlist being played.
     */
    private Playlist currentPlaylist;
    /**
     * Boolean values representing the different states of the player.
     */
    private boolean playlistLoop;
    private boolean songLoop;
    private boolean isPaused;
    /**
     * List of observers following the Observer pattern.
     */
    private final List<Observer> observers;

    /**
     * Constructor method of Player.
     * Initializes the queue, observers, clip attributes and starts a new thread.
     *
     * @param songManager SongManager instance to obtain audio data from songs.
     * @throws BusinessException if there was an error initializing the audio service.
     */
    public Player(SongManager songManager) throws BusinessException {
        this.songManager = songManager;
        this.queue = new LinkedList<>();
        this.observers = new ArrayList<>();

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new BusinessException("Failed to initialise playback service", e);
        }

        clip.addLineListener(this);
        new Thread(this).start();
    }

    /**
     * LineListener method that is called when a line event occurs.
     *
     * @param event a line event that describes the change
     */
    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            if (isPaused) {
                return;
            }
            synchronized (clip) {
                clip.notifyAll();
            }
        }
    }

    /**
     * Thread run method that waits, closes and plays next song when required.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {

        while (true) {
            try {
                synchronized (clip) {
                    clip.wait();
                }
            } catch (InterruptedException e) {
                continue;
            }

            clip.close();

            playNextSong();
        }
    }

    /**
     * Method that attaches an observer to the observers list.
     *
     * @param o observer to attach
     */
    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    /**
     * Method that detaches an observer from the observers list.
     *
     * @param o observer to detach
     */
    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    /**
     * Method that notifies all observers of a change.
     *
     * @param message message to send to observers.
     */
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message, currentSong);
        }
    }

    /**
     * Method that starts playing the next song in the queue.
     * It checks the current player state and acts accordingly.
     */
    public void playNextSong() {

        isPaused = false;

        if (!songLoop) {

            if (queue.isEmpty() && playlistLoop) {
                queue.addAll(currentPlaylist.getSongs());
            }

            currentSong = queue.poll();
        }

        if (currentSong == null) {
            stop();
            return;
        }

        AudioInputStream songAudio;
        try {
            songAudio = songManager.getSongAudio(currentSong.getId());
        } catch (PersistenceException e) {
            notifyObservers("Failed to retrieve audio data from song");
            return;
        }

        notifyObservers(SONG_START);

        try {
            clip.open(songAudio);
            clip.start();
        } catch (LineUnavailableException | IOException e) {
            notifyObservers("Failed to start song playback");
        }
    }

    /**
     * Method that adds a song to queue, or songs if a playlist is specified, and starts playing.
     *
     * @param song song to add.
     * @param playlist playlist to add.
     */
    public void queue(Song song, Playlist playlist) {

        queue.clear();

        if (playlist == null) {
            currentPlaylist = null;
            notifyObservers(ORIGIN_CHANGE);
            queue.add(song);
        }
        else {

            if (currentPlaylist == null || playlist.getId() != currentPlaylist.getId()) {
                notifyObservers(ORIGIN_CHANGE);
            }

            currentPlaylist = playlist;
            loadFromSong(song);
        }

        synchronized (clip) {
            clip.notifyAll();
        }
    }

    /**
     * Method that queues the playlist songs from the current song position.
     *
     * @param playlist playlist to queue.
     */
    public void queue(Playlist playlist) {

        queue.clear();

        currentPlaylist = playlist;
        loadFromSong(currentSong);
        queue.pop();
    }

    /**
     * Method that queues songs of the current playlist from the song parameter's position.
     *
     * @param song starting song.
     */
    private void loadFromSong(Song song) {

        List<Song> songs = currentPlaylist.getSongs();

        boolean found = false;
        for (Song s : songs) {
            if (s.getId() == song.getId()) {
                found = true;
            }
            if (found) {
                queue.add(s);
            }
        }
    }

    /**
     * Method that informs the thread to skip to next song.
     */
    public void nextSong() {
        synchronized (clip) {
            clip.notifyAll();
        }
    }

    /**
     * Method that plays the previous song, if possible.
     */
    public void previousSong() {

        songLoop = false;

        if (currentPlaylist == null) {
            clip.setMicrosecondPosition(0);
            notifyObservers(SONG_START);
            return;
        }

        List<Song> songs = currentPlaylist.getSongs();
        int previousSong = songs.indexOf(currentSong) - 1;
        if (previousSong >= 0) {
            queue.addFirst(currentSong);
            queue.addFirst(songs.get(previousSong));
            synchronized (clip) {
                clip.notifyAll();
            }
        }
        else {
            clip.setMicrosecondPosition(0);
            notifyObservers(SONG_START);
        }
    }

    /**
     * Method that stops the current song and clears the queue.
     */
    public void stop() {
        isPaused = true;
        clip.close();

        currentSong = null;
        currentPlaylist = null;

        notifyObservers(STOP);
    }

    /**
     * Method that sets the position of the current song.
     *
     * @param second position to set to.
     */
    public void setPosition(int second) {
        clip.setMicrosecondPosition(second * 1000000L);
    }

    /**
     * Method that toggles the pause state of the player.
     */
    public void togglePause() {
        if (clip.isRunning()) {
            isPaused = true;
            clip.stop();
            notifyObservers(SONG_PAUSE);
        } else {
            isPaused = false;
            clip.start();
            notifyObservers(SONG_RESUME);
        }
    }

    /**
     * Method that toggles the playlist loop state.
     */
    public void togglePlaylistLoop() {
        playlistLoop = !playlistLoop;
    }

    /**
     * Method that toggles the song loop state.
     */
    public void toggleSongLoop() {
        songLoop = !songLoop;
    }

    /**
     * Method that returns whether a playlist is queued.
     * @return whether a playlist is queued.
     */
    public boolean isPlaylistQueued() {
        return currentPlaylist != null;
    }

    /**
     * Method that returns whether the player is playing.
     * @return whether the player is playing.
     */
    public boolean isPlaying() {
        return !isPaused;
    }

    /**
     * Getter method that returns the current song.
     * @return current song.
     */
    public Song getCurrentSong() {
        return currentSong;
    }

    /**
     * Getter method that returns the current position in the song.
     * @return current position in the song.
     */
    public double getPosition() {
        return clip.getMicrosecondPosition() / 1000000D;
    }

    /**
     * Getter method that returns the playlist of the song that is playing.
     * @return current playlist of the song playing, returns null otherwise.
     */
    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     * Method that returns the given time in MM:SS format.
     *
     * @param time time to convert in seconds.
     * @return time in MM:SS format.
     */
    public String getTimeString(float time) {

        int minutes = (int) Math.floor(time / 60);
        int seconds = Math.round(time - (minutes * 60));

        return  (minutes > 9 ?
                String.valueOf(minutes) :
                '0' + String.valueOf(minutes)) +
                ':' +
                (seconds > 9 ?
                        String.valueOf(seconds) :
                        '0' + String.valueOf(seconds));
    }
}
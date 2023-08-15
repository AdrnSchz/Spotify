package presentation.controllers.media.player;

import business.Player;
import business.entities.Song;
import presentation.Globals;
import business.Observer;
import presentation.controllers.FrameController;
import presentation.views.media.player.PlayerUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Controller class for the PlayerUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class PlayerController extends Thread implements ActionListener, ChangeListener, MouseListener, Observer {

    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;
    /**
     * Player instance to play songs.
     */
    private final Player player;
    /**
     * PlayerUI instance to display the player.
     */
    private final PlayerUI ui;
    /**
     * Seconds elapsed since the start of the song.
     */
    private int secondsElapsed;
    /**
     * Boolean values representing the different states of the player.
     */
    private boolean autoUpdateElapsed;
    private boolean isPlaying;

    /**
     * Constructor method for PlayerController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param player Player instance to play songs.
     */
    public PlayerController(FrameController frameController, Player player) {
        this.ui = new PlayerUI();
        this.frameController = frameController;
        this.player = player;

        ui.registerListener(this);
        frameController.setPlayer(ui);
        player.attach(this);
        start();
    }

    /**
     * Method to load a song into the player.
     *
     * @param song Song to be loaded.
     */
    private void loadSong(Song song) {

        ui.setSong(song.getTitle(), song.getArtist(), player.getTimeString(song.getDuration()));
        ui.resetSlider((int) Math.floor(song.getDuration()));

        if (!player.isPlaylistQueued()) {
            ui.disablePlaylistLoop();
        }

        synchronized (ui) {
            secondsElapsed = 0;
            ui.notifyAll();
        }
    }

    /**
     * Thread run method that updates the elapsed time and the player slider.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {

        while (true) {

            synchronized (ui) {
                try {
                    ui.wait();
                } catch (InterruptedException e) {
                    // Shouldn't be interrupted here, skip to next iteration to wait again
                    continue;
                }
            }

            isPlaying = true;
            autoUpdateElapsed = true;

            secondsElapsed = (int) Math.round(player.getPosition());
            SwingUtilities.invokeLater(() -> {
                ui.setSliderValue(secondsElapsed);
                ui.setTimeElapsed(player.getTimeString(secondsElapsed));
            });

            while (isPlaying) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    continue;
                }

                if (isPlaying && autoUpdateElapsed) {
                    SwingUtilities.invokeLater(() -> {
                        ui.setSliderValue(++secondsElapsed);
                        ui.setTimeElapsed(player.getTimeString(secondsElapsed));
                    });
                }
            }
        }
    }

    /**
     * Observer update method to update the player.
     *
     * @param message Message received.
     * @param song Song currently playing.
     */
    @Override
    public void update(String message, Song song) {
        SwingUtilities.invokeLater(() -> {
            switch (message) {
                case Player.SONG_START -> loadSong(song);
                case Player.ORIGIN_CHANGE -> {
                    ui.resetSongLoop();
                    ui.resetPlaylistLoop();
                }
                case Player.SONG_PAUSE -> {
                    isPlaying = false;
                    this.interrupt();

                    ui.pause();
                }
                case Player.SONG_RESUME -> {
                    synchronized (ui) {
                        ui.notifyAll();
                        ui.play();
                    }
                }
                case Player.STOP -> {
                    isPlaying = false;
                    ui.removeSong();
                }
                default -> {
                    isPlaying = false;
                    ui.removeSong();
                    frameController.showError(message);
                }
            }
        });
    }

    /**
     * ActionListener method to process the different button clicks.
     *
     * @param event the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case Globals.JB_SONG_LOOP -> {
                ui.toggleLoopSong();
                player.toggleSongLoop();
            }
            case Globals.JB_PLAYLIST_LOOP -> {
                ui.toggleLoopPlaylist();
                player.togglePlaylistLoop();
            }
            case Globals.JB_PREVIOUS_SONG -> {
                // exit sleep loop to wait for song start
                isPlaying = false;
                this.interrupt();

                ui.resetSongLoop();
                player.previousSong();
            }
            case Globals.JB_PLAY_PAUSE -> player.togglePause();
            case Globals.JB_NEXT_SONG -> {
                // exit sleep loop to wait for song start
                isPlaying = false;
                this.interrupt();

                player.nextSong();
            }
            case Globals.JB_STOP -> player.stop();
        }
    }

    /**
     * ChangeListener method to process the slider changes.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == ui.getSlider()) {
            secondsElapsed = ui.getSliderValue();
            ui.setTimeElapsed(player.getTimeString(secondsElapsed));
        }
    }

    /**
     * MouseListener method to process when the slider is released.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == ui.getSlider()) {
            player.setPosition(secondsElapsed);
            this.interrupt(); // exit thread count sleep, if sleeping, to start counting again
            autoUpdateElapsed = true;
        }
    }

    /**
     * MouseListener method to process when the slider is pressed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == ui.getSlider()) {
            autoUpdateElapsed = false;
        }
    }

    /**
     * MouseListener method to process when the slider is clicked.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

       if (e.getSource() == ui.getSongRepeatLabel() && ui.isSongRepeatEnabled()) {
           ui.toggleLoopSong();
           player.toggleSongLoop();
       }
       else if (e.getSource() == ui.getPlaylistRepeatLabel() && ui.isPlaylistRepeatEnabled()) {
           ui.toggleLoopPlaylist();
           player.togglePlaylistLoop();
       }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void update() {}
}
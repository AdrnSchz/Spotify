package presentation.controllers.media;

import business.*;
import business.entities.Song;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.media.SongDetailUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Controller class for the SongDetailsUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class SongDetailsController implements ActionListener, Observer, Observable {
    /**
     * SongManager instance to retrieve the song information.
     */
    private final SongManager songManager;
    /**
     * SongDetailUI instance to display the the information about a song.
     */
    private final SongDetailUI ui;
    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;
    /**
     * Player instance to play/pause the song.
     */
    private final Player player;
    /**
     * PlaylistManager instance to add songs to a playlists.
     */
    private final PlaylistManager playlistManager;
    /**
     * UserManager instance to retrieve the user information.
     */
    private final UserManager userManager;
    /**
     * Song instance representing the selected song.
     */
    private Song selectedSong;
    /**
     * ArrayList of Observers to know and notify when a song is paused/played/started.
     */
    private final ArrayList<Observer> listeners = new ArrayList<>();

    /**
     * Constructor method for SongDetailsController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param songManager SongManager instance to retrieve the song information.
     * @param player Player instance to play/pause the song.
     * @param playlistManager PlaylistManager instance to add songs to a playlists.
     * @param userManager UserManager instance to retrieve the user information.
     */
    public SongDetailsController(FrameController frameController, SongManager songManager, Player player,
                                 PlaylistManager playlistManager, UserManager userManager) {
        this.frameController = frameController;
        this.songManager = songManager;
        this.ui = new SongDetailUI();
        this.player = player;
        this.playlistManager = playlistManager;
        this.userManager = userManager;
        frameController.addCard(ui, Globals.SONG_DETAILS);
        ui.setName(Globals.SONG_DETAILS);
        ui.setListeners(this);
        player.attach(this);
    }

    /**
     * Method to update the view with the information of the selected son.
     *
     * @param id int representing the id of the selected song.
     */
    public void setSong(int id) {
        try {
            this.selectedSong = songManager.getSong(id);
        } catch (BusinessException e) {
            frameController.showError(e.getMessage());
        }
        String lyrics = songManager.getLyrics(selectedSong);
        if (lyrics == null) {
            lyrics = "Sorry. There are no lyrics available for this song";
        }
        ui.setSongInfo(selectedSong.getTitle(), selectedSong.getArtist(), selectedSong.getAlbum(),
                selectedSong.getGenre(), player.getTimeString(selectedSong.getDuration()), selectedSong.getOwner(), lyrics);
        if (userManager.getCurrentUser().equals(selectedSong.getOwner())) {
            ui.showDeleteButton();
        } else {
            ui.hideDeleteButton();
        }
        if (player.getCurrentSong() == null) {
            ui.setPlayButton();
        } else {
            if (selectedSong.getId() == player.getCurrentSong().getId()) {
                if (player.isPlaying()) {
                    ui.setPauseButton();
                } else {
                    ui.setPlayButton();
                }
            } else {
                ui.setPlayButton();
            }
        }
    }

    /**
     * ActionListener method to process the different button clicks.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Globals.JB_SETTINGS -> {
                frameController.setPreviousScreen(ui);
                frameController.swapScreen(ui, Globals.LOG_OUT);
            }
            case Globals.JB_DELETE-> {
                try {
                    songManager.deleteSong(selectedSong);
                    frameController.forgetCurrentScreen();
                    if (player.getCurrentSong() != null && player.getCurrentSong().getId() == selectedSong.getId()) {
                        player.stop();
                    }
                    notifyObservers("deleted song");
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            }
            case Globals.JB_ADD_TO_PLAYLIST-> {
                try {
                    ui.setPopup(playlistManager.getUserPlaylistsName(), frameController.getFrame());
                    ui.setPopListeners(this, playlistManager.getUserPlaylistsId());
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            }
            case Globals.JB_PLAY_PAUSE -> {
                if (player.getCurrentSong() == null) {
                    player.queue(selectedSong, null);
                } else {
                    if (selectedSong.getId() != player.getCurrentSong().getId()) {
                        player.queue(selectedSong, null);
                    } else {
                        player.togglePause();
                    }
                }
            }
            case Globals.JB_CONFIRM_PLAYLIST -> {
                for (Enumeration<AbstractButton> buttons = ui.getGroup().getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        try {
                            boolean isAdded = playlistManager.addSongToPlaylist(selectedSong.getId(),
                                    Integer.parseInt(button.getActionCommand()));
                            if (!isAdded) {
                                frameController.showError("Song already in playlist");
                            }
                            else{
                                notifyObservers("added song");
                            }
                        } catch (BusinessException ex) {
                            frameController.showError(ex.getMessage());
                        }
                    }
                }
                ui.getDialog().dispose();
            }
        }
    }

    /**
     * Observer update method to update the songDetails view.
     *
     * @param message Message received.
     * @param song Song currently playing.
     */
    @Override
    public void update(String message, Song song) {
        SwingUtilities.invokeLater(() -> {
            switch (message) {
                case Player.SONG_START, Player.SONG_RESUME -> {
                    if (selectedSong != null) {
                        if (song != null && song.getId() == selectedSong.getId()) {
                            ui.setPauseButton();
                        } else {
                            ui.setPlayButton();
                        }
                    }
                }
                case Player.SONG_PAUSE, Player.STOP -> {
                    if (selectedSong != null) {
                        ui.setPlayButton();
                    }
                }
            }
        });
    }

    @Override
    public void update() {}

    /**
     * Method to attach an observer.
     *
     * @param o Observer to be attached.
     */
    @Override
    public void attach(Observer o) {
        listeners.add(o);
    }

    /**
     * Method to detach an observer.
     *
     * @param o Observer to be detached.
     */
    @Override
    public void detach(Observer o) {
        listeners.remove(o);
    }

    /**
     * Method to notify other classes about an action made.
     *
     * @param message Message to be sent to the observers.
     */
    @Override
    public void notifyObservers(String message) {
        for (Observer listener : listeners) {
            listener.update();
        }
    }
}

package presentation.controllers.media;

import business.Observer;
import business.SongManager;
import business.entities.Song;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.media.AvailableSongsUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
/**
 * Controller class for the AvailableSongsUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class AvailableSongsController extends MouseAdapter implements Observer {
    /**
     * SongManager instance to retrieve the stored songs.
     */
    private final SongManager songManager;
    /**
     * AvailableSongsUI instance to display list of songs.
     */
    private final AvailableSongsUI availableSongsUI;
    /**
     * SongDetailsController instance to communicate with the SongDetails.
     */
    private final SongDetailsController songDetailsController;
    /**
     * FrameController instance to communicate with the MainFrame.
     */
    private final FrameController frameController;

    /**
     * Constructor method for AvailableSongsController.
     *
     * @param frameController FrameController instance to communicate with the MainFrame.
     * @param songManager SongManager instance to retrieve the stored songs.
     * @param songDetailsController SongDetailsController instance to communicate with the SongDetails.
     * @param playlistSongsController PlaylistSongsController instance to communicate with the PlaylistSongs.
     */
    public AvailableSongsController(SongManager songManager, FrameController frameController,
                                    SongDetailsController songDetailsController, PlaylistSongsController playlistSongsController) {
        this.songManager = songManager;
        availableSongsUI = new AvailableSongsUI();
        this.frameController = frameController;
        this.songDetailsController = songDetailsController;
        availableSongsUI.setName(Globals.AVAILABLE_SONGS);
        availableSongsUI.setListeners(this);
        availableSongsUI.setListeners(playlistSongsController);
        this.songDetailsController.attach(this);
        frameController.addCard(availableSongsUI, Globals.AVAILABLE_SONGS);
        playlistSongsController.attach(this);
    }

    /**
     * Method to update the songs table.
     */
    public void updateSongs() {
        try {
            List<String[]> listSongs = songManager.getAllSongs();
            Object[][] songs = new Object[listSongs.size()][6];
            availableSongsUI.updateTable(songs, listSongs);

        } catch (BusinessException e) {
            frameController.showError(e.getMessage());
        }
    }

    /**
     * Mouse Listener to go to the song details screen regarding the selected song.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        String id;
        if (e.getSource() == availableSongsUI.getSongsTable() && !frameController.getPreviousScreenTag().equals(Globals.PLAYLIST_SONGS)) {
            id = availableSongsUI.getSongAtPoint(e.getPoint());
            songDetailsController.setSong(Integer.parseInt(id));
            frameController.swapScreen(availableSongsUI, Globals.SONG_DETAILS);
            availableSongsUI.clearFields();
        }
    }

    @Override
    public void update(String message, Song song) {}

    /**
     * Method to update the songs table.
     */
    @Override
    public void update() {
        updateSongs();
    }

}
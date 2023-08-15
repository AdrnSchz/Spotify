package presentation.controllers.home;

import presentation.Globals;
import presentation.controllers.media.AvailableSongsController;
import presentation.controllers.FrameController;
import presentation.controllers.media.ListPlaylistsController;
import presentation.controllers.stats.StatsController;
import presentation.views.home.MainScreenUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Controller class for the MainScreenUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class MainScreenController extends MouseAdapter implements ActionListener {

    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;
    /**
     * MainScreenUI instance to display the main options.
     */
    private final MainScreenUI ui;
    /**
     * AvailableSongsController instance to communicate with the AvailableSongsUI.
     */
    private final AvailableSongsController availableSongsController;
    /**
     * ListPlaylistsController instance to communicate with the ListPlaylistsUI.
     */
    private final ListPlaylistsController listPlaylistsController;
    /**
     * StatsController instance to communicate with the StatsUI.
     */
    private final StatsController statsController;

    /**
     * Constructor method for MainScreenController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param listPlaylistsController ListPlaylistsController instance to communicate with the ListPlaylistsUI.
     * @param statsController StatsController instance to communicate with the StatsUI.
     * @param availableSongsController AvailableSongsController instance to communicate with the AvailableSongsUI.
     */
    public MainScreenController(FrameController frameController, ListPlaylistsController listPlaylistsController,
                                StatsController statsController, AvailableSongsController availableSongsController) {
        this.frameController = frameController;
        ui = new MainScreenUI();
        this.listPlaylistsController = listPlaylistsController;
        this.availableSongsController = availableSongsController;
        this.statsController = statsController;
        ui.setListeners(this);
        ui.setName(Globals.MAIN_SCREEN);
        frameController.addCard(ui, Globals.MAIN_SCREEN);
    }

    /**
     * ActionListener method to process the different button clicks.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case Globals.JB_TO_ADD_SONG -> frameController.swapScreen(ui, Globals.ADD_SONG);
            case Globals.JB_TO_CREATE_PLAYLIST -> frameController.swapScreen(ui, Globals.CREATE_PLAYLIST);
            case Globals.JB_TO_SONGS -> {
                availableSongsController.updateSongs();
                frameController.swapScreen(ui, Globals.AVAILABLE_SONGS);
            }
            case Globals.JB_TO_PLAYLISTS -> {
                listPlaylistsController.updatePlaylistsData();
                frameController.swapScreen(ui, Globals.AVAILABLE_PLAYLISTS);
            }
            case Globals.JB_TO_STATS -> {
                statsController.setStats();
                frameController.swapScreen(ui, Globals.STATS);
            }
        }
    }

    /**
     * MouseListener method to process when the cursor is selecting a button.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        ui.setButton((JButton) e.getSource(), Color.black, Globals.greenSpotify);
    }

    /**
     * MouseListener method to process when the cursor is no longer selecting a button.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        ui.setButton((JButton) e.getSource(), Color.white, Color.gray);
    }


}

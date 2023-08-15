package presentation.controllers.media;

import business.PlaylistManager;
import business.UserManager;
import business.entities.Playlist;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.media.CreatePlaylistUI;

import java.awt.*;
import java.awt.event.*;

/**
 * Controller class for the CreatePlaylistUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class CreatePlaylistController extends MouseAdapter implements ActionListener {
    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;
    /**
     * PlaylistManager instance to add a new playlists.
     */
    private final PlaylistManager playlistManager;
    /**
     * UserManager instance to retrieve the current user.
     */
    private final UserManager userManager;
    /**
     * PlayerUI instance to display the necessary fields to create a playlists.
     */
    private final CreatePlaylistUI ui;

    /**
     * Constructor method for PlayerController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param playlistManager PlaylistManager instance to add a new playlists.
     * @param userManager UserManager instance to retrieve the current user.
     */
    public CreatePlaylistController(FrameController frameController, PlaylistManager playlistManager, UserManager userManager) {
        this.frameController = frameController;
        this.playlistManager = playlistManager;
        this.userManager = userManager;
        ui = new CreatePlaylistUI();
        frameController.addCard(ui, Globals.CREATE_PLAYLIST);
        ui.setListeners(this);
        ui.setName(Globals.CREATE_PLAYLIST);
    }

    /**
     * Method that clears the entered fields
     *
     */
    public void clearFields() {
        ui.clearFields();
    }

    /**
     * Method that checks if all the given fields were entered
     *
     * @return boolean indicating if none of the fields are empty
     */
    public boolean emptyFields() {
        return (ui.getNameField().equals("") || ui.getDescription().equals(""));
    }

    /**
     * ActionListener method to process the button click.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Globals.JB_CREATE_PLAYLIST.equals(e.getActionCommand())) {
            if (!emptyFields()) {
                try {
                    Playlist playlist = new Playlist(ui.getNameField(), userManager.getCurrentUser(), ui.getDescription());
                    playlistManager.createPlaylist(playlist);
                    frameController.showMessage("Playlist successfully created");
                    frameController.swapScreen(ui, Globals.MAIN_SCREEN);
                    ui.clearFields();
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            } else {
                frameController.showError("All fields are required");
            }
        }
    }

    /**
     * MouseListener method to process when the cursor is selecting one of the buttons
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        ui.setCreatePlaylistForeground(Color.black);
    }

    /**
     * MouseListener method to process when the cursor is no longer selecting one of the buttons
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        ui.setCreatePlaylistForeground(Color.white);
    }

}

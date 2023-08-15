package presentation.controllers.user;

import business.Player;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.user.LogOutUI;

import java.awt.*;
import java.awt.event.*;

/**
 * Controller class for the LogOutUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class LogOutController extends MouseAdapter implements ActionListener {
    /**
     * UserManager instance to retrieve and set the current user.
     */
    private final UserManager userManager;
    /**
     * SongManager instance to delete the songs created by the user to be deleted.
     */
    private final SongManager songManager;
    /**
     * PlaylistManager instance to delete the playlists created by the user to be deleted.
     */
    private final PlaylistManager playlistManager;
    /**
     * Player instance to stop playing songs.
     */
    private final Player player;
    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;
    /**
     * LogOutUI instance to display the logOut and deleteAccount options.
     */
    private final LogOutUI ui;

    /**
     * Constructor method for PlayerController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param userManager UserManager instance to retrieve and set the current user.
     * @param songManager SongManager instance to delete the songs created by the user to be deleted.
     * @param playlistManager PlaylistManager instance to delete the playlists created by the user to be deleted.
     * @param player Player instance to stop playing songs.
     */
    public LogOutController(FrameController frameController, UserManager userManager, SongManager songManager,
                            PlaylistManager playlistManager, Player player) {
        this.frameController = frameController;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
        this.player = player;
        ui = new LogOutUI();
        ui.setListeners(this);
        frameController.addCard(ui, Globals.LOG_OUT);
        ui.setName(Globals.LOG_OUT);
    }

    /**
     * Method to delete the current user.
     *
     * @throws BusinessException if there was an error deleting the user.
     */
    public boolean deleteUser() throws BusinessException {
        String user = userManager.getCurrentUser();
        songManager.deleteUserSongs(user);
        playlistManager.deleteUserPlaylists(user);
        if (userManager.deleteUser(user)) {
            logOut();
            return true;
        }
        return false;
    }

    /**
     * Method to log the current user out.
     *
     */
    public void logOut() {
        player.stop();
        frameController.togglePlayer();
        userManager.setCurrentUser("");
    }

    /**
     * ActionListener method to process the different button clicks.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Globals.JB_DELETE_ACCOUNT -> {
                try {
                    if(deleteUser()){
                        frameController.showMessage("Account successfully deleted");
                        frameController.swapScreen(ui, Globals.MENU);
                    }
                    else{
                        frameController.showError("Cannot delete account. Try again later");
                    }
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            }
            case Globals.JB_LOG_OUT -> {
                logOut();
                frameController.swapScreen(ui, Globals.MENU);
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
        Object source = e.getSource();
        if (Globals.JB_LOG_OUT.equals(source)) {
            ui.setLogOutButtonForeground(Color.black);
        } else if (Globals.JB_DELETE.equals(source)) {
            ui.setDeleteAccountButtonForeground(Color.black);
        }
    }

    /**
     * MouseListener method to process when the cursor is no longer selecting one of the buttons
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();
        if (Globals.JB_LOG_OUT.equals(source)) {
            ui.setLogOutButtonForeground(Color.white);
        } else if (Globals.JB_DELETE.equals(source)) {
            ui.setDeleteAccountButtonForeground(Color.white);
        }
    }
}

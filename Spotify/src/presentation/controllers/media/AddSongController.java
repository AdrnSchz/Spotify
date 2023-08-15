package presentation.controllers.media;

import business.SongManager;
import business.UserManager;
import business.entities.Song;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.media.AddSongUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Controller class for the AddSongUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class AddSongController extends MouseAdapter implements ActionListener {
    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;
    /**
     * SongManager instance to store the added song.
     */
    private final SongManager songManager;
    /**
     * UserManager instance to retrieve the current user.
     */
    private final UserManager userManager;
    /**
     * AddSongUI instance to display the fields to add a song.
     */
    private final AddSongUI ui;

    /**
     * Constructor method for AddSongController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param songManager SongManager instance to store the added song.
     * @param userManager UserManager instance to retrieve the current user.
     */
    public AddSongController(FrameController frameController, SongManager songManager, UserManager userManager) {
        this.frameController = frameController;
        this.songManager = songManager;
        this.userManager = userManager;
        ui = new AddSongUI();
        frameController.addCard(ui, Globals.ADD_SONG);
        ui.setListeners(this);
        ui.setName(Globals.ADD_SONG);
    }

    /**
     * Method that checks if all the given fields were entered
     *
     * @return boolean indicating if none of the fields are empty
     */
    public boolean emptyFields(){
        ArrayList<String> fields = ui.getFields();
        for (String field : fields) {
            if (field.equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that clears the entered fields
     *
     */
    public void clearFields(){
        ui.clearFields();
    }

    /**
     * ActionListener method to process the different button clicks.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Globals.JB_ADD_SONG -> {
                try {
                    if (emptyFields()) {
                        frameController.showError("All fields are required");
                    }
                    else {
                        Song song = ui.getSong(userManager.getCurrentUser());
                        songManager.addSong(song, ui.getPath());
                        ui.clearFields();
                        frameController.showMessage("Song successfully created");
                        frameController.swapScreen(ui, Globals.MAIN_SCREEN);
                    }
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                    ui.clearPath();
                }
            }
            case Globals.JB_SELECT_FILE -> {
                ui.selectFile();
            }
            case Globals.JB_GENRE -> ui.setGenre(ui.getSelectedComboBox());
        }
    }

    /**
     * MouseListener method to process when the cursor is selecting one of the buttons
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        ui.setAddSongForeground(Color.black);
    }

    /**
     * MouseListener method to process when the cursor is no longer selecting one of the buttons
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        ui.setAddSongForeground(Color.white);
    }

}

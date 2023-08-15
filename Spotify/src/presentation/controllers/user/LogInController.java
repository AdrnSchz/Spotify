package presentation.controllers.user;

import business.UserManager;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.user.LogInUI;

import java.awt.*;
import java.awt.event.*;

/**
 * Controller class for the LogInUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class LogInController implements ActionListener, KeyListener, MouseListener {

    /**
     * LogInUI instance to display the log in screen.
     */
    private final LogInUI ui;

    /**
     * UserManager instance to retrieve the user information.
     */
    private final UserManager userManager;

    /**
     * FrameController instance to communicate with the main frame.
     */
    private final FrameController frameController;

    /**
     * Constructor method for LogInController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param userManager UserManager instance to retrieve the user information.
     */
    public LogInController(FrameController frameController, UserManager userManager) {
        this.frameController = frameController;
        this.ui = new LogInUI();
        this.userManager = userManager;
        frameController.addCard(ui, Globals.LOG_IN);
        ui.setListeners(this);
        ui.setName(Globals.LOG_IN);
    }

    /**
     * Method to check if the user credentials are correct.
     *
     * @return boolean representing if the user credentials are correct.
     * @throws BusinessException if there is any error retrieving the user information from the database.
     */
    public boolean checkLogIn() throws BusinessException {
        boolean isValid = userManager.checkLogin(ui.getUsername(), ui.getPassword());
        if(isValid) {
            userManager.setCurrentUser(ui.getUsername());
            ui.clearUsername();
        }
        ui.clearPassword();
        return isValid;
    }

    /**
     * ActionListener method to process the different button clicks.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case Globals.JB_LOG_IN -> {
                try {
                    if (checkLogIn()) {
                        frameController.clearPreviousScreens();
                        frameController.setPreviousScreen(ui);
                        frameController.togglePlayer();
                        frameController.swapScreen(ui, Globals.MAIN_SCREEN);
                    } else {
                        frameController.showError("The entered credentials are not correct");
                    }
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            }
            case Globals.JB_TO_SIGN_UP -> frameController.swapScreen(ui, Globals.SIGN_UP);
        }
    }

    /**
     * Method to clear the username and password fields.
     */
    public void clearFields(){
        ui.clearUsername();
        ui.clearPassword();
    }

    /**
     * KeyListener method to process the different key typed.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == ui.getPasswordField()) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                ui.pressLogIn();
            }
        }
    }

    /**
     * MouseListener method to process the mouseEntered interactions.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == ui.getLogInButton()) {
            ui.getLogInButton().setForeground(Color.black);
        }
        else if (e.getSource() == ui.getMessage()) {
            ui.getMessage().setForeground(Color.white);
        }
    }

    /**
     * MouseListener method to process the mouseExited interactions.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == ui.getLogInButton()) {
            ui.getLogInButton().setForeground(Color.white);
        }
        else if (e.getSource() == ui.getMessage()) {
            ui.getMessage().setForeground(Globals.greenSpotify);
        }
    }

    /**
     * MouseListener method to process the different clicks in the screen.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == ui.getMessage()) {
            actionPerformed(new ActionEvent(ui.getMessage(), 0, Globals.JB_TO_SIGN_UP));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
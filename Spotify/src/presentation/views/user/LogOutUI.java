package presentation.views.user;

import presentation.Globals;
import presentation.views.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * LogOutUI class that represents the settings view.
 *
 * @author Group 6
 * @version 1.0
 */
public class LogOutUI extends Screen {
    /**
     * Fonts representing the title format
     */
    private static final Font title_font = new Font("Calibri", Font.BOLD, 30);
    /**
     * JButtons representing the different option buttons.
     */
    private JButton logOutButton, deleteAccountButton;

    /**
     * Constructor method of LogOutUI.
     */
    public LogOutUI() {
        configLayout();
    }

    /**
     * Method that registers a listener to the buttons.
     * @param actionListener listener to be registered.
     */
    public void setListeners(ActionListener actionListener) {
        logOutButton.setActionCommand(Globals.JB_LOG_OUT);
        deleteAccountButton.setActionCommand(Globals.JB_DELETE_ACCOUNT);

        logOutButton.addActionListener(actionListener);
        deleteAccountButton.addActionListener(actionListener);
        logOutButton.addMouseListener((MouseListener) actionListener);
        deleteAccountButton.addMouseListener((MouseListener) actionListener);
    }

    /**
     * Method that configures the general layout of the view.
     */
    private void configLayout() {
        //Create grid
        this.setLayout(new GridLayout(1, 3));
        this.setBackground(Color.black);
        this.add(Box.createRigidArea(new Dimension(10, 0)));

        //Create central panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.setBackground(Color.black);

        //Settings Title
        JLabel settingsTitle = new JLabel("SETTINGS");
        settingsTitle.setFont(title_font);
        settingsTitle.setForeground(Globals.greenSpotify);
        settingsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(settingsTitle);

        centerPanel.add(Box.createRigidArea(new Dimension(10, 50)));

        //Log out button
        logOutButton = newJButton(Globals.greenSpotify, "LOG OUT");
        centerPanel.add(logOutButton);

        //Delete account button
        centerPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        deleteAccountButton = newJButton(Color.gray, "DELETE ACCOUNT");
        centerPanel.add(deleteAccountButton);

        this.add(centerPanel);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
    }

    /**
     * Method that configures and returns a JButton.
     *
     * @param background Color to set the background of the button to.
     * @param message String indicating the text to display on the button.
     * @return JButton configured.
     */
    private JButton newJButton(Color background, String message){
        JButton jButton = new JButton(message);
        jButton.setBackground(background);
        jButton.setForeground(Color.white);
        jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setOpaque(true);
        jButton.setMinimumSize(Globals.fieldDimension);
        jButton.setMaximumSize(Globals.fieldDimension);
        return jButton;
    }

    /**
     * Method that sets the color of the text of the logOut JButton.
     *
     * @param color Color to set the text of the button to.
     */
    public void setLogOutButtonForeground(Color color){
        logOutButton.setForeground(color);
    }

    /**
     * Method that sets the color of the text of the deleteAccount JButton.
     *
     * @param color Color to set the text of the button to.
     */
    public void setDeleteAccountButtonForeground(Color color){
        deleteAccountButton.setForeground(color);
    }

    @Override
    public void clearFields() {}

    /**
     * Method that retrieves the name of the component
     *
     * @return String indicating the name of the component
     */
    @Override
    public String getTag() {
        return getName();
    }
}

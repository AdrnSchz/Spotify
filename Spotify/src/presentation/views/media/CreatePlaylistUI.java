package presentation.views.media;

import presentation.Globals;
import presentation.views.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * CreatePlaylistUI class that represents the view to create a playlist.
 *
 * @author Group 6
 * @version 1.0
 */
public class CreatePlaylistUI extends Screen {
    /**
     * JButton representing the button to create the playlists.
     */
    private static JButton createPlaylist;
    /**
     * Dimensions representing the different rigid boxes used to compensate the view.
     */
    private final Dimension bigSpace = new Dimension(10, 10);
    private final Dimension smallSpace = new Dimension(10, 5);
    /**
     * JTextField representing the fields to be completed.
     */
    private JTextField name, description;
    /**
     * JPanel representing the center panel of the view.
     */
    private JPanel centerPanel;

    /**
     * Constructor method of CreatePlaylistUI.
     */
    public CreatePlaylistUI(){
        configLayout();
    }

    /**
     * Method that registers a listener to the buttons.
     * @param actionListener listener to be registered.
     */
    public void setListeners(ActionListener actionListener) {
        createPlaylist.setActionCommand(Globals.JB_CREATE_PLAYLIST);
        createPlaylist.addActionListener(actionListener);
        createPlaylist.addMouseListener((MouseListener) actionListener);
    }

    /**
     * Method that configures and returns a JTextField.
     *
     * @return JTextField with the desired configuration.
     */
    private JTextField configJTextField(){
        JTextField jTextField = new JTextField();
        jTextField.setMaximumSize(Globals.fieldDimension);
        jTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        return jTextField;
    }

    /**
     * Method that configures and adds a JLabel to the centerPanel.
     *
     * @param text String to be displayed on the JLabel.
     * @param font Font to use for the text.
     * @param foreground Color to set the text to.
     * @param dimension Dimension to add after the JLabel
     */
    private void configJLabel(String text, Font font, Color foreground, Dimension dimension){
        JLabel jlabel = new JLabel(text);
        jlabel.setFont(font);
        jlabel.setForeground(foreground);
        jlabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(jlabel);
        centerPanel.add(Box.createRigidArea(dimension));
    }

    /**
     * Method that configures the general layout of the view.
     */
    private void configLayout() {
        this.setLayout(new GridLayout(1, 3));
        this.setBackground(Color.black);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.setBackground(Color.black);

        //Left border
        this.add(Box.createRigidArea(new Dimension(10, 0)));

        //Center
        configJLabel("CREATE A PLAYLIST", new Font("Calibri", Font.BOLD, 30),
                Globals.greenSpotify, smallSpace);
        configJLabel("Feeling inspired today?", new Font("Calibri", Font.ITALIC, 20),
                Globals.greenSpotify, bigSpace);
        configJLabel("Name", new Font("Calibri", Font.BOLD, 15), Color.white, smallSpace);

        name = configJTextField();
        centerPanel.add(name);
        centerPanel.add(Box.createRigidArea(bigSpace));

        configJLabel("Description", new Font("Calibri", Font.BOLD, 15), Color.white, smallSpace);

        description = configJTextField();
        centerPanel.add(description);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 20)));

        createPlaylist = new JButton("CREATE PLAYLIST");
        createPlaylist.setMaximumSize(Globals.fieldDimension);
        createPlaylist.setBackground(Globals.greenSpotify);
        createPlaylist.setForeground(Color.white);
        createPlaylist.setAlignmentX(Component.LEFT_ALIGNMENT);
        createPlaylist.setBorderPainted(false);
        createPlaylist.setFocusPainted(false);
        createPlaylist.setOpaque(true);

        centerPanel.add(createPlaylist);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        this.add(centerPanel);

        //Right border
        this.add(Box.createRigidArea(new Dimension(1, 0)));
    }

    /**
     * Method that sets the color of the text of the createPlaylists JButton.
     *
     * @param color Color to set the text of the button to.
     */
    public void setCreatePlaylistForeground(Color color){
        createPlaylist.setForeground(color);
    }

    /**
     * Method that returns the name entered by the user.
     *
     * @return String indicating the added name.
     */
    public String getNameField(){
        return name.getText();
    }

    /**
     * Method that returns the description entered by the user.
     *
     * @return String indicating the added description.
     */
    public String getDescription(){
        return description.getText();
    }

    /**
     * Method that clears the fields of the view.
     *
     */
    @Override
    public void clearFields() {
        name.setText("");
        description.setText("");
    }

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
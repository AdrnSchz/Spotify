package presentation.views.home;

import presentation.Globals;
import presentation.views.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * MainScreenUI class that represents the main screen view.
 *
 * @author Group 6
 * @version 1.0
 */
public class MainScreenUI extends Screen {
    /**
     * Fonts representing the different titles
     */
    private static final Font text_font = new Font("Calibri", Font.ITALIC, 30);
    private static final Font jb_font = new Font("Calibri", Font.BOLD, 20);
    private static final Font title_font = new Font("Calibri", Font.BOLD, 40);
    /**
     * Dimensions representing the different rigid boxes used to compensate the view.
     */
    private static final Dimension buttons_dimension = new Dimension(150,150);
    private static final Dimension separation = new Dimension(20, 250);
    private static final Dimension sides = new Dimension(250, 0);
    /**
     * JButtons representing the different menus.
     */
    private JButton addSongButton, createPlaylistButton, availableSongsButton, availablePlaylistsButton, statsButton;
    /**
     * JPanel representing the center panel of the view.
     */
    private JPanel centerPanel;


    /**
     * Constructor method of MainScreenUI.
     */
    public MainScreenUI() {
        configLayout();
    }

    /**
     * Method that registers a listener to the buttons.
     * @param listener listener to be registered.
     */
    public void setListeners(ActionListener listener) {
        addSongButton.setActionCommand(Globals.JB_TO_ADD_SONG);
        createPlaylistButton.setActionCommand(Globals.JB_TO_CREATE_PLAYLIST);
        availableSongsButton.setActionCommand(Globals.JB_TO_SONGS);
        availablePlaylistsButton.setActionCommand(Globals.JB_TO_PLAYLISTS);
        statsButton.setActionCommand(Globals.JB_TO_STATS);

        addSongButton.addActionListener(listener);
        createPlaylistButton.addActionListener(listener);
        availableSongsButton.addActionListener(listener);
        availablePlaylistsButton.addActionListener(listener);
        statsButton.addActionListener(listener);

        addSongButton.addMouseListener((MouseListener) listener);
        createPlaylistButton.addMouseListener((MouseListener) listener);
        availableSongsButton.addMouseListener((MouseListener) listener);
        availablePlaylistsButton.addMouseListener((MouseListener) listener);
        statsButton.addMouseListener((MouseListener) listener);
    }

    /**
     * Method that configures the general layout of the view.
     */
    private void configLayout() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);

        configCenter();
        this.add(Box.createRigidArea(sides), BorderLayout.EAST);
        this.add(Box.createRigidArea(sides), BorderLayout.WEST);
    }

    /**
     * Method that configures the center layout of the view.
     */
    private void configCenter() {
        JPanel centralLayout = new JPanel();
        centralLayout.setLayout(new BorderLayout());
        centralLayout.setBackground(Color.black);

        configCenterNorth(centralLayout);
        configCenterCenter(centralLayout);

        this.add(centralLayout, BorderLayout.CENTER);
    }

    /**
     * Method that configures the north region of the center panel of the view.
     *
     * @param centralLayout JPanel representing the center panel of the view.
     */
    private void configCenterNorth(JPanel centralLayout){
        JPanel topCenterPanel = new JPanel();
        topCenterPanel.setLayout(new GridLayout(3, 1));
        topCenterPanel.setBackground(Color.black);

        topCenterPanel.add(Box.createRigidArea(new Dimension(1000, 10)));

        JLabel welcomeTitle = new JLabel("Welcome!");
        welcomeTitle.setFont(title_font);
        welcomeTitle.setForeground(Globals.greenSpotify);
        topCenterPanel.add(welcomeTitle);

        JLabel options_message = new JLabel("What would you like to do?");
        options_message.setFont(text_font);
        options_message.setForeground(Globals.greenSpotify);
        topCenterPanel.add(options_message);
        centralLayout.add(topCenterPanel, BorderLayout.NORTH);
    }

    /**
     * Method that configures the center region of the center panel of the view.
     *
     * @param centralLayout JPanel representing the center panel of the view.
     */
    private void configCenterCenter(JPanel centralLayout){
        centerPanel = new JPanel(new FlowLayout());
        centerPanel.setBackground(Color.black);

        addSongButton = configButton("<html><center>ADD<br>SONG<center></html>");
        centerPanel.add(Box.createRigidArea(separation));
        createPlaylistButton = configButton("<html><center>CREATE<br>PLAYLIST<center></html>");
        centerPanel.add(Box.createRigidArea(separation));
        availableSongsButton = configButton("<html><center>AVAILABLE<br>SONGS<center></html>");
        centerPanel.add(Box.createRigidArea(separation));
        availablePlaylistsButton = configButton("<html><center>AVAILABLE<br>PLAYLISTS<center></html>");
        centerPanel.add(Box.createRigidArea(separation));
        statsButton = configButton("STATS");

        centralLayout.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Method that configures and returns a JButton.
     *
     * @param text String to be displayed on the button.
     * @return JButton configured.
     */
    private JButton configButton(String text){
        JButton jButton = new JButton(text);
        jButton.setPreferredSize(buttons_dimension);
        jButton.setBackground(Color.gray);
        jButton.setForeground(Color.white);
        jButton.setFont(jb_font);
        jButton.setOpaque(true);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        centerPanel.add(jButton);
        return jButton;
    }

    /**
     * Method that sets the colors of a JButton.
     *
     * @param button JButton to be changed.
     * @param background Color of the background to be set.
     * @param foreground Color of the text to be set.
     */
    public void setButton(JButton button, Color foreground, Color background){
        button.setForeground(foreground);
        button.setBackground(background);
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

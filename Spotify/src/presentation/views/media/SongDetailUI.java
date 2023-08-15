package presentation.views.media;

import presentation.Globals;
import presentation.views.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Class representing the SongDetailUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class SongDetailUI extends Screen {

    /**
     * Different JBtton instances to interact with the user.
     */
    private JButton playButton, toPlaylistButton, deleteButton, confirmButton;
    /**
     * TextArea instances to display the lyrics to the user.
     */
    private JTextArea lyricsArea;
    /**
     * JScrollPane instance to scroll through the lyrics.
     */
    private JScrollPane scrollPane;
    /**
     * JScrollBar instance to manage the scroll bar.
     */
    private JScrollBar vertical;
    /**
     * ImageIcon instances to display the play/pause icons.
     */
    private ImageIcon playIcon, pauseIcon;
    /**
     * JLabel instances to display the song information.
     */
    private JLabel songName, albumName, durationName, ownerName;
    /**
     * ButtonGroup instance to manage the radio buttons.
     */
    private ButtonGroup group;
    /**
     * JDialog instance to display the pop up.
     */
    private JDialog dialog;

    /**
     * Constructor method for SongDetailUI.
     */
    public SongDetailUI() {
        configLayout();
    }

    /**
     * Method that configures the layout of the view.
     */
    private void configLayout() {
        configCenter();
    }

    /**
     * Method that updates the song information with new song information.
     *
     * @param title String representing the title of the song.
     * @param artist String representing the artist of the song.
     * @param album String representing the album of the song.
     * @param genre String representing the genre of the song.
     * @param duration String representing the duration of the song.
     * @param owner String representing the owner of the song.
     * @param lyrics String representing the lyrics of the song.
     */
    public void setSongInfo(String title, String artist, String album, String genre, String duration, String owner,
                            String lyrics) {

        songName.setText(title + " - " + artist);
        albumName.setText(album + " · " + genre);
        durationName.setText(duration);
        ownerName.setText("Added by: " + owner);
        lyricsArea.setText(lyrics);
        scrollPane.getViewport().setViewPosition( new Point(0, 0) );
        vertical.setValue( vertical.getMinimum());
    }

    /**
     * Getter method that returns the button group.
     *
     * @return ButtonGroup instance representing the button group.
     */
    public ButtonGroup getGroup() {
        return group;
    }

    /**
     * Getter method that returns the pop up.
     *
     * @return JDialog instance representing the pop up.
     */
    public JDialog getDialog() {
        return dialog;
    }

    /**
     * Setter method that sets the play icon into the play/pause button.
     */
    public void setPlayButton() {
        if (playIcon != null) playButton.setIcon(playIcon);
    }

    /**
     * Setter method that sets the pause icon into the play/pause button.
     */
    public void setPauseButton() {
        if (pauseIcon != null) playButton.setIcon(pauseIcon);
    }

    /**
     * Method that displays the delete button.
     */
    public void showDeleteButton() {deleteButton.setVisible(true);}

    /**
     * Method that hides the delete button.
     */
    public void hideDeleteButton() {deleteButton.setVisible(false);}

    /**
     * Setter method that sets the listeners.
     *
     * @param actionLis ActionListener instance representing the listeners.
     */
    public void setListeners(ActionListener actionLis) {
        deleteButton.setActionCommand(Globals.JB_DELETE);
        toPlaylistButton.setActionCommand(Globals.JB_ADD_TO_PLAYLIST);
        playButton.setActionCommand(Globals.JB_PLAY_PAUSE);

        playButton.addActionListener(actionLis);
        toPlaylistButton.addActionListener(actionLis);
        deleteButton.addActionListener(actionLis);
    }

    /**
     * Setter method that sets the pop up listeners.
     * @param list ActionListener instance representing the listeners.
     * @param ids int[] representing the ids of the playlist each button will represent.
     */
    public void setPopListeners(ActionListener list, int[] ids) {
        confirmButton.setActionCommand(Globals.JB_CONFIRM_PLAYLIST);
        int i = 0;

        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.addActionListener(list);
            button.setActionCommand(ids[i] + "");
            i++;
        }
        confirmButton.addActionListener(list);
    }

    /**
     * Method that configures the center of the view.
     */
    private void configCenter() {
        this.setLayout(new GridLayout(1,2));
        this.setBackground(Color.black);

        //config left grid
        configLeft();
        //config right grid
        configRight();
    }

    /**
    * Method that configures the left grid of the view.
    */
    private void configLeft() {
        JPanel leftGrid = new JPanel(new GridBagLayout());
        JPanel infoPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setBackground(Color.black);
        leftGrid.setBackground(Color.black);
        leftGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftGrid.setAlignmentY(Component.TOP_ALIGNMENT);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        leftGrid.add(Box.createRigidArea(new Dimension(100, 50)), gbc);

        durationName = new JLabel("Duration");
        durationName.setFont(new Font("Calibri", Font.BOLD, 25));
        durationName.setForeground(Color.gray);
        durationName.setAlignmentX(Component.LEFT_ALIGNMENT);
        durationName.setAlignmentY(Component.TOP_ALIGNMENT);
        infoPanel.add(durationName);

        songName = new JLabel("Song - Artist");
        songName.setFont(new Font("Calibri", Font.BOLD, 30));
        songName.setForeground(Color.white);
        songName.setAlignmentX(Component.LEFT_ALIGNMENT);
        songName.setAlignmentY(Component.TOP_ALIGNMENT);
        infoPanel.add(songName);

        albumName = new JLabel("Album · Genre");
        albumName.setFont(new Font("Calibri", Font.PLAIN, 25));
        albumName.setForeground(Color.white);
        albumName.setAlignmentX(Component.LEFT_ALIGNMENT);
        albumName.setAlignmentY(Component.TOP_ALIGNMENT);
        infoPanel.add(albumName);

        ownerName = new JLabel("Added by: owner");
        ownerName.setFont(new Font("Calibri", Font.PLAIN, 20));
        ownerName.setForeground(Color.gray);
        ownerName.setAlignmentX(Component.LEFT_ALIGNMENT);
        ownerName.setAlignmentY(Component.TOP_ALIGNMENT);
        infoPanel.add(ownerName);
        gbc.gridx = 1;
        leftGrid.add(infoPanel, gbc);

        gbc.gridy = 1;
        configButtons(leftGrid, gbc);

        this.add(leftGrid);
    }

    /**
    * Method that configures the right grid of the view.
    */
    private void configRight() {
        JPanel rightGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        rightGrid.setBackground(Color.black);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        lyricsArea = new JTextArea("Sorry. There are no lyrics available for this song.");
        lyricsArea.setFont(new Font("Calibri", Font.BOLD, 20));
        lyricsArea.setEditable(false);
        lyricsArea.setBackground(Color.LIGHT_GRAY);
        lyricsArea.setSelectedTextColor(Globals.greenSpotify);
        lyricsArea.setLineWrap(true);
        lyricsArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(lyricsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMinimumSize(new Dimension(200, 300));
        vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue( vertical.getMinimum());
        rightGrid.add(scrollPane, gbc);

        gbc.gridx = 1;
        rightGrid.add(Box.createRigidArea(new Dimension(100, 50)), gbc);

        this.add(rightGrid);
    }

    /**
     * Method that configures the popUp panel.
     * @param playlistName String[] representing the names of the playlists.
     * @param frame JFrame instance representing the frame of the view.
     */
    public void configPopup(String[] playlistName, JFrame frame) {
        dialog = new JDialog(frame, "Add to playlist");
        dialog.setSize((int)(frame.getWidth() * 0.2), (int)(frame.getHeight() * 0.2));
        dialog.setBackground(Color.WHITE);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("SELECT A PLAYLIST");
        label.setFont(new Font("Calibri", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel listPanel = new JPanel(new GridLayout(0,1));
        listPanel.setBackground(Color.WHITE);
        group = new ButtonGroup();
        for (int i = 0; i < playlistName.length; i++) {
            JRadioButton radio = new JRadioButton(playlistName[i]);
            radio.setAlignmentX(Component.CENTER_ALIGNMENT);
            group.add(radio);
            listPanel.add(radio);
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        panel.add(scroll);
        panel.add(Box.createRigidArea(new Dimension(0, 7)));

        confirmButton = new JButton("CONFIRM");
        confirmButton.setBackground(Globals.greenSpotify);
        confirmButton.setForeground(Color.white);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setBorderPainted(false);
        confirmButton.setFocusPainted(false);
        confirmButton.setOpaque(true);
        panel.add(confirmButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    /**
     * Method that sets the popUp panel.
     * @param playlistName String[] representing the names of the playlists.
     * @param frame JFrame instance representing the frame of the view.
     */
    public void setPopup(String[] playlistName, JFrame frame) {
        configPopup(playlistName, frame);
    }

    /**
     * Method that configures the buttons of the view.
     * @param grid JPanel instance representing the grid panel.
     * @param gbc GridBagConstraints instance representing the constraints of the grid.
     */
    private void configButtons(JPanel grid, GridBagConstraints gbc) {
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        botPanel.setBackground(Color.black);

        Image play, add, pause, bin;
        try {
            play = ImageIO.read(new File("files/images/play_button.png"));
            pause = ImageIO.read(new File("files/images/pause_button.png"));
            add = ImageIO.read(new File("files/images/add_playlist.png"));
            bin = ImageIO.read(new File("files/images/bin_button.png"));

            playIcon = new ImageIcon(play);
            pauseIcon = new ImageIcon(pause);
            playButton = new JButton();
            playButton.setIcon(playIcon);
            playButton.setBorder(BorderFactory.createEmptyBorder());
            playButton.setContentAreaFilled(false);
            playButton.setBorderPainted(false);
            playButton.setFocusPainted(false);
            playButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            botPanel.add(playButton);

            ImageIcon addIcon = new ImageIcon(add);
            toPlaylistButton = new JButton(addIcon);
            toPlaylistButton.setBorder(BorderFactory.createEmptyBorder());
            toPlaylistButton.setContentAreaFilled(false);
            toPlaylistButton.setBorderPainted(false);
            toPlaylistButton.setFocusPainted(false);
            toPlaylistButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            botPanel.add(toPlaylistButton);

            ImageIcon binIcon = new ImageIcon(bin);
            deleteButton = new JButton(binIcon);
            deleteButton.setBorder(BorderFactory.createEmptyBorder());
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            botPanel.add(deleteButton);
        } catch (IOException e) {
            playButton = new JButton("PLAY");
            botPanel.add(playButton);

            toPlaylistButton = new JButton("ADD TO PLAYLIST");
            botPanel.add(toPlaylistButton);

            deleteButton = new JButton("DELETE");
            botPanel.add(deleteButton);

            playIcon = null;
            pauseIcon = null;
        }

        grid.add(botPanel, gbc);
    }

    @Override
    public void clearFields() {
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

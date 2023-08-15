package presentation.views.media.player;

import presentation.Globals;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

/**
 * PlayerUI class that represents the player view.
 *
 * @author Group 6
 * @version 1.0
 */
public class PlayerUI extends JPanel {

    /**
     * JLabels representing the song title, artist, elapsed time and song length.
     */
    private JLabel songTitle;
    private JLabel songArtist;
    private JLabel songElapsed;
    private JLabel songLength;
    /**
     * JButtons representing the song loop, playlist loop, previous, play, next and stop buttons.
     */
    private JButton songLoopButton;
    private JButton playlistLoopButton;
    private JButton previousButton;
    private JButton playButton;
    private JButton nextButton;
    private JButton stopButton;
    /**
     * JSlider representing the song progress bar.
     */
    private JSlider slider;
    /**
     * JLabels representing the song and playlist repeat labels.
     */
    private JLabel songRepeatLabel;
    private JLabel playlistRepeatLabel;

    /**
     * Constructor method of PlayerUI.
     */
    public PlayerUI() {
        setVisible(false);
        configLayout();
        configLeft();
        configCenter();
        configRight();
        removeSong();
    }

    /**
     * Method that configures the layout of the view.
     */
    private void configLayout() {
        setLayout(new BorderLayout());
        setBackground(Globals.greyPlayer);
    }

    /**
     * Method that configures the left side of the view.
     */
    private void configLeft() {
        JPanel songInfo = new JPanel();
        songInfo.setBackground(Globals.greyPlayer);
        songInfo.setLayout(new BoxLayout(songInfo, BoxLayout.Y_AXIS));
        songInfo.setPreferredSize(new Dimension(240, 40));

        songTitle = new JLabel();
        songArtist = new JLabel();
        songTitle.setForeground(Color.WHITE);
        songTitle.setFont(new Font("Calibri", Font.PLAIN, 18));
        songArtist.setForeground(Color.WHITE);
        songTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        songArtist.setAlignmentX(Component.CENTER_ALIGNMENT);

        songInfo.add(Box.createRigidArea(new Dimension(80, 40)));
        songInfo.add(songTitle);
        songInfo.add(songArtist);

        add(songInfo, BorderLayout.WEST);
    }

    /**
     * Method that configures the center of the view.
     */
    private void configCenter() {
        JPanel center = new JPanel();
        center.setBackground(Globals.greyPlayer);
        center.setLayout(new BorderLayout());
        addSongRepeatButton(center);
        addPlayer(center);
        addPlaylistRepeatButton(center);
        add(center, BorderLayout.CENTER);
    }

    /**
     * Method that configures the right side of the view.
     */
    private void configRight() {
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(Globals.greyPlayer);
        east.setPreferredSize(new Dimension(240, 40));

        stopButton = newJButton("files/images/stop_icon.png");
        east.add(Box.createRigidArea(new Dimension(80, 25)));
        east.add(stopButton);
        add(east, BorderLayout.EAST);
    }

    /**
     * Method that adds the song repeat button to the view.
     *
     * @param center JPanel representing the center of the view.
     */
    private void addSongRepeatButton(JPanel center) {
        JPanel songRepeat = new JPanel();
        songRepeat.setBackground(Globals.greyPlayer);
        songRepeat.setLayout(new BoxLayout(songRepeat, BoxLayout.Y_AXIS));

        songLoopButton = newJButton("files/images/repeat_icon.png");
        songRepeatLabel = new JLabel("<html><div style='text-align: center;'>Song<br>Repeat</div></html>");
        songRepeatLabel.setForeground(Color.WHITE);

        songRepeatLabel.add(Box.createVerticalGlue());
        songRepeat.add(Box.createRigidArea(new Dimension(10, 20)));
        songRepeat.add(songLoopButton);
        songRepeat.add(songRepeatLabel);
        songRepeatLabel.add(Box.createVerticalGlue());

        center.add(songRepeat, BorderLayout.WEST);
    }

    /**
     * Method that adds the playlist repeat button to the view.
     *
     * @param center JPanel representing the center of the view.
     */
    private void addPlaylistRepeatButton(JPanel center) {
        JPanel playlistRepeat = new JPanel();
        playlistRepeat.setBackground(Globals.greyPlayer);
        playlistRepeat.setLayout(new BoxLayout(playlistRepeat, BoxLayout.Y_AXIS));

        playlistRepeatLabel = new JLabel("<html><div style='text-align: center;'>Playlist<br>Repeat</div></html>");
        playlistRepeatLabel.setForeground(Color.WHITE);

        playlistLoopButton = newJButton("files/images/repeat_icon.png");

        playlistRepeat.add(Box.createVerticalGlue());
        playlistRepeat.add(Box.createRigidArea(new Dimension(10, 20)));
        playlistRepeat.add(playlistLoopButton);
        playlistRepeat.add(playlistRepeatLabel);
        playlistRepeat.add(Box.createVerticalGlue());

        center.add(playlistRepeat, BorderLayout.EAST);
    }

    /**
     * Method that adds the player information to the view.
     *
     * @param center JPanel representing the center of the view.
     */
    private void addPlayer(JPanel center) {
        JPanel player = new JPanel();
        player.setBackground(Globals.greyPlayer);
        player.setLayout(new BoxLayout(player, BoxLayout.Y_AXIS));

        JPanel playerButtons = new JPanel();
        playerButtons.setBackground(Globals.greyPlayer);
        playerButtons.setLayout(new BoxLayout(playerButtons, BoxLayout.X_AXIS));

        previousButton = newJButton("files/images/previous_icon.png");
        playButton = newJButton("files/images/play_icon.png");
        nextButton = newJButton("files/images/next_icon.png");

        playerButtons.add(previousButton);
        playerButtons.add(playButton);
        playerButtons.add(nextButton);

        JPanel playerSlider = new JPanel();
        playerSlider.setBackground(Globals.greyPlayer);
        playerSlider.setLayout(new BoxLayout(playerSlider, BoxLayout.X_AXIS));

        songElapsed = new JLabel();
        songElapsed.setForeground(Color.WHITE);

        songLength = new JLabel();
        songLength.setForeground(Color.WHITE);

        slider = new JSlider();
        slider.setBackground(Globals.greyPlayer);

        playerSlider.add(Box.createRigidArea(new Dimension(30, 1)));
        playerSlider.add(songElapsed);
        playerSlider.add(slider);
        playerSlider.add(songLength);
        playerSlider.add(Box.createRigidArea(new Dimension(30, 1)));

        player.add(Box.createRigidArea(new Dimension(1, 5)));
        player.add(playerButtons);
        player.add(playerSlider);
        player.add(Box.createRigidArea(new Dimension(1, 10)));
        center.add(player, BorderLayout.CENTER);
    }

    /**
     * Method that sets the current playing song information and enables the player buttons.
     *
     * @param title title of the song.
     * @param artist artist of the song.
     * @param durationString duration of the song.
     */
    public void setSong(String title, String artist, String durationString) {
        songTitle.setText(title);
        songArtist.setText(artist);
        songElapsed.setText("00:00");
        songLength.setText(durationString);

        if (songRepeatLabel.getForeground() == Color.LIGHT_GRAY) {
            songRepeatLabel.setForeground(Color.WHITE);
        }
        if (playlistRepeatLabel.getForeground() == Color.LIGHT_GRAY) {
            playlistRepeatLabel.setForeground(Color.WHITE);
        }

        songLoopButton.setEnabled(true);
        playlistLoopButton.setEnabled(true);
        previousButton.setEnabled(true);
        playButton.setEnabled(true);
        nextButton.setEnabled(true);
        stopButton.setEnabled(true);

        play();
    }

    /**
     * Method that removes the current playing song information and disables the player buttons.
     */
    public void removeSong() {
        songTitle.setText("--");
        songArtist.setText("--");
        songElapsed.setText("00:00");
        songLength.setText("00:00");

        slider.setValue(0);
        slider.setEnabled(false);

        pause();
        resetSongLoop();
        resetPlaylistLoop();
        songRepeatLabel.setForeground(Color.LIGHT_GRAY);
        playlistRepeatLabel.setForeground(Color.LIGHT_GRAY);
        songLoopButton.setEnabled(false);
        playlistLoopButton.setEnabled(false);
        previousButton.setEnabled(false);
        playButton.setEnabled(false);
        nextButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    /**
     * Method that disables the playlist loop button.
     */
    public void disablePlaylistLoop() {
        playlistLoopButton.setEnabled(false);
        playlistRepeatLabel.setForeground(Color.LIGHT_GRAY);
    }

    /**
     * Method that toggles the song loop button.
     */
    public void toggleLoopSong() {

        if (songRepeatLabel.getForeground() == Color.white) {
            songLoopButton.setIcon(new ImageIcon("files/images/repeat_icon_green.png"));
            songRepeatLabel.setForeground(Globals.greenSpotify);
        }
        else {
            resetSongLoop();
        }
    }

    /**
     * Method that toggles the playlist loop button.
     */
    public void toggleLoopPlaylist() {

        if (playlistRepeatLabel.getForeground() == Color.white) {
            playlistLoopButton.setIcon(new ImageIcon("files/images/repeat_icon_green.png"));
            playlistRepeatLabel.setForeground(Globals.greenSpotify);
        }
        else {
            resetPlaylistLoop();
        }
    }

    /**
     * Method that sets the play button icon.
     */
    public void play(){
        playButton.setIcon(new ImageIcon("files/images/pause_icon.png"));
    }

    /**
     * Method that sets the pause button icon.
     */
    public void pause(){
        playButton.setIcon(new ImageIcon("files/images/play_icon.png"));
    }

    /**
     * Method that resets the song loop button.
     */
    public void resetSongLoop() {
        songLoopButton.setIcon(new ImageIcon("files/images/repeat_icon.png"));
        songRepeatLabel.setForeground(Color.white);
    }

    /**
     * Method that resets the playlist loop button.
     */
    public void resetPlaylistLoop() {
        playlistLoopButton.setIcon(new ImageIcon("files/images/repeat_icon.png"));
        playlistRepeatLabel.setForeground(Color.white);
    }

    /**
     * Method that sets the time elapsed label.
     *
     * @param timeString time elapsed.
     */
    public void setTimeElapsed(String timeString) {
        songElapsed.setText(timeString);
    }

    /**
     * Method that resets the slider.
     *
     * @param duration duration of the song.
     */
    public void resetSlider(int duration) {
        slider.setValue(0);
        slider.setMaximum(duration);
        slider.setEnabled(true);
    }

    /**
     * Getter method that returns the slider value.
     *
     * @return slider value.
     */
    public int getSliderValue() {
        return slider.getValue();
    }

    /**
     * Setter method that sets the slider value.
     *
     * @param value slider value.
     */
    public void setSliderValue(int value) {
        slider.setValue(value);
    }

    /**
     * Getter method that returns the song repeat label.
     * @return song repeat label.
     */
    public JLabel getSongRepeatLabel(){
        return songRepeatLabel;
    }

    /**
     * Getter method that returns the playlist repeat label.
     *
     * @return playlist repeat label.
     */
    public JLabel getPlaylistRepeatLabel(){
        return playlistRepeatLabel;
    }

    /**
     * Getter method that returns the slider.
     *
     * @return slider.
     */
    public JSlider getSlider(){
        return slider;
    }

    /**
     * Method that returns whether the song repeat button is enabled.
     *
     * @return true if the song repeat button is enabled, false otherwise.
     */
    public boolean isSongRepeatEnabled() {
        return songLoopButton.isEnabled();
    }

    /**
     * Method that returns whether the playlist repeat button is enabled.
     *
     * @return true if the playlist repeat button is enabled, false otherwise.
     */
    public boolean isPlaylistRepeatEnabled() {
        return playlistLoopButton.isEnabled();
    }

    /**
     * Method that returns a JButton with icon.
     *
     * @param iconPath path of the icon.
     * @return JButton with icon.
     */
    private JButton newJButton(String iconPath){
        JButton jButton = new JButton();
        jButton.setBackground(Globals.greyPlayer);
        jButton.setIcon(new ImageIcon(iconPath));
        jButton.setBorderPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.setFocusPainted(false);
        setOpaque(true);

        return jButton;
    }

    /**
     * Method that registers a listener to the buttons.
     * @param listener listener to be registered.
     */
    public void registerListener(EventListener listener) {
        songLoopButton.setActionCommand(Globals.JB_SONG_LOOP);
        playlistLoopButton.setActionCommand(Globals.JB_PLAYLIST_LOOP);
        previousButton.setActionCommand(Globals.JB_PREVIOUS_SONG);
        playButton.setActionCommand(Globals.JB_PLAY_PAUSE);
        nextButton.setActionCommand(Globals.JB_NEXT_SONG);
        stopButton.setActionCommand(Globals.JB_STOP);

        songLoopButton.addActionListener((ActionListener) listener);
        playlistLoopButton.addActionListener((ActionListener) listener);
        previousButton.addActionListener((ActionListener) listener);
        playButton.addActionListener((ActionListener) listener);
        nextButton.addActionListener((ActionListener) listener);
        stopButton.addActionListener((ActionListener) listener);

        songRepeatLabel.addMouseListener((MouseListener) listener);
        playlistRepeatLabel.addMouseListener((MouseListener) listener);

        slider.addChangeListener((ChangeListener) listener);
        slider.addMouseListener((MouseListener) listener);
    }
}
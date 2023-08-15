package presentation.views.media;

import business.entities.Song;
import presentation.Globals;
import presentation.views.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * AddSongUI class that represents the view to add a song.
 *
 * @author Group 6
 * @version 1.0
 */
public class AddSongUI extends Screen {
    /**
     * JButtons representing the different options.
     */
    private static JButton addSong, selectFile;
    /**
     * Dimensions representing the different rigid boxes used to compensate the view.
     */
    private final Dimension bigSpace = new Dimension(10, 10);
    private final Dimension smallSpace = new Dimension(10, 5);
    /**
     * JTextFields representing the fields to be completed.
     */
    private JTextField title, album, artist;
    /**
     * String representing the chosen genre.
     */
    private String genre = "";
    /**
     * String representing the path of the song file to be added.
     */
    private String file = "";
    /**
     * JComboBox representing the different genres to be chosen.
     */
    private final JComboBox<String> genreComboBox = new JComboBox<>();
    /**
     * JPanel representing the center panel of the view.
     */
    private JPanel centerPanel;

    /**
     * Constructor method of AddSongUI.
     */
    public AddSongUI() {
        configLayout();
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
        JLabel createPlaylistTitle = new JLabel("ADD A SONG");
        createPlaylistTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        createPlaylistTitle.setForeground(Globals.greenSpotify);
        createPlaylistTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(createPlaylistTitle);

        centerPanel.add(Box.createRigidArea(smallSpace));

        JLabel inspiredTitle = new JLabel("Share your music with the world!");
        inspiredTitle.setFont(new Font("Calibri", Font.ITALIC, 20));
        inspiredTitle.setForeground(Globals.greenSpotify);
        inspiredTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(inspiredTitle);

        centerPanel.add(Box.createRigidArea(bigSpace));

        configJLabel("Title");
        title = configJTextField();

        configJLabel("Genre");
        genreComboBox.addItem("Please select");
        genreComboBox.addItem("Pop");
        genreComboBox.addItem("Rock");
        genreComboBox.addItem("Jazz");
        genreComboBox.addItem("Classic");
        genreComboBox.addItem("Metal");
        genreComboBox.addItem("EDM");
        genreComboBox.addItem("Soul");
        genreComboBox.addItem("Funk");
        genreComboBox.addItem("Disco");
        genreComboBox.setMaximumSize(Globals.fieldDimension);
        genreComboBox.setAlignmentX(LEFT_ALIGNMENT);
        centerPanel.add(genreComboBox);
        centerPanel.add(Box.createRigidArea(bigSpace));

        configJLabel("Album");
        album = configJTextField();

        configJLabel("Artist");
        artist = configJTextField();

        configJLabel("File");

        selectFile = configJButton("SELECT FILE", Color.gray);
        addSong = configJButton("ADD SONG", Globals.greenSpotify);
        this.add(centerPanel);

        //Right border
        this.add(Box.createRigidArea(new Dimension(1, 0)));
    }

    /**
     * Method that registers a listener to the buttons.
     * @param actionListener listener to be registered.
     */
    public void setListeners(ActionListener actionListener) {
        addSong.setActionCommand(Globals.JB_ADD_SONG);
        selectFile.setActionCommand(Globals.JB_SELECT_FILE);
        genreComboBox.setActionCommand(Globals.JB_GENRE);

        addSong.addActionListener(actionListener);
        addSong.addMouseListener((MouseListener) actionListener);
        selectFile.addActionListener(actionListener);
        genreComboBox.addActionListener(actionListener);
    }

    /**
     * Method that configures and adds a JLabel to the centerPanel.
     *
     * @param text String to be displayed on the JLabel.
     */
    private void configJLabel(String text){
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        jLabel.setForeground(Color.white);
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(jLabel);
        centerPanel.add(Box.createRigidArea(smallSpace));
    }

    /**
     * Method that configures and adds a JTextField to the centerPanel.
     *
     */
    private JTextField configJTextField(){
        JTextField jTextField = new JTextField();
        jTextField.setMaximumSize(Globals.fieldDimension);
        jTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        centerPanel.add(jTextField);
        centerPanel.add(Box.createRigidArea(bigSpace));
        return jTextField;
    }

    /**
     * Method that configures and returns a JButton.
     *
     * @param text String to be displayed on the button.
     * @param background Color to set the background of the button to.
     * @return JButton configured.
     */
    private JButton configJButton(String text, Color background){
        JButton jButton = new JButton(text);
        jButton.setMaximumSize(Globals.fieldDimension);
        jButton.setBackground(background);
        jButton.setForeground(Color.white);
        jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setOpaque(true);
        centerPanel.add(jButton);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        return jButton;
    }

    /**
     * Method that sets the color of the text of the addSong JButton.
     *
     * @param color Color to set the text of the button to.
     */
    public void setAddSongForeground(Color color){
        addSong.setForeground(color);
    }

    /**
     * Method that sets the path of the song to be added.
     *
     * @param path String indicating the path of the song file.
     */
    public void setFile(String path){
        file = path;
        selectFile.setText("FILE SELECTED");
        selectFile.setBackground(Globals.greenSpotify);
    }

    /**
     * Method that clears the path of the song to be added and resets the selectFile JButton.
     *
     */
    public void clearPath(){
        file = "";
        selectFile.setText("SELECT FILE");
        selectFile.setBackground(Color.gray);
    }

    /**
     * Method that retrieves the title entered by the user.
     *
     * @return String indicating the added title.
     */
    public String getTitle() {
        return title.getText();
    }

    /**
     * Method that retrieves the genre selected by the user.
     *
     * @return String indicating the chose genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Method that retrieves the album entered by the user.
     *
     * @return String indicating the album name.
     */
    public String getAlbum() {
        return album.getText();
    }

    /**
     * Method that retrieves the artist entered by the user.
     *
     * @return String indicating the album name.
     */
    public String getArtist() {
        return artist.getText();
    }

    /**
     * Method that retrieves the song file path selected by the user.
     *
     * @return String indicating the path of the file.
     */
    public String getPath() {
        return file;
    }

    /**
     * Method that lets the user choose a file using a JFileChooser.
     *
     */
    public void selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(centerPanel);

        if (result == JFileChooser.APPROVE_OPTION) {
            setFile(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Method that sets the genre chosen by the user.
     *
     * @param genre String indicating the selected genre.
     */
    public void setGenre(String genre){
        if(genre.equals("Please select")){
            this.genre = "";
        }
        else {
            this.genre = genre;
        }
    }

    /**
     * Method that returns the chosen genre by the user.
     *
     * @return String indicating the selected genre in the JComboBox.
     */
    public String getSelectedComboBox() {
        return (String) genreComboBox.getSelectedItem();
    }

    /**
     * Method that returns the content of the fields entered by the user.
     *
     * @return ArrayList of Strings indicating the fields entered by the user.
     */
    public ArrayList<String> getFields(){
        ArrayList<String> fields = new ArrayList<>();
        fields.add(getTitle());
        fields.add(getGenre());
        fields.add(getAlbum());
        fields.add(getArtist());
        fields.add(file);
        return fields;
    }

    /**
     * Method that returns a new Song created with the entered information.
     *
     * @return Song with the information entered by the user.
     */
    public Song getSong(String user) {
        return new Song(title.getText(), artist.getText(), album.getText(), genre, user);
    }

    /**
     * Method that clears the fields of the view.
     *
     */
    @Override
    public void clearFields() {
        title.setText("");
        genreComboBox.setSelectedIndex(0);
        album.setText("");
        artist.setText("");
        clearPath();
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

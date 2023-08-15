package presentation.views.media;

import presentation.Globals;
import presentation.views.Screen;
import presentation.views.components.JTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlaylistSongsUI extends Screen {

    private final JTableModel tableModel;
    private final JLabel playlistName;
    private final JLabel playlistDesc;
    private JButton playButton;
    private JButton pauseButton;
    private JButton binButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton reorderButton;
    private int playlistId;

    private int reordering;

    public PlaylistSongsUI(){
        reordering = 0;

        setLayout(new BorderLayout());
        setBackground(Color.black);

        playButton = loadButton("files/images/play_button_table.png", 15, 15);
        pauseButton = loadButton("files/images/pause_button_table.png", 15, 15);
        binButton = loadButton("files/images/bin_table.png", 15, 15);
        deleteButton = loadButton("files/images/bin_table.png", 30, 30);
        addButton = loadButton("files/images/add_song_table.png", 30, 30);

        reorderButton = new JButton("REORDER");
        reorderButton.setFont(new Font("SIGN UP", Font.BOLD, 15));
        reorderButton.setMaximumSize(new Dimension(100, 30));
        reorderButton.setMinimumSize(new Dimension(100, 30));
        reorderButton.setBorderPainted(false);
        reorderButton.setFocusPainted(false);
        reorderButton.setBackground(Globals.greenSpotify);
        reorderButton.setForeground(Color.black);
        reorderButton.setOpaque(true);

        JPanel topGrid = new JPanel(new GridLayout(2, 1));
        topGrid.setBackground(Color.black);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.BLACK);
        playlistName = new JLabel("AVAILABLE PLAYLISTS");
        playlistName.setFont(new Font("Calibri", Font.BOLD, 30));
        playlistName.setForeground(Globals.greenSpotify);
        titlePanel.add(playlistName);
        titlePanel.add(Box.createRigidArea(new Dimension(150, 1)));
        titlePanel.add(addButton);
        titlePanel.add(Box.createRigidArea(new Dimension(10, 1)));
        titlePanel.add(deleteButton);
        titlePanel.add(Box.createRigidArea(new Dimension(10, 1)));
        titlePanel.add(reorderButton);
        topGrid.add(titlePanel);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.PAGE_AXIS));
        descriptionPanel.setBackground(Color.black);
        descriptionPanel.add(Box.createRigidArea(new Dimension(1, 15)));

        playlistDesc = new JLabel("There is currently no description available for this playlist");
        playlistDesc.setFont(new Font("Calibri", Font.ITALIC, 20));
        playlistDesc.setForeground(Globals.greenSpotify);
        playlistDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionPanel.add(playlistDesc);
        descriptionPanel.add(Box.createRigidArea(new Dimension(1, 15)));

        topGrid.add(descriptionPanel);
        this.add(topGrid, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.setBackground(Color.black);

        tableModel = new JTableModel(new String[]{" ", "SONGS", "OWNER", " "});
        tablePanel.add(tableModel.getScrollPane(), BorderLayout.WEST);
        this.add(tablePanel, BorderLayout.CENTER);

    }

    private JButton loadButton(String path, int width, int length){
        JButton jButton;
        try {
            BufferedImage buttonBuffered;
            buttonBuffered = ImageIO.read(new File(path));
            Image buttonImage = buttonBuffered.getScaledInstance(width, length, Image.SCALE_DEFAULT);
            ImageIcon buttonIcon = new ImageIcon(buttonImage);
            jButton = new JButton(buttonIcon);
            jButton.setBorder(BorderFactory.createEmptyBorder());
            jButton.setContentAreaFilled(false);
            jButton.setBorderPainted(false);
            jButton.setFocusPainted(false);
        } catch (IOException e) {
            jButton = new JButton();
        }
        return jButton;
    }

    public void updateTable(String[][] data, int[] dataIds, String playlistName, int playlistId, int songPlayed, String description, boolean isOwner) {
        this.playlistName.setText(playlistName);
        this.playlistId = playlistId;
        if (description.length() > 0)
            this.playlistDesc.setText(description);

        Object[][] finalData;
        finalData = new Object[data.length][4];

        for (int i = 0; i < data.length; i++){
            if (songPlayed == dataIds[i]) {
                finalData[i][0] = pauseButton;
            }
            else {
                finalData[i][0] = playButton;
            }
            finalData[i][1] = data[i][0];
            finalData[i][2] = data[i][1];
            finalData[i][3] = binButton;
        }
        tableModel.updateTable(finalData, dataIds);
        tableModel.setColumnWidth(0, 60);
        tableModel.setColumnWidth(3, 60);

        addButton.setVisible(isOwner);
        deleteButton.setVisible(isOwner);
        reorderButton.setVisible(isOwner);
    }

    public void setListeners(MouseListener listener){
        tableModel.getTable().addMouseListener(listener);
        addButton.addActionListener((ActionListener) listener);
        deleteButton.addActionListener((ActionListener) listener);
        reorderButton.addActionListener((ActionListener) listener);

        reorderButton.setActionCommand("reOrder");
        addButton.setActionCommand("addButton");
        deleteButton.setActionCommand("deleteButton");
    }

    public void setPlayButton(int row){
        tableModel.changeCellObject(row, 0, playButton);
    }

    public void setPauseButton(int row){
        tableModel.changeCellObject(row, 0, pauseButton);
    }

    public int getPlaylistId(){
        return playlistId;
    }

    public JTable getTable(){
        return tableModel.getTable();
    }

    public int getUserRowId(int row){
        return tableModel.getRowId(row);
    }

    public void toggleReorder(String message){
        if (reordering == 2) {
            reordering = 0;
            reorderButton.setBackground(Globals.greenSpotify);
        }
        else if (reordering == 1){
            if (message.equals("press")){
                reordering = 0;
                reorderButton.setBackground(Globals.greenSpotify);
            }
            else {
                reordering = 2;
            }
        }
        else {
            reordering = 1;
            reorderButton.setBackground(new Color(85, 85, 85));
        }
    }

    public JButton getReorderButton(){
        return reorderButton;
    }

    public int getReorderingStatus(){
        return reordering;
    }

    @Override
    public void clearFields() {
        reorderButton.setBackground(Globals.greenSpotify);
    }

    @Override
    public String getTag() {
        return getName();
    }
}

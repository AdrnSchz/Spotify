package presentation.controllers.media;

import business.*;
import business.entities.Song;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.components.JTableModel;
import presentation.views.media.PlaylistSongsUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PlaylistSongsController extends MouseAdapter implements ActionListener, Observer, Observable, MouseListener {

    private final PlaylistSongsUI ui;
    private final Player player;
    private final SongDetailsController songDetailsController;
    private final SongManager songManager;
    private final UserManager userManager;
    private final FrameController frameController;
    private final PlaylistManager playlistManager;
    private final ArrayList<Observer> listeners = new ArrayList<>();
    private int songToMove1;
    private int songToMove2;

    public PlaylistSongsController(Player player, SongDetailsController songDetailsController, SongManager songManager,
                                   UserManager userManager, PlaylistManager playlistManager, FrameController frameController){
        this.frameController = frameController;
        this.player = player;
        this.songManager = songManager;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
        this.songDetailsController = songDetailsController;
        this.songDetailsController.attach(this);
        ui = new PlaylistSongsUI();
        ui.setName(Globals.PLAYLIST_SONGS);
        ui.setListeners(this);
        frameController.addCard(ui, Globals.PLAYLIST_SONGS);
        player.attach(this);
    }

    public void updatePlaylist (int playlistId) {
        int songId = -1;
        String playlistName = "";
        try {
            if (playlistManager.getPlaylistWithSongs(playlistId) != null){
                playlistName = playlistManager.getPlaylistWithSongs(playlistId).getName();
            }
        }
        catch (BusinessException e){
            playlistName = "";
        }

        String[][] rawData = new String[0][];
        try {
            rawData = playlistManager.getSongsInPlaylist(playlistId);
        } catch (BusinessException e) {
            frameController.showError(e.getMessage());
        }
        String[][] data = new String[rawData.length][2];
        int[] dataIds = new int[rawData.length];

        for (int i = 0; i < rawData.length; i++){
            for (int j = 0; j < rawData.length; j++) {
                if (i == j) {
                    data[i][0] = rawData[i][0];
                    data[i][1] = rawData[i][1];
                    dataIds[i] = Integer.parseInt(rawData[i][2]);
                    break;
                }
            }
        }
        try {
            if (player.getCurrentSong() != null){
                songId = player.getCurrentSong().getId();
            }
            if (playlistManager.getPlaylistWithSongs(playlistId) != null) {
                boolean isOwner = playlistManager.getPlaylistWithSongs(playlistId).getOwner().equals(userManager.getCurrentUser());

                ui.updateTable(data, dataIds, playlistName, playlistId, songId,
                        playlistManager.getPlaylistWithSongs(playlistId).getDescription(), isOwner);
            }
        }
        catch (BusinessException e){
            frameController.showError(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable sourceTable = (JTable) e.getSource();

        if (frameController.getCurrentScreen().equals(Globals.AVAILABLE_SONGS) && !e.getSource().equals(ui.getTable())){
            int row = sourceTable.rowAtPoint(e.getPoint());
            int songId = Integer.parseInt((String)sourceTable.getValueAt(row, 0));
            try {
                playlistManager.addSongToPlaylist(songId, ui.getPlaylistId());
            }
            catch (BusinessException ex){
                frameController.showError(ex.getMessage());
            }
            frameController.forgetCurrentScreen();
            update();
            return;
        }

        if (frameController.getCurrentScreen().equals(Globals.SONG_DETAILS)){
            return;
        }
        int row = ui.getTable().rowAtPoint(e.getPoint());
        int col = ui.getTable().columnAtPoint(e.getPoint());
        ui.getTable().clearSelection();
        if (row < 0){
            return;
        }
        int songId = ui.getUserRowId(row);

        if (ui.getReorderingStatus() == 0) {
            if (col == 0 && row >= 0) {
                try {
                    if (player.getCurrentSong() == null || songId != player.getCurrentSong().getId()) {
                        player.queue(songManager.getSong(songId), playlistManager.getPlaylistWithSongs(ui.getPlaylistId()));
                    }
                    if (player.getCurrentSong() != null) {
                        player.togglePause();
                    }
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            } else if ((col == 1 || col == 2) && row >= 0) {
                songDetailsController.setSong(songId);
                frameController.swapScreen(ui, Globals.SONG_DETAILS);
            } else if (col == 3 && row >= 0) {
                try {
                    if (userManager.getCurrentUser().equals(playlistManager.getPlaylistWithSongs(ui.getPlaylistId()).getOwner())) {
                        playlistManager.deleteSongInPlaylist(songId, ui.getPlaylistId());
                        updatePlaylist(ui.getPlaylistId());
                    }
                } catch (BusinessException ex) {
                    frameController.showError(ex.getMessage());
                }
            }
        }
        else if (ui.getReorderingStatus() == 1 && row >= 0){
            try {
                if (userManager.getCurrentUser().equals(playlistManager.getPlaylistWithSongs(ui.getPlaylistId()).getOwner())) {
                    songToMove1 = ui.getUserRowId(row);
                    ui.toggleReorder("");
                }
            } catch (BusinessException ex) {
                frameController.showError(ex.getMessage());
            }
        }
        else if (ui.getReorderingStatus() == 2 && row >= 0) {
            try {
                if (userManager.getCurrentUser().equals(playlistManager.getPlaylistWithSongs(ui.getPlaylistId()).getOwner())) {
                    songToMove2 = ui.getUserRowId(row);
                }
                songToMove2 = ui.getUserRowId(row);
                playlistManager.swapPositions(ui.getPlaylistId(), songToMove1, songToMove2);
                update();
                ui.toggleReorder("");
                if (player.getCurrentPlaylist() != null && player.getCurrentPlaylist().getId() == ui.getPlaylistId()) {
                    player.queue(playlistManager.getPlaylistWithSongs(ui.getPlaylistId()));
                }
            } catch (BusinessException ex) {
                frameController.showError(ex.getMessage());
            }
        }
    }

    @Override
    public void update(String message, Song song) {
        SwingUtilities.invokeLater(()->{
            JTable table = ui.getTable();
            if (song == null) {
                if (message.equals(Player.STOP)){
                    for (int i = 0; i < table.getRowCount(); i++) {
                        ui.setPlayButton(i);
                    }
                }
                return;
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                ui.setPlayButton(i);

                if (table.getValueAt(i, 1).equals(song.getTitle())) {
                    if (message.equals(Player.SONG_START) || message.equals(Player.SONG_RESUME) ){
                        ui.setPauseButton(i);
                    }
                }
            }
        });
    }

    @Override
    public void update() {
        updatePlaylist(ui.getPlaylistId());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("reOrder")){
            ui.toggleReorder("press");
            return;
        }
        try {
            if (userManager.getCurrentUser().equals(playlistManager.getPlaylistWithSongs(ui.getPlaylistId()).getOwner())) {
                if (e.getActionCommand().equals("addButton")) {
                    notifyObservers("adding song");
                    frameController.swapScreen(ui, Globals.AVAILABLE_SONGS);
                } else if (e.getActionCommand().equals("deleteButton")) {
                    try {
                        playlistManager.deletePlaylist(ui.getPlaylistId());
                        notifyObservers("deleted playlist");
                        frameController.forgetCurrentScreen();
                    } catch (BusinessException ex) {
                        frameController.showError(ex.getMessage());
                    }
                }
            }
        } catch (BusinessException ex) {
            frameController.showError(ex.getMessage());
        }
    }

    @Override
    public void attach(Observer o) {
        listeners.add(o);
    }

    @Override
    public void detach(Observer o) {
        listeners.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer listener : listeners) {
            listener.update();
        }
    }
}
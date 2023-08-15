package presentation.controllers.media;

import business.Observer;
import business.PlaylistManager;
import business.UserManager;
import business.entities.Song;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.media.ListPlaylistsUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListPlaylistsController extends MouseAdapter implements Observer {

    private final ListPlaylistsUI ui;
    private final PlaylistManager playlistManager;
    private final PlaylistSongsController playlistSongsController;
    private final UserManager userManager;
    private final FrameController frameController;

    public ListPlaylistsController(FrameController frameController, PlaylistManager playlistManager,
                                   PlaylistSongsController playlistSongsController, UserManager userManager){
        this.playlistManager = playlistManager;
        this.playlistSongsController = playlistSongsController;
        this.playlistSongsController.attach(this);
        this.frameController = frameController;
        this.userManager = userManager;
        ui = new ListPlaylistsUI();
        ui.setName(Globals.AVAILABLE_PLAYLISTS);
        ui.setListeners(this);
        frameController.addCard(ui, Globals.AVAILABLE_PLAYLISTS);
    }

    public void updatePlaylistsData(){
        try {
            String[][] allPlaylistsRaw = playlistManager.getAllPlaylists();
            ui.updatePlaylists(allPlaylistsRaw, userManager.getCurrentUser());
        }
        catch (BusinessException e){
            frameController.showError(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int playlistId;

        if (e.getSource() == ui.getGlobalTable()) {
            playlistId = ui.getPlayListAtPointGlobalTable(e.getPoint());
            playlistSongsController.updatePlaylist(playlistId);
            frameController.swapScreen(ui, Globals.PLAYLIST_SONGS);
        }
        else if (e.getSource() == ui.getUserTable()){
            playlistId = ui.getPlayListAtPointUserTable(e.getPoint());
            playlistSongsController.updatePlaylist(playlistId);
            frameController.swapScreen(ui, Globals.PLAYLIST_SONGS);
        }
    }

    @Override
    public void update(String message, Song song) {}

    @Override
    public void update() {
        updatePlaylistsData();
    }

}

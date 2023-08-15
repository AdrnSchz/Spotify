package business;

import business.entities.Playlist;
import business.exceptions.BusinessException;
import persistence.PlaylistDAO;
import persistence.db.DBPlaylistDAO;
import persistence.db.Database;
import persistence.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    private final PlaylistDAO playlistDAO;
    private final UserManager userManager;

    public PlaylistManager(Database db, UserManager userManager) {
        playlistDAO = new DBPlaylistDAO(db);
        this.userManager = userManager;
    }

    public void deleteUserPlaylists(String username) throws BusinessException {
        try {
            playlistDAO.deletePlaylistsByUser(username);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    public void createPlaylist(Playlist playlist) throws BusinessException {
        try {
            playlistDAO.createPlaylist(playlist);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    public String[][] getAllPlaylists() throws BusinessException{

        List<Playlist> playlists;
        try {
            playlists = playlistDAO.getPlaylists();
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }

        if (playlists != null) {
            String[][] data = new String[playlists.size()][3];

            for (int i = 0; i < playlists.size(); i++) {
                data[i][0] = playlists.get(i).getName();
                data[i][1] = playlists.get(i).getOwner();
                data[i][2] = Integer.toString(playlists.get(i).getId());
            }
            return data;
        }
        return new String[0][0];
    }

    public String[] getUserPlaylistsName() throws BusinessException {

        List<Playlist> allPlaylists;
        List<String> userPlaylists = new ArrayList<>();
        try {
            allPlaylists = playlistDAO.getPlaylists();
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }

        for (int i = 0; i < allPlaylists.size(); i++) {
            if (allPlaylists.get(i).getOwner().equals(userManager.getCurrentUser())) {
                userPlaylists.add(allPlaylists.get(i).getName());
            }
        }

        return userPlaylists.toArray(new String[0]);
    }

    public int[] getUserPlaylistsId() throws BusinessException {

        List<Playlist> allPlaylists;
        List<Integer> userPlaylists = new ArrayList<>();
        try {
            allPlaylists = playlistDAO.getPlaylists();
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }

        for (int i = 0; i < allPlaylists.size(); i++) {
            if (allPlaylists.get(i).getOwner().equals(userManager.getCurrentUser())) {
                userPlaylists.add(allPlaylists.get(i).getId());
            }
        }

        int[] array = new int[userPlaylists.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = userPlaylists.get(i);
        }

        return array;
    }

    public String[][] getSongsInPlaylist(int id) throws BusinessException {
        try {
            Playlist playlist = playlistDAO.getPlaylistWithSongs(id);
            String[][] songs = new String[playlist.getSongs().size()][3];

            for (int i = 0; i < playlist.getSongs().size(); i++){
                songs[i][0] = playlist.getSongs().get(i).getTitle();
                songs[i][1] = playlist.getSongs().get(i).getOwner();
                songs[i][2] = Integer.toString(playlist.getSongs().get(i).getId());
            }
            return songs;
        }
        catch (PersistenceException e){
            throw new BusinessException(e);
        }
        catch (NullPointerException ignored){
        }
        return new String[0][0];
    }

    public boolean addSongToPlaylist(int songId, int playlistId) throws BusinessException{
        try {
            Playlist playlist = playlistDAO.getPlaylistWithSongs(playlistId);
            return playlistDAO.addSong(playlistId, songId);
        }
        catch (PersistenceException e){
            throw new BusinessException(e);
        }
    }
    public Playlist getPlaylistWithSongs(int id) throws BusinessException{
        try {
            return playlistDAO.getPlaylistWithSongs(id);
        }
        catch (PersistenceException e){
            throw new BusinessException(e);
        }
    }

    public void deletePlaylist(int id) throws BusinessException{
        try {
            playlistDAO.deletePlaylist(id);
        }
        catch (PersistenceException e){
            throw new BusinessException(e);
        }
    }

    public void deleteSongInPlaylist(int songId, int playlistId) throws BusinessException{
        try {
            Playlist playlist = playlistDAO.getPlaylistWithSongs(playlistId);
            for (int i = 0; i < playlist.getSongs().size(); i++){
                if (playlist.getSongs().get(i).getId() == songId){
                    playlistDAO.removeSong(playlistId, songId);
                }
            }
        } catch (PersistenceException e){
            throw new BusinessException(e);
        }
    }

    public void swapPositions(int playlistId, int songId1, int songId2) throws BusinessException {
        try {
            int position1 = 0;
            int position2 = 0;
            Playlist playlist = playlistDAO.getPlaylistWithSongs(playlistId);
            for (int i = 0; i < playlist.getSongs().size(); i++) {
                if (songId1 == playlist.getSongs().get(i).getId()) {
                    position1 = playlist.getSongs().get(i).getPosition();
                }
                if (songId2 == playlist.getSongs().get(i).getId()) {
                    position2 = playlist.getSongs().get(i).getPosition();
                }
            }
            playlistDAO.setPosition(playlistId, songId2, position1);
            playlistDAO.setPosition(playlistId, songId1, position2);
        }
        catch (PersistenceException e){
            throw new BusinessException(e);
        }
    }
}

import business.Player;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.exceptions.BusinessException;
import persistence.db.Database;
import persistence.exceptions.PersistenceException;
import presentation.controllers.FrameController;
import presentation.controllers.home.MainScreenController;
import presentation.controllers.home.MenuController;
import presentation.controllers.media.*;
import presentation.controllers.media.player.PlayerController;
import presentation.controllers.stats.StatsController;
import presentation.controllers.user.LogInController;
import presentation.controllers.user.LogOutController;
import presentation.controllers.user.SignUpController;
import presentation.views.*;

import javax.swing.*;

/**
 * Main class of the program.
 * Creates instances of the different classes and connects them.
 *
 * @author Group 6
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();

        try {

            Database db = new Database();

            UserManager userManager = new UserManager(db);
            SongManager songManager = new SongManager(db);
            PlaylistManager playlistManager = new PlaylistManager(db, userManager);
            Player player = new Player(songManager);

            SwingUtilities.invokeLater(() -> {

                FrameController frameController = new FrameController(mainFrame);
                new PlayerController(frameController, player);
                new SignUpController(userManager, frameController);
                new MenuController(frameController);
                new LogInController(frameController, userManager);
                new AddSongController(frameController, songManager, userManager);
                new CreatePlaylistController(frameController, playlistManager, userManager);
                new LogOutController(frameController, userManager, songManager, playlistManager, player);
                SongDetailsController songDetailsController = new SongDetailsController(frameController, songManager, player, playlistManager, userManager);
                PlaylistSongsController playlistSongsController = new PlaylistSongsController(player, songDetailsController, songManager, userManager, playlistManager, frameController);
                ListPlaylistsController listPlaylistsController = new ListPlaylistsController(frameController, playlistManager, playlistSongsController, userManager);
                AvailableSongsController availableSongsController = new AvailableSongsController(songManager, frameController, songDetailsController, playlistSongsController);
                StatsController statsController = new StatsController(frameController, songManager);
                new MainScreenController(frameController, listPlaylistsController, statsController, availableSongsController);
                frameController.config();
            });

        } catch (PersistenceException | BusinessException e) {
            mainFrame.showError(e.getMessage());
            System.exit(1);
        }
    }
}
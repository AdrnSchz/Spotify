package presentation.controllers.stats;

import business.SongManager;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.stats.StatsUI;

/**
 * Controller class for the StatsUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class StatsController {

    /**
     * StatsUI instance to display the stats.
     */
    private final StatsUI ui;
    /**
     * SongManager instance to retrieve the stats.
     */
    private final SongManager songManager;

    /**
     * Constructor method for StatsController.
     *
     * @param frameController FrameController instance to communicate with the main frame.
     * @param songManager SongManager instance to retrieve the stats.
     */
    public StatsController(FrameController frameController, SongManager songManager) {
        ui = new StatsUI();
        this.songManager = songManager;
        frameController.addCard(ui, Globals.STATS);
        ui.setName(Globals.STATS);
    }

    /**
     * Updates the stats in the StatsUI to the newest stats.
     */
    public void setStats() {
        ui.setStats(songManager.getStats());
    }

}

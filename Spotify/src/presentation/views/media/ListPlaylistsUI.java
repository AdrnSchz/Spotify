package presentation.views.media;

import presentation.Globals;
import presentation.views.Screen;
import presentation.views.components.JTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class ListPlaylistsUI extends Screen {

    private JTableModel globalModel;
    private JTableModel userModel;

    public ListPlaylistsUI(){

        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.add(Box.createRigidArea(new Dimension(1,40)), BorderLayout.SOUTH);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BorderLayout());
        tablesPanel.setBackground(Color.BLACK);

        JPanel flowLayoutTitle = new JPanel(new FlowLayout());
        flowLayoutTitle.setBackground(Color.BLACK);
        JLabel title = new JLabel("AVAILABLE PLAYLISTS");
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        title.setForeground(Globals.greenSpotify);
        flowLayoutTitle.add(title);
        flowLayoutTitle.add(Box.createRigidArea(new Dimension(675, 10)));
        tablesPanel.add(flowLayoutTitle, BorderLayout.NORTH);

        JPanel flowLayoutTables = new JPanel(new FlowLayout());
        flowLayoutTables.setBackground(Color.BLACK);
        configTables(flowLayoutTables);
        tablesPanel.add(flowLayoutTables, BorderLayout.CENTER);

        this.add(tablesPanel, BorderLayout.CENTER);
    }

    private void configTables(JPanel tablesPanel){
        globalModel = new JTableModel(new String[]{"GLOBAL LIBRARY", "OWNER"});
        userModel = new JTableModel(new String[]{"MY LIBRARY", "OWNER"});

        tablesPanel.add(globalModel.getScrollPane());
        tablesPanel.add(Box.createRigidArea(new Dimension(40, 40)));
        tablesPanel.add(userModel.getScrollPane());
    }

    public void setListeners(MouseListener listener){
        globalModel.getTable().addMouseListener(listener);
        userModel.getTable().addMouseListener(listener);
    }


    public void updatePlaylists(String[][] allPlaylistsRaw, String currentUser){

        String[][] allPlaylists = new String[allPlaylistsRaw.length][2];
        int[] allPlaylistsIds = new int[allPlaylistsRaw.length];

        int userPlaylistsIndex = 0;
        for (String[] strings : allPlaylistsRaw) {
            if (strings[1].equalsIgnoreCase(currentUser)) {
                userPlaylistsIndex++;
            }
        }
        String[][] userPlaylists = new String[userPlaylistsIndex][2];
        int[] userPlaylistsIds = new int[userPlaylistsIndex];
        userPlaylistsIndex = 0;

        for (int i = 0; i < allPlaylistsRaw.length; i++) {
            allPlaylists[i][0] = allPlaylistsRaw[i][0];
            allPlaylists[i][1] = allPlaylistsRaw[i][1];
            allPlaylistsIds[i] = Integer.parseInt(allPlaylistsRaw[i][2]);

            if (allPlaylistsRaw[i][1].equalsIgnoreCase(currentUser)) {
                userPlaylists[userPlaylistsIndex][0] = allPlaylistsRaw[i][0];
                userPlaylists[userPlaylistsIndex][1] = allPlaylistsRaw[i][1];
                userPlaylistsIds[userPlaylistsIndex] = Integer.parseInt(allPlaylistsRaw[i][2]);
                userPlaylistsIndex++;
            }
        }
        globalModel.updateTable(allPlaylists, allPlaylistsIds);
        userModel.updateTable(userPlaylists, userPlaylistsIds);
    }

    public JTable getGlobalTable(){
        return globalModel.getTable();
    }

    public JTable getUserTable(){
        return userModel.getTable();
    }

    public int getGlobalRowId(int row){
        return globalModel.getRowId(row);
    }
    public int getUserRowId(int row){
        return userModel.getRowId(row);
    }

    @Override
    public void clearFields() {}

    @Override
    public String getTag() {
        return getName();
    }

    public int getPlayListAtPointGlobalTable(Point point) {
        int playlistInfo = 0;
        int row;
        int col;

        row = getGlobalTable().rowAtPoint(point);
        col = getGlobalTable().columnAtPoint(point);
        getGlobalTable().clearSelection();

        if ((col == 0 || col == 1) && row >= 0) {
            playlistInfo = getGlobalRowId(row);
        }

        return playlistInfo;
    }

    public int getPlayListAtPointUserTable(Point point) {
        int playlistInfo = 0;
        int row;
        int col;
        row = getUserTable().rowAtPoint(point);
        col = getUserTable().columnAtPoint(point);
        getUserTable().clearSelection();

        if ((col == 0 || col == 1) && row >= 0) {
            playlistInfo = getUserRowId(row);
        }

        return playlistInfo;
    }
}

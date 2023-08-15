package presentation.views.media;

import presentation.Globals;
import presentation.views.Screen;
import presentation.views.components.JTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * AvailableSongsUI class that represents the view to display all of the stored songs.
 *
 * @author Group 6
 * @version 1.0
 */
public class AvailableSongsUI extends Screen {
    /**
     * JTextField for the search bar.
     */
    private JTextField search;
    /**
     * JTable representing the stored songs.
     */
    private JTable table;
    /**
     * JTableModel representing the model of the JTable.
     */
    private JTableModel jTableModel;
    /**
     * TableRowSorter to sort the JTable.
     */
    private TableRowSorter <TableModel> tableRowSorter;
    /**
     * Dimension representing the size of the search bar.
     */
    public static final Dimension fieldDimension = new Dimension(525, 20);

    /**
     * Constructor method of AvailableSongsUI.
     */
    public AvailableSongsUI() {
        this.configLayout();
    }

    /**
     * Method that configures the general layout of the view.
     */
    private void configLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.black);
        configTop();
        configCenter();
    }

    /**
     * Method that configures the top panel of the view (Title and subtitle).
     */
    private void configTop() {
        JPanel topPanel =  new JPanel();
        topPanel.setBackground(Color.black);
        topPanel.setLayout(new GridLayout(2, 1));
        topPanel.setMaximumSize(new Dimension(500, 30));
        topPanel.setMinimumSize(new Dimension(500, 30));

        JLabel title = new JLabel("AVAILABLE SONGS");
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        title.setForeground(Globals.greenSpotify);
        topPanel.add(title);

        JLabel subTitle = new JLabel("Discover a new world");
        subTitle.setFont(new Font("Calibri", Font.ITALIC, 20));
        subTitle.setForeground(Globals.greenSpotify);
        topPanel.add(subTitle);

        this.add(topPanel);
    }

    /**
     * Method that configures the center panel of the view (Table and search bar).
     */
    private void configCenter() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.black);
        centerPanel.setLayout(new GridLayout(1, 3));
        centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.black);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
        tablePanel.add(Box.createRigidArea(new Dimension(1, 20)));

        search = new JTextField();
        search.setMaximumSize(fieldDimension);
        search.setMinimumSize(fieldDimension);
        search.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tablePanel.add(search);
        tablePanel.add(Box.createRigidArea(new Dimension(10, 5)));

        this.jTableModel = new JTableModel(new String[]{"ID","TITLE","GENRE","ALBUM","ARTIST","OWNER"});

        this.table = this.jTableModel.getTable();

        tableRowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(tableRowSorter);

        tablePanel.add(this.jTableModel.getScrollPane());

        centerPanel.add(tablePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(centerPanel);

        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = getTitle();
                searchText(text);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = getTitle();
                searchText(text);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    /**
     * Method used to update the table with the songs passed as a parameter.
     * @param songs 2D array of objects representing the songs to be displayed in the table.
     * @param listSongs List of songs to be displayed in the table.
     */
    public void updateTable(Object[][] songs, List<String[]> listSongs) {
        for(int i = 0; i < listSongs.size(); i++) {
            Object[] song = listSongs.get(i);
            songs[i] = (new Object[]{song[0], song[1], song[2], song[3], song[4], song[5]});
        }

        jTableModel.updateTable(songs);
    }

    /**
     * Method used to filter the table with the text passed as a parameter.
     * @param text String representing the text to be used as a filter.
     */
    public void searchText(String text) {
        if(text.length() == 0) {
            tableRowSorter.setRowFilter(null);
        }
        else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    /**
     * Method used to get the song from the selected cell in the table.
     * @param point Point representing the position of the cell selected.
     * @return the id of the chosen song.
     */
    public String getSongAtPoint(Point point) {
        int row;
        row = getSongsTable().rowAtPoint(point);
        getSongsTable().clearSelection();

        if (row >= 0) {
            String id = (String) getSongsTable().getValueAt(row, 0);
            return id;
        }
        return null;
    }

    /**
     * Method used to get the table.
     * @return the table.
     */
    public JTable getSongsTable() {
        return jTableModel.getTable();
    }

    /**
     * Method used to get the song title.
     * @return the song title.
     */
    public String getTitle() {
        return search.getText();
    }

    /**
     * Method that registers a listener to the table.
     * @param mouseListener listener to be registered.
     */
    public void setListeners(MouseListener mouseListener) {
        jTableModel.getTable().addMouseListener(mouseListener);

    }

    /**
     * Method that to clear the search bar.
     */
    @Override
    public void clearFields() {
        search.setText("");
    }

    /**
     * Method that retrieves the name of the component.
     *
     * @return String indicating the name of the component.
     */
    @Override
    public String getTag() {
        return getName();
    }
}
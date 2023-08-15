package presentation.views.components;

import presentation.Globals;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JTableModel extends DefaultTableModel {

    JTable table;
    JScrollPane scroll;
    DefaultTableCellRenderer renderer;
    String[] columnNames;
    int[] rowIds;

    public JTableModel(String[] names) {

        table = new JTable(this);
        table.getTableHeader().setBackground(Globals.greenSpotify);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Calibri", Font.PLAIN, 15));
        table.setBackground(Color.GRAY);
        table.setRowHeight(30);
        table.setFont(new Font("Calibri", Font.PLAIN, 12));
        table.setCellSelectionEnabled(false);
        table.setShowGrid(false);
        table.setRowMargin(0);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        columnNames = names;

        scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.black);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jTable, Object object, boolean bln, boolean bln1, int row, int col) {
                if (object instanceof JButton) {
                    return (JButton) object;
                }
                return super.getTableCellRendererComponent(jTable, object.toString(), bln, false, row, col);
            }
        };
    }

    public void updateTable(Object[][] update){
        setDataVector(update, columnNames);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            for (int i = 0; i < update[0].length; i++){
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored){
        }
    }

    public void updateTable(Object[][] update, int[] idsUpdate){
        rowIds = idsUpdate;
        setDataVector(update, columnNames);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            for (int i = 0; i < update[0].length; i++){
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored){
        }
    }
    public int getRowId(int row){
        return rowIds[row];
    }

    public JTable getTable(){
        return table;
    }

    public JScrollPane getScrollPane(){
        return scroll;
    }
    public void setColumnWidth(int column,  int width){
        table.getColumnModel().getColumn(column).setMaxWidth(width);
        table.getColumnModel().getColumn(column).setMinWidth(width);

    }
    public void changeCellObject(int row, int col, Object object) {
        table.setValueAt(object, row, col);
    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addExtraColumn(String columnName){
        addColumn(columnName);
        table.getColumnModel().getColumn(getColumnCount()-1).setMinWidth(60);
        table.getColumnModel().getColumn(getColumnCount()-1).setMinWidth(60);
    }
}

package presentation.views.components;

import presentation.Globals;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JIconButton extends JButton {

    private final String icon;
    private final String paintedIcon;
    private final String clickedIcon;
    private final JLabel label;
    private boolean clicked;
    private final boolean clickable;

    public JIconButton(String iconPath, String paintedIconPath, String clickedIconPath, JLabel text, Color color, boolean click) {
        icon = iconPath;
        paintedIcon = paintedIconPath;
        clickedIcon = clickedIconPath;
        label = text;
        clicked = false;
        clickable = click;

        setBackground(color);
        setForeground(color);
        setIcon(new ImageIcon(iconPath));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!clicked) {
                    setIcon(new ImageIcon(paintedIcon));
                    setBackground(Globals.greenSpotify);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!clicked) {
                    setIcon(new ImageIcon(icon));
                    setBackground(Globals.greyPlayer);
                }
            }
        });
    }

    public JIconButton(String iconPath, String paintedIcon, JLabel text, Color color, boolean clickable){
        this(iconPath, paintedIcon, iconPath, text, color, clickable);
    }

    public JIconButton(String iconPath, String paintedIcon, Color color, boolean clickable){
        this(iconPath, iconPath, iconPath, null, color, clickable);
    }

    public JIconButton(String iconPath, Color color, boolean clickable){
        this(iconPath, iconPath, iconPath, null, color, clickable);
    }

    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_CLICKED && clickable) {
            if(!clicked){
                setIcon(new ImageIcon(clickedIcon));
                setBackground(Globals.greenSpotify);
                if(label != null) {
                    label.setForeground(Globals.greenSpotify);
                }
            }
            else{
                setIcon(new ImageIcon(icon));
                setBackground(Globals.greyPlayer);
                if(label != null) {
                    label.setForeground(Color.white);
                }
            }
            clicked = !clicked;
        }
    }
}
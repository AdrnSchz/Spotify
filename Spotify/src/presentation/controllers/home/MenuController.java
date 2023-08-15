package presentation.controllers.home;

import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.home.MenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuController extends MouseAdapter implements ActionListener {

    private final MenuUI ui;
    private final FrameController frameController;

    public MenuController(FrameController frameController){
        this.ui = new MenuUI();
        this.frameController = frameController;
        frameController.addCard(ui, Globals.MENU);
        ui.setListeners(this);
        ui.setName(Globals.MENU);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frameController.swapScreen(ui, e.getActionCommand());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ui.setButtonForeground((JButton) e.getSource(), Color.white);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ui.setButtonForeground((JButton) e.getSource(), Color.black);
    }

}

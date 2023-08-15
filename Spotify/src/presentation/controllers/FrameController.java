package presentation.controllers;

import presentation.Globals;
import presentation.views.MainFrame;
import presentation.views.Screen;
import presentation.views.media.player.PlayerUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class FrameController implements ActionListener {

    private final MainFrame mainFrame;
    private final Stack<Screen> previousScreen;
    private String currentScreen;

    public FrameController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        previousScreen = new Stack<>();
    }

    public void config() {
        mainFrame.config();
        mainFrame.setMainListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            default -> mainFrame.selectScreen(e.getActionCommand());
            case Globals.JB_BACK -> {
                mainFrame.setTopVisibility(true);
                Screen previousScreen = getPreviousScreen();
                Screen currentScreen = mainFrame.getCurrentCard();
                currentScreen.clearFields();
                mainFrame.selectScreen(previousScreen.getTag());
                previousScreen.clearFields();
            }

            case Globals.JB_SETTINGS -> {
                mainFrame.setTopVisibility(false);
                Screen currentScreen = mainFrame.getCurrentCard();
                currentScreen.clearFields();
                previousScreen.add(currentScreen);
                mainFrame.selectScreen(Globals.LOG_OUT);
            }
        }
    }

    public void addCard(JPanel panel, String tag){
        mainFrame.addCard(panel, tag);
    }

    public void setPlayer(PlayerUI playerUI) {
        mainFrame.setPlayer(playerUI);
    }

    public void showError(String err) {
        mainFrame.showError(err);
    }

    public void showMessage(String message){
        mainFrame.showMessage(message);
    }

    public void swapScreen(Screen currentScreen, String nextScreen){
        this.currentScreen = nextScreen;
        setPreviousScreen(currentScreen);
        mainFrame.selectScreen(nextScreen);
    }

    public void forgetCurrentScreen(){
        currentScreen = getPreviousScreenTag();
        mainFrame.selectScreen(getPreviousScreen().getTag());
    }

    public void setPreviousScreen(Screen screen){
        previousScreen.add(screen);
    }

    public Screen getPreviousScreen(){
        return previousScreen.pop();
    }

    public String getCurrentScreen(){
        return currentScreen;
    }

    public String getPreviousScreenTag(){
        return previousScreen.get(previousScreen.size()-1).getTag();
    }

    public void clearPreviousScreens(){
        previousScreen.clear();
    }

    public void togglePlayer(){
        mainFrame.togglePlayer();
    }

    public MainFrame getFrame() {
        return mainFrame;
    }

}
package presentation.views.home;

import presentation.Globals;
import presentation.views.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

public class MenuUI extends Screen {

    private JButton signUp, logIn;

    public MenuUI() {
        configLayout();
    }

    private void configLayout() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.black);
        add(Box.createRigidArea(new Dimension(750, 125)));

        configCenter();
    }

    private void configCenter() {

        BufferedImage bufferedLogo;
        try {
            bufferedLogo = ImageIO.read(new File("files/images/logo.png"));
            Image logoImage = bufferedLogo.getScaledInstance(135, 135, Image.SCALE_DEFAULT);
            JLabel logo = new JLabel(new ImageIcon(logoImage));
            //JPanel and set to image to background of JPanel
            logo.setBackground(Color.BLACK);
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(logo);
        } catch (IOException e) {
            add(Box.createRigidArea(new Dimension(135, 135)));
        }

        add(Box.createRigidArea(new Dimension(750, 30)));

        JLabel appName = new JLabel("ESPOTIFAI");
        appName.setFont(new Font("Calibri", Font.BOLD, 40));
        appName.setForeground(Globals.greenSpotify);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(appName);
        add(Box.createRigidArea(new Dimension(750, 30)));

        signUp = new JButton("SIGN UP");
        signUp.setFont(new Font("SIGN UP", Font.BOLD, 15));
        signUp.setMaximumSize(Globals.button_dimension);
        signUp.setMinimumSize(Globals.button_dimension);
        signUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUp.setBorderPainted(false);
        signUp.setFocusPainted(false);
        signUp.setBackground(Globals.greenSpotify);
        signUp.setForeground(Color.black);
        signUp.setOpaque(true);

        add(signUp);
        add(Box.createRigidArea(new Dimension(750, 20)));

        logIn = new JButton("LOG IN");
        logIn.setFont(new Font("LOG IN", Font.BOLD, 15));
        logIn.setMaximumSize(Globals.button_dimension);
        logIn.setMinimumSize(Globals.button_dimension);
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logIn.setBorderPainted(false);
        logIn.setFocusPainted(false);
        logIn.setBackground(new Color(85, 85, 85));
        logIn.setForeground(Globals.greenSpotify);
        logIn.setOpaque(true);

        add(logIn);
    }

    public void setListeners(EventListener listener){
        signUp.setActionCommand(Globals.SIGN_UP);
        logIn.setActionCommand(Globals.LOG_IN);

        signUp.addMouseListener((MouseListener) listener);
        signUp.addActionListener((ActionListener) listener);
        logIn.addMouseListener((MouseListener) listener);
        logIn.addActionListener((ActionListener) listener);
    }

    @Override
    public void clearFields() {}

    @Override
    public String getTag() {
        return getName();
    }

    public void setButtonForeground(JButton button, Color color) {
        button.setForeground(color);
    }
}
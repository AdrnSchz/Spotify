package presentation.views.user;

import presentation.Globals;
import presentation.views.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

/**
 * LogInUI class that represents the view to display the login screen.
 *
 * @author Group 6
 * @version 1.0
 */
public class LogInUI extends Screen {
    /**
     * JTextField instance to write the username.
     */
    private JTextField usernameField;
    /**
     * JPasswordField instance to write the password.
     */
    private JPasswordField passwordField;
    /**
     * JButton instance to log in.
     */
    private JButton logInButton;
    /**
     * JLabel instance to display a message.
     */
    private JLabel signUpMessage;

    /**
     * Constructor method for LogInUI.
     */
    public LogInUI() {
        configLayout();
    }

    /**
     * Method that configures the general layout of the view.
     */
    private void configLayout() {
        this.setLayout(new GridLayout(1, 3));
        this.setBackground(Color.black);

        configCenter();
    }

    /**
     * Setter method that sets the listeners.
     *
     * @param listener EventListener instance representing the listeners.
     */
    public void setListeners(EventListener listener) {
        logInButton.setActionCommand(Globals.JB_LOG_IN);

        logInButton.addActionListener((ActionListener) listener);
        passwordField.addKeyListener((KeyListener) listener);
        signUpMessage.addMouseListener((MouseListener) listener);
        logInButton.addMouseListener((MouseListener) listener);
    }

    /**
     * Getter method that returns the logInButton.
     *
     * @return Component instance representing the logInButton.
     */
    public Component getLogInButton() {
        return logInButton;
    }

    /**
     * Getter method that returns the PasswordField.
     *
     * @return Component instance representing the PasswordField.
     */
    public Component getPasswordField() {
        return passwordField;
    }

    /**
     * Getter method that returns the message displayed.
     *
     * @return Component instance representing the Label message.
     */
    public Component getMessage() {
        return signUpMessage;
    }

    /**
     * Method that presses the logInButton.
     */
    public void pressLogIn() {
        logInButton.doClick();
    }

    /**
     * Method that clears the password field.
     */
    public void clearPassword() {
        passwordField.setText("");
    }

    /**
     * Method that clears the username field.
     */
    public void clearUsername() {
        usernameField.setText("");
    }

    /**
     * Method that configures the center panel.
     */
    private void configCenter() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.setBackground(Color.black);
        //left border
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.setBackground(Color.black);

        //box layout
        JLabel logInTitle = new JLabel("LOG IN");
        logInTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        logInTitle.setForeground(Globals.greenSpotify);
        logInTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(logInTitle);

        JLabel usernameTitle = new JLabel("Username/Email");
        usernameTitle.setForeground(Color.white);
        usernameTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(usernameTitle);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 5)));

        usernameField = new JTextField();
        usernameField.setMaximumSize(Globals.fieldDimension);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        centerPanel.add(usernameField);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 15)));

        JLabel passwordTitle = new JLabel("Password");
        passwordTitle.setForeground(Color.white);
        passwordTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(passwordTitle);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 5)));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(Globals.fieldDimension);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        centerPanel.add(passwordField);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 25)));

        logInButton = new JButton("LOG IN");
        logInButton.setMaximumSize(Globals.fieldDimension);
        logInButton.setBackground(Globals.greenSpotify);
        logInButton.setForeground(Color.white);
        logInButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logInButton.setBorderPainted(false);
        logInButton.setFocusPainted(false);
        logInButton.setOpaque(true);
        centerPanel.add(logInButton);
        centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        signUpMessage = new JLabel("Don't have an account? Sign Up");
        signUpMessage.setFont(new Font("Calibri", Font.ITALIC, 15));
        signUpMessage.setForeground(Globals.greenSpotify);
        signUpMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(signUpMessage);
        this.add(centerPanel);

        //right border
        this.add(Box.createRigidArea(new Dimension(1, 0)));
        this.setBackground(Color.black);

    }

    /**
     * Getter method that returns the username.
     *
     * @return String representing the username.
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Getter method that returns the password.
     *
     * @return String representing the password.
     */
    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    /**
     * Method that clears the fields of the view.
     *
     */
    @Override
    public void clearFields() {
        clearUsername();
        clearPassword();
    }

    /**
     * Method that retrieves the name of the component
     *
     * @return String indicating the name of the component
     */
    @Override
    public String getTag() {
        return getName();
    }
}

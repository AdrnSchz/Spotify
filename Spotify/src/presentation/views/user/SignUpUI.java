package presentation.views.user;

import presentation.Globals;
import presentation.views.Screen;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * SignUpUI class that represents the view to create a user.
 *
 * @author Group 6
 * @version 1.0
 */
public class SignUpUI extends Screen {
    /**
     * Dimension representing the rigid boxes used to compensate the view.
     */
    public static final Dimension fieldDimension = new Dimension(500, 30);
    /**
     * JTextFields representing the username to be entered.
     */
    private JTextField username;
    /**
     * JTextFields representing the email to be entered.
     */
    private JTextField email;
    /**
     * JPasswordFields representing the password to be entered.
     */
    private JPasswordField password;
    /**
     * JPasswordFields representing the password confirmation to be entered.
     */
    private JPasswordField confirmation;
    /**
     * JButtons representing the sign up button.
     */
    private JButton signUpButton;

    /**
     * Constructor method of SignUpUI.
     */
    public SignUpUI() {
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
     * Method that registers a listener to the buttons.
     * @param actionListener listener to be registered.
     */
    public void setListeners(ActionListener actionListener) {
        signUpButton.setActionCommand(Globals.JB_SIGN_UP);
        signUpButton.addActionListener(actionListener);

        confirmation.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    signUpButton.doClick();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /**
     * Method that returns the information entered by the user.
     * @return String[] representing the user's data.
     */
    public String[] getTextFields() {
        String[] infoUser = new String[4];
        infoUser[0] = username.getText();
        infoUser[1] = email.getText();
        infoUser[2] = String.valueOf(password.getPassword());
        infoUser[3] = String.valueOf(confirmation.getPassword());

        return infoUser;
    }

    /**
     * Method to clear the password fields.
     */
    public void clearPassword() {
        password.setText("");
        confirmation.setText("");
    }

    /**
     * Method that configures the central panel of the view.
     */
    private void configCenter() {
         JPanel centerPanel = new JPanel();
         centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
         centerPanel.setBackground(Color.black);

         this.add(Box.createRigidArea(new Dimension(10, 0)));
         this.setBackground(Color.black);

         JLabel signUpTitle = new JLabel("SIGN UP");
         signUpTitle.setFont(new Font("Calibri", Font.BOLD, 30));
         signUpTitle.setForeground(Globals.greenSpotify);
         signUpTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
         centerPanel.add(signUpTitle);

         JLabel usernameTitle = new JLabel("Username");
         usernameTitle.setForeground(Color.white);
         usernameTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
         centerPanel.add(usernameTitle);
         centerPanel.add(Box.createRigidArea(new Dimension(10, 5)));

         username = new JTextField();
         username.setMinimumSize(fieldDimension);
         username.setMaximumSize(fieldDimension);
         username.setAlignmentX(Component.LEFT_ALIGNMENT);
         username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
         centerPanel.add(username);

         centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));

         JLabel emailTitle = new JLabel("Email");
         emailTitle.setForeground(Color.white);
         emailTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
         centerPanel.add(emailTitle);
         centerPanel.add(Box.createRigidArea(new Dimension(10, 5)));

         email = new JTextField();
         email.setMinimumSize(fieldDimension);
         email.setMaximumSize(fieldDimension);
         email.setAlignmentX(Component.LEFT_ALIGNMENT);
         email.setBorder(javax.swing.BorderFactory.createEmptyBorder());
         centerPanel.add(email);

         centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));

         JLabel passwordTitle = new JLabel("Password");
         passwordTitle.setForeground(Color.white);
         passwordTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
         centerPanel.add(passwordTitle);
         centerPanel.add(Box.createRigidArea(new Dimension(10, 5)));

         password = new JPasswordField();
         password.setMinimumSize(fieldDimension);
         password.setMaximumSize(fieldDimension);
         password.setAlignmentX(Component.LEFT_ALIGNMENT);
         password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
         centerPanel.add(password);

         centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));

         JLabel confirmationTitle = new JLabel("Password Confirmation");
         confirmationTitle.setForeground(Color.white);
         confirmationTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
         centerPanel.add(confirmationTitle);
         centerPanel.add(Box.createRigidArea(new Dimension(10, 5)));

         confirmation = new JPasswordField();
         confirmation.setMinimumSize(fieldDimension);
         confirmation.setMaximumSize(fieldDimension);
         confirmation.setAlignmentX(Component.LEFT_ALIGNMENT);
         confirmation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
         centerPanel.add(confirmation);

         centerPanel.add(Box.createRigidArea(new Dimension(10, 20)));

         signUpButton = new JButton("SIGN UP");
         signUpButton.setBackground(Globals.greenSpotify);
         signUpButton.setForeground(Color.white);
         signUpButton.setOpaque(true);
         signUpButton.setAlignmentX(Component.LEFT_ALIGNMENT);
         signUpButton.setBorderPainted(false);
         signUpButton.setFocusPainted(false);
         signUpButton.setOpaque(true);
         signUpButton.setMinimumSize(fieldDimension);
         signUpButton.setMaximumSize(fieldDimension);
         signUpButton.setActionCommand(Globals.JB_SIGN_UP);
         signUpButton.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseEntered(MouseEvent e) {
                 signUpButton.setForeground(Color.black);
             }

             @Override
             public void mouseExited(MouseEvent e) {
                 signUpButton.setForeground(Color.white);
             }
         });
         centerPanel.add(signUpButton);
         this.add(centerPanel);

         this.add(Box.createRigidArea(new Dimension(10, 0)));
         this.setBackground(Color.black);
    }

    /**
     * Method that clears the text fields.
     */
    @Override
    public void clearFields() {
        username.setText("");
        email.setText("");
        password.setText("");
        confirmation.setText("");
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

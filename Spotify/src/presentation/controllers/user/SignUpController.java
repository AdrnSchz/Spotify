package presentation.controllers.user;

import business.UserManager;
import business.exceptions.BusinessException;
import presentation.Globals;
import presentation.controllers.FrameController;
import presentation.views.user.SignUpUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the SignUpUI.
 *
 * @author Group 6
 * @version 1.0
 */
public class SignUpController implements ActionListener {
    /**
     * UserManager instance to retrieve data from the users and store the current.
     */
    private final UserManager userManager;
    /**
     * SignUpUI instance to display the fields to create a user.
     */
    private final SignUpUI signUpUI;
    /**
     * FrameController instance to communicate with the MainFrame.
     */
    private final FrameController frameController;

    private final static String BLANK = "Blank field";
    private final static String USER_EXISTS = "Username already exists.";
    private final static String EMAIL_EXISTS = "Email already exists.";
    private final static String EMAIL = "Please enter a valid email.";
    private final static String PASSWORD = "Please enter a valid password.\n" + "(8 characters including 1 uppercase, 1 lowercase and 1 number).";
    private final static String MATCH = "Passwords do not match.";

    /**
     * Constructor method for SignUpController.
     *
     * @param frameController FrameController instance to communicate with the MainFrame.
     * @param userManager UserManager instance to retrieve data from the users and store the current.
     */
    public SignUpController(UserManager userManager, FrameController frameController) {
        this.frameController = frameController;
        this.userManager = userManager;
        this.signUpUI = new SignUpUI();
        frameController.addCard(signUpUI, Globals.SIGN_UP);
        signUpUI.setListeners(this);
        signUpUI.setName(Globals.SIGN_UP);

    }

    /**
     * Method that checks if there was any error with the given credentials.
     *
     * @return boolean the errors found, if any.
     */
    public String checkSignUp() throws BusinessException {
        String[] checkInfo = signUpUI.getTextFields();
        userManager.setCurrentUser(checkInfo[0]);
        String[] errors = new String[]{BLANK, USER_EXISTS, EMAIL, EMAIL_EXISTS, PASSWORD, MATCH};
        boolean[] info = userManager.checkInfo(checkInfo);

        for(int i = 0; i < info.length; i++) {
            if(info[i]) {
                signUpUI.clearPassword();
                return errors[i];
            }
        }
        signUpUI.clearFields();
        userManager.signUpUser(checkInfo);
        return null;
    }

    /**
     * Method that clears the fields of the SignUpUI.
     */
    public void clearFields() {
        signUpUI.clearFields();
    }

    /**
     * ActionListener to implement the button to sign up. Add when clicked, checks if the fields are correct.
     *
     * @param e the event to be processed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Globals.JB_SIGN_UP)) {
            String error = null;
            try {
                error = checkSignUp();
            } catch (BusinessException ex) {
                frameController.showError(ex.getMessage());
            }
            if(error != null) {
                frameController.showError(error);
            }
            else {
                frameController.togglePlayer();
                frameController.swapScreen(signUpUI, Globals.MAIN_SCREEN);
            }
        }
    }
}
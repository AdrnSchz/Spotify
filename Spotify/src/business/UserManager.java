package business;

import business.entities.User;
import business.exceptions.BusinessException;
import persistence.UserDAO;
import persistence.db.DBUserDAO;
import persistence.db.Database;
import persistence.exceptions.PersistenceException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Manager of the Users.
 *
 * @author Group 6
 * @version 1.0
 */
public class UserManager {

    /**
     * UserDAO instance to retrieve and manage the user.
     */
    private final UserDAO userDAO;
    /**
     * Current user logged in.
     */
    private String currentUser;

    /**
     * Constructor method for UserManager.
     *
     * @param db Database instance to be used.
     */
    public UserManager(Database db){
        userDAO = new DBUserDAO(db);
    }

    /**
     * Method used to check if the fields entered by the user are correct.
     *
     * @param infoUser String[] with the information entered by the user.
     * @return boolean[] with the errors.
     * @throws BusinessException if there's an error with the database.
     */
    public boolean[] checkInfo(String[] infoUser) throws BusinessException {
        boolean[] errors = new boolean[]{false, false, false, false, false, false};

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(infoUser[1]);
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

        if (infoUser[0].length() == 0 || infoUser[1].length() == 0 || infoUser[2].length() == 0 || infoUser[3].length() == 0) {
            errors[0] = true;
        }

        try {
            if(userDAO.getUser(infoUser[0]) != null) {
                errors[1] = true;
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }

        try {
            if(userDAO.getUser(infoUser[1]) != null) {
                errors[3] = true;
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }

        if(!matcher.find()) {
            errors[2] = true;
        }

        pattern = Pattern.compile(regexPassword);
        matcher = pattern.matcher(infoUser[2]);

        if(!matcher.find()) {
            errors[4] = true;
        }

        if(!infoUser[2].equals(infoUser[3])) {
            errors[5] = true;
        }
        return errors;
    }

    /**
     * Method used to create a user.
     *
     * @param infoUser String[] with the information entered by the user.
     * @throws BusinessException if there's an error with the database.
     */
    public void signUpUser(String[] infoUser) throws BusinessException {
        User user = new User(infoUser[0], infoUser[1], infoUser[2]);
        try {
            userDAO.createUser(user);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Method used to check if the user is actually registered.
     *
     * @param username String with the username entered by the user.
     * @param password String with the password entered by the user.
     * @return boolean indicating if the user is registered or not.
     * @throws BusinessException if there's an error with the database.
     */
    public boolean checkLogin(String username, String password) throws BusinessException {
        User user;
        try {
            user = userDAO.getUser(username);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Method used to delete a user.
     *
     * @param username String with the user to delete.
     * @return boolean indicating if the user has been deleted or not.
     * @throws BusinessException if there's an error with the database.
     */
    public boolean deleteUser(String username) throws BusinessException {
        try {
            return userDAO.deleteUser(username);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Method used to set the current user.
     *
     * @param user username to set.
     */
    public void setCurrentUser(String user){
        currentUser = user;
    }

    /**
     * Method used to get the current user.
     *
     * @return the user.
     */
    public String getCurrentUser(){
        return currentUser;
    }
}
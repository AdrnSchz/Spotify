package business.entities;

/**
 * User entity class.
 *
 * @author Group 6
 * @version 1.0
 */
public class User {
    /**
     * String values representing the user's information.
     */
    private final String username;
    private final String password;
    private final String email;

    /**
     * Constructor method for User.
     *
     * @param username String with the username.
     * @param email String with the email.
     * @param password String with the password.
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Getter for the username.
     *
     * @return String with the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the password.
     *
     * @return String with the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the email.
     *
     * @return String with the email.
     */
    public String getEmail() {
        return email;
    }
}

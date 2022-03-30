/**
 * Used to manage the state of the user in the application.
 * The user can be logged out, or logged into a certain account given
 * the username and password.
 *
 * @author Talha Abdulkuddus (20076327)
 * @version 2021.03.23
 */
public class UserManager {

    private static UserManager instance;
    private User currentUser;

    private UserManager(){}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Tries to find a user in the database according to the provided username and password.
     * @return User object if the login process was successful, null otherwise.
     */
    public User loginUser(String username, String password) {
        DatabaseController database = DatabaseController.getInstance();
        currentUser = database.getUser(username, password);
        return currentUser;
    }

    /**
     * Logs out the user from the application.
     */
    public void logoutUser() {
        currentUser = null;
    }

    /**
     * Used to get the user object of the application.
     * @return User object that is logged in. If there is no user logged in at the moment, return null.
     */
    public User getCurrentUser() {
        return currentUser;
    }
}

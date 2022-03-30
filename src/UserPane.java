import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

/**
 * This class represents a pane, which is used at the top left of the main application window.
 * In essence, it holds two buttons, one that opens up a user profile window and other which logs out the user from the system.
 * This class was created to reduce the amount of code in the main window.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.0
 */
public class UserPane extends TilePane {

    // A user object. If it is null, then it means that there's no user that is logged in at the moment.
    private User user;
    // A label that holds the name of the user. If the label is clicked, the user profile window is opened.
    private Button userProfileButton;
    // Holds a reference to the main window of this application.
    private final MainWindow mainWindow;

    /**
     * Public constructor of the UserPane.
     * @param mainWindow A reference to the main window of the application.
     */
    public UserPane(MainWindow mainWindow) {
        this.getStyleClass().add("user-pane");
        this.mainWindow = mainWindow;
        createLayout();
    }

    /**
     * Used to create the layout of the pane.
     */
    private void createLayout() {
        // Initialise the user label. The label is public in order to later change it.
        userProfileButton = new Button("No user");
        userProfileButton.getStyleClass().add("user-button");
        userProfileButton.setOnAction(this::openUserWindow);

        Button logoutButton = new Button("Log out");
        logoutButton.getStyleClass().add("user-button");
        logoutButton.setOnAction(this::logoutUser);

        this.getChildren().addAll(userProfileButton, logoutButton);
    }

    /**
     * Used to change the text of the user label.
     */
    private void updateUserLabel() {
        if(user == null) {
            userProfileButton.setText(" No user ");
        }
        else {
            userProfileButton.setText(user.getFirstName() + " " + user.getLastName());
        }
    }

    /**
     * Used to open a window which contains all the information about the user.
     * @param event Event on which the window is opened
     */
    public void openUserWindow(ActionEvent event) {
        if(user != null) {
            UserWindow userWindow = new UserWindow();
            userWindow.start(user);
        }
    }

    /**
     * Used to set the User object of this class to a new one.
     * @param user A new user which to set to.
     */
    public void setUser(User user) {
        this.user = user;
        updateUserLabel();
    }

    /**
     * Used to log out user from the application.
     * @param event An event on which the log out happens.
     */
    private void logoutUser(ActionEvent event) {
        user = null;
        updateUserLabel();
        mainWindow.logoutUser();
    }
}
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

/**
 * This class represents a pane, which is used at the top left of the main application window.
 * In essence, it holds two buttons, one that opens up a registration form, and other which opens a Log in window.
 * This class was created to reduce the amount of code in the main window.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.0
 */
public class LoginRegisterPane extends TilePane {

    // Holds a reference to the main window of this application.
    private final MainWindow mainWindow;

    /**
     * Public constructor of the LoginRegisterPane.
     * @param mainWindow A reference to the main window of the application.
     */
    public LoginRegisterPane(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.getStyleClass().add("user-pane");
        createLayout();
    }

    /**
     * Used to create the layout of the pane.
     * The pane holds two buttons (Register and Login) for the user to interact.
     */
    private void createLayout() {
        // Create both buttons
        Button loginButton = new Button("Log in");
        loginButton.getStyleClass().add("user-button");
        loginButton.setOnAction(this::openLoginWindow);

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("user-button");
        registerButton.setOnAction(this::openRegistrationWindow);

        this.getChildren().addAll(registerButton, loginButton);
    }

    /**
     * Used to open the registration window for the user.
     * @param event Event on which the window is opened
     */
    private void openRegistrationWindow(ActionEvent event) {
        RegistrationWindow registrationwindow = new RegistrationWindow();
        registrationwindow.start();
    }

    /**
     * Used to open the login window for the user.
     * @param event Event on which the window is opened.
     */
    private void openLoginWindow(ActionEvent event) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.start(mainWindow);
    }
}
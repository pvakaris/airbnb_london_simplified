import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * LoginWindow is a window, which opens up when a user clicks the button "Log in" in the main window of the application.
 * In this window, a user can log in to an account, that already exists in the user database.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.0
 */
public class LoginWindow {

    // This field holds a username the user has input.
    private TextField usernameField;
    // This field holds a password the user has input.
    private TextField passwordField;
    // This field holds the main window of the whole application. It is passed as a parameter once the LoginWindow is created.
    private MainWindow mainWindow;
    // Used to send alerts to the user.
    private AlertGenerator alertGenerator;

    /**
     * Public constructor of the LoginWindow.
     */
    public LoginWindow() {}

    /**
     * Used to initialise and start the login window.
     * @param mainWindow Main window of the application.
     *                   Passed as a parameter in order to makes changes in it once a user is logged in.
     */
    public void start(MainWindow mainWindow) {
        try {
            Stage stage = new Stage();
            this.mainWindow = mainWindow;
            alertGenerator = AlertGenerator.getInstance();

            // Setup the user details panel
            BorderPane root = new BorderPane();
            setupMainPane(root);

            Scene scene = new Scene(root, 350,300);
            scene.getStylesheets().add("registration-login-stylesheet.css");

            stage.setTitle("Log in to an existing account");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Used to setup the main pane of the window
     * @param mainPane The pane which put all the other components and containers to.
     */
    private void setupMainPane(BorderPane mainPane) {
        // Header section
        Label header = new Label("Log in procedure");
        header.setId("header-label");
        mainPane.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);

        // Center section
        VBox list = setupLoginDetailsPanel();
        mainPane.setCenter(list);
        BorderPane.setAlignment(list, Pos.CENTER);

        // Bottom section
        Button loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setId("finish-login-button");
        loginButton.setOnAction(this::finishLogin);
        mainPane.setBottom(loginButton);
        BorderPane.setAlignment(loginButton, Pos.CENTER);


        // Raise the finish button from the bottom margin by 20px.
        BorderPane.setMargin(loginButton, new Insets(20));
    }

    /**
     * Used when the login button is clicked. Looks for a user according to the provided details.
     * @param event After which this method is called.
     */
    private void finishLogin(ActionEvent event) {
        User user = UserManager.getInstance().loginUser(usernameField.getText(), passwordField.getText());
        if(user != null) {
            mainWindow.loginUser();
            successfulLoginAlert(user);

            // Close the login window.
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
        else {
            failedLoginAlert();
        }
    }

    /**
     * Creates the VBox pane which holds labels and text fields which the user puts his login details to.
     * @return VBox with appropriate components.
     */
    private VBox setupLoginDetailsPanel() {
        VBox list = new VBox();

        setUsernameSection(list);
        setPasswordSection(list);

        return list;
    }

    /**
     * Used to set the username section of the login form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setUsernameSection(Pane parent) {
        usernameField = new TextField();
        parent.getChildren().add(createLabelAndTextFieldPair("Username:", usernameField));
    }

    /**
     * Used to set the password section of the login form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setPasswordSection(Pane parent) {
        passwordField = new PasswordField();
        parent.getChildren().add(createLabelAndTextFieldPair("Password:", passwordField));
    }

    /**
     * Creates tile pane with a Label, TextField pair to display the requirement and the answer in the login form.
     * @param labelText Text displayed on the label.
     * @param field Type of the field.
     * @return TilePane containing a label and a text field.
     */
    private TilePane createLabelAndTextFieldPair(String labelText, TextField field) {
        TilePane pair = new TilePane();

        Label label = new Label(labelText);
        label.setMaxWidth(Double.MAX_VALUE);

        field.setMaxWidth(Double.MAX_VALUE);
        field.setPadding(new Insets(1,1,1,1));

        pair.getStyleClass().add("label-textField-pair");
        pair.getChildren().addAll(label, field);

        return pair;
    }

    /**
     * Send an alert to the user to inform about the successful login.
     * @param user A user object, whose details to use in the announcement.
     */
    private void successfulLoginAlert(User user) {
        alertGenerator.informUser("Welcome back " + user.getFirstName() + " " + user.getLastName() + "!");
    }

    /**
     * Send an alert to the user about a failed login attempt.
     */
    private void failedLoginAlert() {
        alertGenerator.informUser("There is no such user.");
    }
}
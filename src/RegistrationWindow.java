import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * RegistrationWindow is a window, which opens up when a user clicks the button "Register" in the main window of the application.
 * In this window, a user can create an AirBnb London account. If a user has an account, he or she can book rooms throughout London.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.2
 */
public class RegistrationWindow {

    // This field holds the first name of the user who is signing-up.
    private TextField firstNameField;
    // This field holds the last name of the user who is signing-up.
    private TextField lastNameField;
    // This field holds the password of the user who is signing-up.
    private TextField passwordField;
    // This field holds the second input of the user's password.
    private TextField repeatPasswordField;
    // This field holds the phone number of the user who is signing-up. The phone number must be of a valid UK format starting with +44...
    private TextField phoneNumberField;
    // This field holds the email of the user who is signing-up.
    private TextField emailField;
    // This field holds the username of the user who is signing-up.
    private TextField usernameField;

    // Used to check the validity of the data user has entered.
    private ValidityChecker validityChecker;
    // Used to send alerts to the user.
    private AlertGenerator alertGenerator;

    /**
     * Public constructor of the RegistrationWindow.
     */
    public RegistrationWindow() {}

    /**
     * Used to initialise and start the registration window.
     */
    public void start() {
        try {
            Stage stage = new Stage();
            validityChecker = ValidityChecker.getInstance();
            alertGenerator = AlertGenerator.getInstance();

            // Setup the registration details panel
            BorderPane root = new BorderPane();
            setupMainPane(root);

            Scene scene = new Scene(root, 400,500);
            scene.getStylesheets().add("registration-login-stylesheet.css");

            stage.setTitle("Register a new user");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }

    }

    /////////////////            PRIVATE METHODS

    /**
     * Used to setup the main pane of the window.
     * @param mainPane The pane which put all the other components and containers to.
     */
    private void setupMainPane(BorderPane mainPane) {
        // Header
        Label header = new Label("Registration");
        header.setId("header-label");
        mainPane.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);

        // Center
        VBox list = setupRegistrationDetailsPanel();
        mainPane.setCenter(list);
        BorderPane.setAlignment(list, Pos.CENTER);

        // Bottom
        Button finishButton = new Button("Finish");
        finishButton.setDefaultButton(true);
        finishButton.setId("finish-button");
        finishButton.setOnAction(this::finishRegistration);
        mainPane.setBottom(finishButton);
        BorderPane.setAlignment(finishButton, Pos.CENTER);

        // Raise the finish button from the bottom margin by 20px.
        BorderPane.setMargin(finishButton, new Insets(20));
    }

    /**
     * Called once the finish button is clicked. If all the provided details are valid, a new user is inserted in the database
     * and the user of this application is informed about a successful registration.
     * @param event Event after which the data is processed.
     */
    private void finishRegistration(ActionEvent event) {
         if(detailsValid()) {
             // Create a new user and inform the customer about successful registration.
             createNewUser();
             alertGenerator.informUser("The registration was successful. You can now log in.");

             // Close the registration window.
             ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
         }
    }

    /**
     * Creates a new User object and calls the database to insert the user to the database.
     */
    private void createNewUser() {
        DatabaseController dataBase = DatabaseController.getInstance();
        dataBase.addUser(new User(firstNameField.getCharacters().toString(),
                                  lastNameField.getCharacters().toString(),
                                  usernameField.getCharacters().toString(),
                                  phoneNumberField.getCharacters().toString(),
                                  emailField.getCharacters().toString(),
                                  passwordField.getCharacters().toString()));
    }

    /**
     * Checks if there are no mistakes in the user input.
     * @return true no mistakes were found, false otherwise.
     */
    private boolean detailsValid() {
        return mistakeNotFound(validityChecker.nameValid(firstNameField.getCharacters().toString())) &&
                mistakeNotFound(validityChecker.nameValid(lastNameField.getCharacters().toString())) &&
                mistakeNotFound(validityChecker.passwordsMatch(passwordField.getCharacters().toString(), repeatPasswordField.getCharacters().toString())) &&
                mistakeNotFound(validityChecker.passwordValid(passwordField.getCharacters().toString())) &&
                mistakeNotFound(validityChecker.ukPhoneNumberFormatValid(phoneNumberField.getCharacters().toString())) &&
                mistakeNotFound(validityChecker.usernameValid(usernameField.getCharacters().toString())) &&
                mistakeNotFound(validityChecker.emailFormatValid(emailField.getCharacters().toString()));
    }

    /**
     * Used to setup the vertical box, which contains all the requirements and text fields which the user should fill in.
     * @return The the vertical box containing all the requirement, answerField pairs.
     */
    private VBox setupRegistrationDetailsPanel() {
        VBox list = new VBox();

        setFirstNameSection(list);
        setLastNameSection(list);
        setEmailSection(list);
        setPhoneSection(list);
        setUsernameSection(list);
        setPasswordSection(list);
        setPasswordRepeatSection(list);

        return list;
    }

    /**
     * Used to set the first name section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setFirstNameSection(Pane parent) {
        firstNameField = new TextField();
        parent.getChildren().add(createLabelAndTextFieldPair("First name:", firstNameField));
    }

    /**
     * Used to set the last name section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setLastNameSection(Pane parent) {
        lastNameField = new TextField();
        parent.getChildren().add(createLabelAndTextFieldPair("Last name:", lastNameField));
    }

    /**
     * Used to set the email section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setEmailSection(Pane parent) {
        emailField = new TextField();
        parent.getChildren().add(createLabelAndTextFieldPair("Email:", emailField));
    }

    /**
     * Used to set the phone number section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setPhoneSection(Pane parent) {
        phoneNumberField = new TextField();
        parent.getChildren().add(createLabelAndTextFieldPair("Phone number:", phoneNumberField));
    }

    /**
     * Used to set the username section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setUsernameSection(Pane parent) {
        usernameField = new TextField();
        parent.getChildren().add(createLabelAndTextFieldPair("Username:", usernameField));
    }

    /**
     * Used to set the password section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setPasswordSection(Pane parent) {
        passwordField = new PasswordField();
        parent.getChildren().add(createLabelAndTextFieldPair("Password:", passwordField));
    }

    /**
     * Used to set the password repetition section of the registration form.
     * This methods puts a pair (label + textField) to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setPasswordRepeatSection(Pane parent) {
        repeatPasswordField = new PasswordField();
        parent.getChildren().add(createLabelAndTextFieldPair("Repeat password:", repeatPasswordField));
    }

    /**
     * Creates tile pane with a Label, TextField pair to display the requirement and the answer in the registration form.
     * @param labelText Text displayed on the label.
     * @param field Type of the field.
     * @return TilePane containing a label and a text field.
     */
    private TilePane createLabelAndTextFieldPair(String labelText, TextField field) {
        TilePane pair = new TilePane();
        Label label = new Label(labelText);

        label.setMaxWidth(Double.MAX_VALUE);
        field.setMaxWidth(Double.MAX_VALUE);

        pair.getStyleClass().add("label-textField-pair");

        pair.getChildren().addAll(label, field);
        return pair;
    }

    /**
     * Checks if there is a mistake. If the mistake passed as a parameter is null,
     * then there is no mistake.
     * @param mistake A mistake to check.
     * @return true if a mistake is found, false otherwise.
     */
    private boolean mistakeNotFound(MistakeType mistake) {
        if(mistake == null) {
            return true;
        }
        else {
            alertGenerator.informUser(mistake.toString());
            return false;
        }
    }
}
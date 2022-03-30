import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * A window which represents personal details of a user and a list of reservations this user has.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.0
 */
public class UserWindow {

    // Field which holds the user object
    private User user;
    // Label, which contains user's password or it's masked version (***...);
    private Label password;
    // Button which when clicked either hides or shows user's password.
    private Button hideShowPassword;
    // Is password visible at the moment.
    private boolean passwordVisible;
    // ListView which contains all reservations made by the user.
    private ListView<String> reservations;

    /**
     * Public constructor of the UserWindow.
     */
    public UserWindow() {}

    /**
     * Used to initialise and start the user window.
     * @param user A user, whose details will be presented in the window.
     */
    public void start(User user) {
        try {
            Stage stage = new Stage();
            this.user = user;

            // Setup the user details panel
            BorderPane root = new BorderPane();
            setupMainPane(root);

            Scene scene = new Scene(root, 500,600);
            scene.getStylesheets().add("user-window-stylesheet.css");

            stage.setTitle("User details");
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
        // Header section
        Label header = new Label("Information about the user");
        header.setId("header-label");
        mainPane.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);

        // Center section
        VBox list = setupDetailsPanel();
        mainPane.setCenter(list);
        BorderPane.setAlignment(list, Pos.CENTER);

        // Bottom section
        TilePane pane = new TilePane();
        VBox reservationsBox = setupReservationsPanel();
        pane.getChildren().addAll(reservationsBox, new Region());
        mainPane.setBottom(reservationsBox);
        BorderPane.setAlignment(reservationsBox, Pos.CENTER);
    }

    /**
     * Used to setup a panel which will hold all the personal information about the user.
     * But not his reservations.
     * @return A VBox containing personal details.
     */
    private VBox setupDetailsPanel() {
        VBox list = new VBox();

        setFirstName(list);
        setLastName(list);
        setEmail(list);
        setPhoneNumber(list);
        setUsername(list);
        setPassword(list);
        setupHideShowButton(list);

        list.setAlignment(Pos.CENTER);
        return list;
    }

    /**
     * Used to modify the password label when the button show/hide password is clicked.
     * Initially, the password is hidden
     * @param event Event on which the label is modified.
     */
    private void modifyPasswordLabel(ActionEvent event) {
        if(passwordVisible) {
            hidePassword();
        }
        else {
            showPassword();
        }
    }

    /**
     * Used to mask the user's password wit * symbol.
     * Password's length = amount of *.
     */
    private void hidePassword() {
        password.setText("*".repeat(user.getPassword().length()));
        passwordVisible = false;
        hideShowPassword.setText("Show password");
    }

    /**
     * Used to reveal the user's password.
     */
    private void showPassword() {
        password.setText(user.getPassword());
        passwordVisible = true;
        hideShowPassword.setText("Hide password");
    }

    /**
     * Used to setup the panel which shows all user's reservations.
     * @return VBox containing all the relevant data.
     */
    private VBox setupReservationsPanel() {
        VBox reservationsBox = new VBox();
        reservationsBox.getChildren().addAll(new Label("Reservations:"), getUserReservations());
        reservationsBox.setPadding(new Insets(0, 30, 30, 30));
        return reservationsBox;
    }

    /**
     * Retrieves a list of reservations that the user has and puts all of them in to the ListView which
     * will be presented at the bottom of the UserView window. If there are no reservations, the list will contain
     * the text: "Nothing to show".
     * @return reservations ListView with all the reservations.
     */
    private ListView<String> getUserReservations() {
        reservations = new ListView<>();
        ArrayList<AirbnbListing> userReservations = user.getReservations();

        if(!userReservations.isEmpty()) {
            for(AirbnbListing reservation : userReservations) {
                reservations.getItems().add(reservation.toStringDisplayedInUserProfile());
            }
        }
        else {
            reservations.getItems().add("Nothing to show.");
        }

        reservations.setOnMouseClicked(this::viewProperty);

        // Leave a little bit of space until the margin.
        reservations.setPadding(new Insets(3, 3, 3, 3));
        reservations.setMaxHeight(200);
        return reservations;
    }

    /**
     * Used to show further information about a property when an item is
     * clicked on in the ListView.
     * @param event Event on which ListView is clicked.
     */
    private void viewProperty(MouseEvent event) {
        int index = reservations.getSelectionModel().getSelectedIndex();
        AirbnbListing propertyListing = user.getReservations().get(index);
        new PropertyDetailWindow(propertyListing).createWindow();
    }

    /**
     * Used to initialise and set-up the button that is responsible for showing and concealing
     * the user's password.
     */
    private void setupHideShowButton(Pane parent) {
        hideShowPassword = new Button("Show password");
        hideShowPassword.setId("hide-show-button");
        hideShowPassword.setOnAction(this::modifyPasswordLabel);
        hidePassword();
        parent.getChildren().add(hideShowPassword);
    }

    /**
     * Used to set the first name section of the user window.
     * This methods puts a pair to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setFirstName(Pane parent) {
        parent.getChildren().add(createLabelPair("First name:", user.getFirstName()));
    }

    /**
     * Used to set the last name section of the user window.
     * This methods puts a pair to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setLastName(Pane parent) {
        parent.getChildren().add(createLabelPair("Last name:", user.getLastName()));
    }

    /**
     * Used to set the email section of the user window.
     * This methods puts a pair to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setEmail(Pane parent) {
        parent.getChildren().add(createLabelPair("Email:", user.getEmail()));
    }

    /**
     * Used to set the mobile phone number section of the user window.
     * This methods puts a pair to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setPhoneNumber(Pane parent) {
        parent.getChildren().add(createLabelPair("Phone number:", user.getPhoneNumber()));
    }

    /**
     * Used to set the username section of the user window.
     * This methods puts a pair to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setUsername(Pane parent) {
        parent.getChildren().add(createLabelPair("Username:", user.getUsername()));
    }

    /**
     * Used to set the password section of the user window.
     * This methods puts a TilePane that holds two labels to the parent pane.
     * @param parent A parent pane which put the pair to.
     */
    private void setPassword(Pane parent) {
        TilePane pair = new TilePane();

        Label label = new Label("Password:");
        password = new Label();

        label.setMaxWidth(Double.MAX_VALUE);
        password.setMaxWidth(Double.MAX_VALUE);

        password.getStyleClass().add("answer-label");

        pair.getChildren().addAll(label, password);
        pair.getStyleClass().add("details-section");
        parent.getChildren().add(pair);
    }

    /**
     * Creates a tile pane with two labels. One asking for user's details, another one giving them.
     * @param category What kind of personal details will be provided in the answer label.
     * @param answer User's personal details.
     * @return TilePane containing both labels.
     */
    private TilePane createLabelPair(String category, String answer) {
        TilePane pair = new TilePane();

        Label leftLabel = new Label(category);
        Label rightLabel = new Label(answer);

        rightLabel.setMaxWidth(Double.MAX_VALUE);
        leftLabel.setMaxWidth(Double.MAX_VALUE);

        rightLabel.getStyleClass().add("answer-label");
        pair.getChildren().addAll(leftLabel, rightLabel);
        pair.getStyleClass().add("details-section");
        return pair;
    }
}
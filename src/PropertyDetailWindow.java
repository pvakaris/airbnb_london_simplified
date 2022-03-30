import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

/**
 * This is a window that displays information about a specific property.
 *
 * @author Talha Abdulkuddus
 * @version 2021.03.21
 */
public class PropertyDetailWindow {

    // All labels in window
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label hostNameLabel;
    @FXML
    private Label roomTypeLabel;
    @FXML
    private Label minNightsLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label reviewCountLabel;
    @FXML
    private Label lastReviewLabel;
    @FXML
    private Label reviewsPerMonthLabel;

    // Reservation button.
    @FXML
    private Button reserveButton;

    // The property listing to display.
    private final AirbnbListing listing;

    // The current application user (null if not logged in).
    private User user;

    // Constant strings to display to user.
    private static final String HOST_TEXT = "Hosted by: ";
    private static final String ROOM_TEXT = "Room type: ";
    private static final String MIN_NIGHTS_TEXT = "Minimum nights: ";
    private static final String PRICE_TEXT = "Price: £";
    private static final String LOGIN_TEXT = "Log in to reserve property";
    private static final String RESERVED_TEXT = "Reserved";

    /**
     * Create a new PropertyDetailWindow, giving the property to display.
     * @param listing The property listing to display.
     */
    public PropertyDetailWindow(AirbnbListing listing) {
        this.listing = listing;
        user = UserManager.getInstance().getCurrentUser();
    }

    /**
     * Create a new window displaying the property passed into the constructor.
     */
    public void createWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("propertyWindow.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Property in " + listing.getNeighbourhood());
            stage.setScene(new Scene(root, 400, 350));
            stage.show();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * On start up, update labels and buttons to display property info.
     */
    @FXML
    private void initialize() {
        setTextLabels();

        // Disable button if user not logged in.
        if (user == null) {
            reserveButton.setText(LOGIN_TEXT);
            reserveButton.setDisable(true);
        }
        // Also disable button if already reserved.
        else if (user.getReservations().contains(listing)) {
            setButtonAsReserved();
        }
    }

    /**
     * Sets the labels in the window to the property listing details.
     */
    private void setTextLabels() {
        descriptionLabel.setText(listing.getName());
        hostNameLabel.setText(HOST_TEXT + listing.getHost_name());
        roomTypeLabel.setText(ROOM_TEXT + listing.getRoom_type());
        minNightsLabel.setText(MIN_NIGHTS_TEXT + listing.getMinimumNights());
        priceLabel.setText(PRICE_TEXT + listing.getPrice());

        reviewCountLabel.setText(String.valueOf(listing.getNumberOfReviews()));

        // For remaining labels, check if values exist / in valid ranges.
        // If not, set label to "N/A".
        String lastReview = listing.getLastReview();
        if (lastReview.isEmpty()) {
            lastReview = "N/A";
        }
        lastReviewLabel.setText(lastReview);

        String reviewsPerMonthText;
        if (listing.getReviewsPerMonth() < 0) {
            reviewsPerMonthText = "N/A";
        } else {
            reviewsPerMonthText = String.valueOf(listing.getReviewsPerMonth());
        }
        reviewsPerMonthLabel.setText(reviewsPerMonthText);
    }

    private void setButtonAsReserved() {
        reserveButton.setDisable(true);
        reserveButton.setText(RESERVED_TEXT);
    }

    /**
     * Launch the user's browser and send them to a Google Maps page with the
     * property's location marked on it.
     * @param event The event triggering this function.
     */
    @FXML
    private void viewOnMap(ActionEvent event) {
        // Create the URL.
        String url = "https://www.google.com/maps/search/?api=1&query=";
        url += listing.getLatitude() + "," + listing.getLongitude();

        // Check if we are able to launch the browser.
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                // If not, show an error message.
                showBrowserLaunchFailureAlert();
            }
        }
    }

    /**
     * Show an alert to the user notifying them an error occurred when attempting to open URL.
     */
    private void showBrowserLaunchFailureAlert() {
        AlertGenerator.getInstance().sendErrorMessage("An error occurred trying to open the browser.");
    }

    /**
     * Reserve the current property for the user.
     * @param event The event triggering this function.
     */
    @FXML
    private void reserveProperty(ActionEvent event) {
        DatabaseController.getInstance().addReservation(user, listing);
        AlertGenerator.getInstance().informUser("You have reserved this property.");
        setButtonAsReserved();
    }

    /**
     * When the back button is clicked on, close the current window.
     * @param event The triggering event.
     */
    @FXML
    private void windowBack(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}

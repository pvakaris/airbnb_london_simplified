import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a panel that contains a map image of the currently reserved properties.
 *
 * The panel can be created with {@link #getPane()}, and that
 * panel can be updated using {@link #update()}
 *
 * @author Talha Abdulkuddus
 * @version 2021.03.26
 */
public class ReservationsPanel implements Panel {

    // Reference to label and image.
    @FXML
    private Label infoLabel;
    @FXML
    private AnchorPane imageContainer;

    // String constants for building the image URL.
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/staticmap?size=640x400";
    private static final String API_KEY = "key=AIzaSyBPREG302WRUPGOi3NZLT_JO1A_lS5p3Qs";
    private static final String MARKERS_URL_PARAMETER = "markers=";

    // The current user logged in (or null).
    private User user;

    // The list of user reservations made.
    private List<AirbnbListing> userReservations;

    // The inflated fxml pane (prevents fxml from being loaded everytime it's shown).
    private Pane pane;

    // The current URL of the image being displayed.
    private String currentImageUrl;

    /**
     * Create a ReservationsPanel.
     */
    public ReservationsPanel() {
        userReservations = new ArrayList<>();
    }

    /**
     * Return the Pane that contains all reservation details.
     *
     * @return The pane with the map pane.
     */
    public Pane getPane() throws Exception {
        if (pane == null) {
            pane = new StackPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationsPanel.fxml"));
            loader.setController(this);
            Parent map = loader.load();
            pane.getChildren().add(map);
        }
        update();
        return pane;
    }

    /**
     * On panel creation, update the panel with the latest information.
     */
    @FXML
    private void initialize() {
        update();
    }


    /**
     * Update the panel using data from the current user.
     */
    @Override
    public void update() {
        if (pane != null) {
            updatePropertyList();
            setMapImage();
        }
        updateLabel();
    }

    /**
     * Update the property list with the user's reservations. This is done each time the panel is shown
     * in case the active user changes.
     */
    private void updatePropertyList() {
        user = UserManager.getInstance().getCurrentUser();

        if (user != null) {

            // Convert the ids to property listings.
            userReservations = user.getReservations();
        } else {
            userReservations = null;
        }
    }

    /**
     * Update the list view and label with the latest data.
     */
    private void updateLabel() {
        if (user == null) {
            infoLabel.setText("Log in to view your reserved property map");
        } else if (userReservations.isEmpty()) {
            infoLabel.setText("Reserve properties to see your map here");
        } else {
            infoLabel.setText("You have reserved " + userReservations.size() + " properties");
        }
    }

    /**
     * Create and fetch the image used to show all reserved properties.
     */
    private void setMapImage() {

        Image mapImage;

        // Only create url if user is logged in and has reserved properties.
        if (userReservations != null && !userReservations.isEmpty() && user != null) {

            String finalUrl = generateImageUrl();

            // If image same as existing image, don't load the image again.
            if (finalUrl.equals(currentImageUrl)) {
                return;
            }

            mapImage = new Image(finalUrl, true);
            currentImageUrl = finalUrl;

        } else {
            mapImage = new Image("no_reservation.png", true);
            currentImageUrl = null;
        }

        // Set layout parameters for the image.
        BackgroundSize bgSize = new BackgroundSize(
                BackgroundSize.AUTO,
                BackgroundSize.AUTO,
                false, false, true, false
        );

        // Set the image onto the container.
        Background bg = new Background(new BackgroundImage(mapImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bgSize));
        imageContainer.setBackground(bg);
    }

    /**
     * Generate the appropriate url for the current active user, using the Google Maps Static API.
     * @return A String containing a URL to a Google Maps image.
     */
    private String generateImageUrl() {
        // Convert reservations to the specific string format as specified by the Google Maps API.
        StringBuilder markers = new StringBuilder(MARKERS_URL_PARAMETER);

        for (AirbnbListing reservation : userReservations) {
            markers.append(reservation.getLatitude())
                    .append(",")
                    .append(reservation.getLongitude())
                    .append("%7C");
        }

        // Build and return the image url.
        return BASE_URL + "&" + markers + "&" + API_KEY;
    }

}

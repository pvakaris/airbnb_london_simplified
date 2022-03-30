import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a panel that contains the map of boroughs.
 * Each borough can be clicked on, which opens a new
 * window displaying a list of properties in that borough.
 *
 * The panel can be created with {@link #getPane()}, and that
 * panel can be updated using {@link #update()}
 *
 * @author Talha Abdulkuddus
 * @version 2021.03.16
 */
public class MapPanel implements Panel {

    // Label displaying total number of properties in an area.
    @FXML
    private Label propertyCountLabel;

    // GridPane holding all the buttons.
    @FXML
    private GridPane mapButtonGrid;

    // String to display in label.
    private static final String PROPERTIES_COUNT_STRING = " properties available in ";

    // The total number of properties available.
    private int propertyCount;

    // HashMap linking borough enums with number of properties in given borough.
    private Map<Borough, Integer> boroughCount;

    // Model holding all property data.
    private AirbnbProperties propertyData;

    // The inflated fxml pane (prevents fxml from being loaded everytime it's shown).
    private Pane pane;

    /**
     * Create a MapPanel that has the ability to create a Pane containing the map view.
     * @param propertyData The underlying data set of properties.
     */
    public MapPanel(AirbnbProperties propertyData) {
        this.propertyData = propertyData;
    }

    /**
     * Return the Pane that contains the map of all boroughs.
     *
     * @return The pane with the map pane.
     */
    public Pane getPane() throws Exception {
        if (pane == null) {
            pane = new StackPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boroughMap.fxml"));
            loader.setController(this);
            Parent map = loader.load();
            pane.getChildren().add(map);
        }
        return pane;
    }

    /**
     * Update the panel after underlying data has been changed.
     */
    public void update() {
        setupMapButtons();
    }

    /**
     * When the mouse hovers over a button, update the label with the
     * available properties for that area.
     *
     * @param event The mouse event received.
     */
    @FXML
    private void buttonMouseEnter(MouseEvent event) {
        Button button = (Button) event.getSource();
        Borough location = Borough.valueOf(button.getText());
        propertyCountLabel.setText(boroughCount.get(location) + PROPERTIES_COUNT_STRING + location);
    }

    /**
     * When the mouse leaves a button, update the label with the
     * available properties for all areas (i.e. London).
     *
     * @param event The mouse event received.
     */
    @FXML
    private void buttonMouseExit(MouseEvent event) {
        propertyCountLabel.setText(propertyCount + PROPERTIES_COUNT_STRING + "London");
    }

    /**
     * When a button is clicked, open a new window with the list
     * of properties in that borough.
     *
     * @param event The event received.
     */
    @FXML
    private void viewBorough(ActionEvent event) {
        Button button = (Button) event.getSource();
        Borough location = Borough.valueOf(button.getText());

        // If no properties available, show a dialog instead.
        if (boroughCount.get(location) == 0) {
            showNoPropertiesDialog(location);
        } else {
            launchPropertyList(location);
        }
    }

    /**
     * Show a dialog to the user informing them that the selected borough has no properties.
     * @param location The borough to name.
     */
    private void showNoPropertiesDialog(Borough location) {
        String message = "There are no properties available in " + location + " for this price range.";
        AlertGenerator.getInstance().informUser(message);
    }

    /**
     * Launch a window showing the selected borough's properties in a list.
     * @param borough The borough to display properties from.
     */
    private void launchPropertyList(Borough borough) {
        List<AirbnbListing> properties = propertyData.getBoroughPropertyList(borough);
        new PropertyListWindow(borough, properties).createWindow();
    }

    // Pane setup below

    /**
     * On panel creation, setup the buttons to display the property data
     * and colour them - the greenest being the area with most properties.
     */
    @FXML
    private void initialize() {
        boroughCount = new HashMap<>();

        setupMapButtons();
    }

    /**
     * Set up the buttons to hold the right count and colour.
     */
    private void setupMapButtons() {
        propertyCount = 0;
        int minCount = Integer.MAX_VALUE;
        int maxCount = 0;

        // For each borough, get the number of available properties and store it.
        // Also record the minimum and maximum number of properties available.
        for (Borough borough : Borough.values()) {
            int count = propertyData.getBoroughPropertyList(borough).size();
            propertyCount += count;
            boroughCount.put(borough, count);

            minCount = Math.min(minCount, count);
            maxCount = Math.max(maxCount, count);
        }

        propertyCountLabel.setText(propertyCount + " properties available in London");
        generateColors(minCount, maxCount);
    }

    /**
     * Generates the color of the borough buttons, based on the availability
     * of properties in that borough.
     *
     * @param minVal The minimum number of properties in a borough.
     * @param maxVal The maximum number of properties in a borough.
     */
    private void generateColors(double minVal, double maxVal) {

        for (Button button : getButtonList()) {

            // Get borough associated with button, and the number of properties in it.
            Borough borough = Borough.valueOf(button.getText());
            int propertyCount = boroughCount.get(borough);

            double saturation = 0;

            // Calculate the saturation level this borough should have, based on the number
            // of properties in it compared to other boroughs.
            if (propertyCount != 0) {
                saturation = (propertyCount - minVal) / (maxVal - minVal);
            }

            // Create and set the background of the button.
            Color bgColor = Color.hsb(132, saturation, 0.85);
            Background bg = new Background(new BackgroundFill(bgColor, null, null));

            button.setBackground(bg);
        }
    }

    /**
     * Returns the list of buttons representing boroughs in the map.
     * @return List of buttons in map.
     */
    private List<Button> getButtonList() {
        List<Node> nodes = mapButtonGrid.getChildren();

        return nodes.stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .collect(Collectors.toList());
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is a window that displays a list of properties for
 * a specific borough. Basic information such as price, reviews and
 * minimum stay are shown.
 *
 * @author Talha Abdulkuddus
 * @version 2021.03.17
 */
public class PropertyListWindow {

    // Reference to drop down choice box for sorting.
    @FXML
    private ComboBox<String> sortComboBox;

    // Reference to list view that displays all properties.
    @FXML
    private ListView<String> propertyListView;

    // Reference to button that changes sort order.
    @FXML
    private Button sortOrderButton;

    // Constants for sort combo box.
    private static final String SORT_NAME = "Host name";
    private static final String SORT_REVIEW = "Reviews";
    private static final String SORT_PRICE = "Price";

    // Current sorting type and order.
    private String currentSort = SORT_PRICE;
    private boolean sortAscending = true;

    private Borough borough;
    private final List<AirbnbListing> propertyList;

    /**
     * Create a new PropertyListWindow, giving the borough and a list of it's properties.
     * @param borough The borough the properties are from.
     * @param propertyList The list of available properties.
     */
    public PropertyListWindow(Borough borough, List<AirbnbListing> propertyList) {
        this.borough = borough;
        this.propertyList = propertyList;
    }

    /**
     * Create a new window displaying the property list passed into the constructor.
     */
    public void createWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("propertyList.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("London Borough of " + borough);
            stage.setScene(new Scene(root, 400, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On window creation, setup the combo box and show the data in the list.
     */
    @FXML
    private void initialize() {
        sortComboBox.getItems().addAll(SORT_PRICE, SORT_REVIEW, SORT_NAME);
        sortComboBox.setOnAction(this::changeSort);
        sortComboBox.getSelectionModel().select(currentSort);

        updateSorting();
    }

    /**
     * Update the list view to show the latest data (when sorting changes).
     */
    private void updateListView(List<AirbnbListing> sortedList) {
        propertyListView.getItems().clear();
        sortedList.forEach(this::addPropertyToListView);
    }

    /**
     * Convert the listing to a String format and place it in the list view.
     * @param property The property to list.
     */
    private void addPropertyToListView(AirbnbListing property) {
        String propertyString = "Host: " + property.getHost_name() +
                "\nPrice: £" + property.getPrice() +
                "\nNumber of reviews: " + property.getNumberOfReviews() +
                "\nMinimum stay: " + property.getMinimumNights() + " nights";
        propertyListView.getItems().add(propertyString);
    }

    /**
     * Update the current sorting type.
     * @param event The event triggering the function.
     */
    @FXML
    private void changeSort(ActionEvent event) {
        currentSort = sortComboBox.getSelectionModel().getSelectedItem();
        updateSorting();
    }

    /**
     * Update the current sorting order.
     * @param event The event triggering the function.
     */
    @FXML
    private void changeSortOrder(ActionEvent event) {
        // Toggle the sort.
        sortAscending = !sortAscending;

        // Update the button to show current sort.
        if (sortAscending) {
            sortOrderButton.setText("↑");
        } else {
            sortOrderButton.setText("↓");
        }

        updateSorting();
    }

    private void updateSorting() {

        // If list is descending, reverse the list.
        // This solves any inconsistencies trying to sort a reversed list from a non-reversed list.
        if (!sortAscending) {
            Collections.reverse(propertyList);
        }

        // Sort according to the selected sort type.
        switch (currentSort) {
            case SORT_NAME:
                propertyList.sort(Comparator.comparing(AirbnbListing::getHost_name));
                break;
            case SORT_PRICE:
                propertyList.sort(Comparator.comparingInt(AirbnbListing::getPrice));
                break;
            case SORT_REVIEW:
                propertyList.sort(Comparator.comparingInt(AirbnbListing::getNumberOfReviews));
                break;
        }

        // If the sort is descending, reverse the list.
        if (!sortAscending) {
            Collections.reverse(propertyList);
        }

        updateListView(propertyList);
    }

    /**
     * When a property is clicked on, show more details of that property.
     * @param event The triggering event.
     */
    @FXML
    private void viewSelectedProperty(MouseEvent event) {
        int index = propertyListView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && index < propertyList.size()) {
            new PropertyDetailWindow(propertyList.get(index)).createWindow();
        }
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

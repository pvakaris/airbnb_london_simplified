import javafx.scene.control.Alert;

/**
 * Used to generate alerts that will be shown to the user.
 *
 * Instead of having the same method in three classes, I've decided to initiate a single static
 * class which will do the work.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.2
 */
public class AlertGenerator {

    // SINGLETON PATTERN - only one alert generator is needed.
    private static AlertGenerator instance;

    /**
     * Private constructor of AlertGenerator class.
     */
    private AlertGenerator() {}

    /**
     * Used to get the static AlertGenerator object.
     * If it does not exist yet, a new static object of this class is initialised and returned.
     * @return AlertGenerator.
     */
    public static AlertGenerator getInstance() {
        if(instance == null) {
            instance = new AlertGenerator();
        }
        return instance;
    }

    /**
     * Used to send an INFORMATION alert to the user to inform about something.
     * @param message A message which to show.
     */
    public void informUser(String message) {
        alertUser(message, Alert.AlertType.INFORMATION);
    }

    /**
     * Used to display an ERROR message to the user.
     * @param message A message which to show
     */
    public void sendErrorMessage(String message) {
        alertUser(message, Alert.AlertType.ERROR);
    }

    /////////////////            PRIVATE METHODS

    /**
     * Used to send an alert to the user to inform about something.
     * @param message A message which to show.
     * @param alertType The type of alert to show.
     */
    private void alertUser(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(message);
        alert.show();
    }
}

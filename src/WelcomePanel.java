import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * WelcomePanel class. This panel is displayed at the start of 
 * the application. It gives the user some instructions on how
 * to use the application.
 *
 * @author Sowrab Chowdhury (k20028600)
 * @version 2021.03.23
 */
public class WelcomePanel implements Panel
{
    
    //The labels in the panel
    private Label title;
    private Label description;
    private Label feedback;

    //Used for the feedback label
    private Integer fromPrice;
    private Integer toPrice;

    /**
     * Returns the pane created in this class.
     * @return The pane of the welcome panel.
     */
    public Pane getPane() throws Exception{

        title = new Label("AirBnB properties");
        title.getStyleClass().add("label-title");

        description = new Label(getDescription());
        description.getStyleClass().add("label-description");
        
        BorderPane topPane = new BorderPane(title, null, null, null, null);
        BorderPane bottomPane = new BorderPane(feedback, null, null, null, null);
        
        update();

        BorderPane pane = new BorderPane(description, topPane, null, bottomPane, null);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(300, 300);


        return pane;

    }

    /**
     * The instructions given to the user.
     * @return The description in the middle of the string.
     */
    private String getDescription(){
        return "-Select the price range, then press the buttons to go over panels."
        .concat("\n-First, you need to register an account with us.")
        .concat("\n-To reserve a property, go to the map, and select a property.");
    }

    /**
     * Updates the feedback label of the panel. 
     */
    public void update() {
        if (!(fromPrice == null || toPrice == null)){
            feedback = new Label("From " + fromPrice + " To " + toPrice);
            feedback.getStyleClass().add("label-feedback");
        }
    }

    /**
     * Sets the minimum in the feedback label.
     * @param num The number to set for the feedback's minimum
     */
    public void setFromPrice(int num){
        fromPrice = num;
    }

    /**
     * Sets the maximum in the feedback label.
     * @param num The number to set for the feedback' maximum
     */
    public void setToPrice(int num){
        toPrice = num;
    }
}
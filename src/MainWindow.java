import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.control.ChoiceBox;

/**
 * MainWindow class. This class starts the main application, allowing the
 * user to change between preferences and panels through the controls setup in the 
 * following methods.
 *
 * @author Sowrab Chowdhury (Student number: K20028600) and Vakaris Paulavičius (Student number: K20062023)
 * @version 2021.03.23
 */
public class MainWindow extends Application
{
    Stage primaryStage;

    //Dropdown menus to choose the price, given at the top of screen.
    private ChoiceBox<Integer> fromBox;
    private ChoiceBox<Integer> toBox;

    //Values of boxes that are saved.
    private Integer fromBoxSaved;
    private Integer toBoxSaved;

    //The control buttons, to switch between panes.
    private Button rightButton;
    private Button leftButton;
    
    //default pane of the main application.
    private BorderPane mainPane;

    //top pane to store login system and dropdown.
    private BorderPane topPane;

    //fromBox box and ToBox will be placed here.
    private Pane dropDown;

    //To manage and create panels for the centre of the window.
    private PanelManager panelManager;

    // A pane which holds two buttons: Log out and a button that opens up the user profile window.
    private UserPane userPane;
    // A pane which holds two buttons: Log in and Register.
    private LoginRegisterPane loginRegisterPane;

    // Manages the state of user authentication.
    private UserManager userManager;

    //The starting panel of the application.
    private WelcomePanel welcomePanel;

    //The pane at the centre, containing the panel.
    private Pane panelPane;

    /**
     * Constructor of the Main Application, 
     * initiates the classes it depends upon.
     */
    public MainWindow() throws Exception{
        panelManager = new PanelManager();
        
        welcomePanel = new WelcomePanel();
        panelManager.addWelcomePanel(welcomePanel);
        userManager = UserManager.getInstance();
    }

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     * 
     * Sets up the main borderPane, along with its sub-panes around 
     * the centre.
     *
     * @param  stage the primary stage for this application.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        primaryStage = stage;
        Pane bottomPane = makeBottomPane();

        topPane = makeTopPane();

        mainPane = new BorderPane();
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.getStyleClass().add("pane");
        
        mainPane.setTop(topPane);
        mainPane.setBottom(bottomPane);
        resetCenterPane();
        
        Scene scene = new Scene(mainPane, 1000, 650);
        scene.getStylesheets().add("MainWindowStyling.css");
        stage.setTitle("Airbnb Window");
        stage.setScene(scene);

        stage.show();
    }

    /** 
     * Creates the bottom pane of the main window, where the button controls
     * are located.
     * @return The bottom pane with button contorls.
     */
    private Pane makeBottomPane()
    {
        rightButton = new Button(">");
        rightButton.setOnAction(this::pressLeft);
        
        leftButton = new Button("<");
        leftButton.setOnAction(this::pressRight);
        
        rightButton.getStyleClass().add("button-ctrl");
        leftButton.getStyleClass().add("button-ctrl");
        
        return new BorderPane(null, null, rightButton, null, leftButton);
    }

    /** 
     * Creates the top pane of the main window, where the drop-down controls
     * are located.
     * @return The top pane with drop-down contorls.
     */
    private BorderPane makeTopPane()
    {
        fromBox = new ChoiceBox<>();
        fromBox.setOnAction(e -> changeFromValue());

        toBox = new ChoiceBox<>();
        toBox.setOnAction(e -> changeToValue());
        createRange();
    
        
        dropDown = new HBox(5.0,new Label("From:"), fromBox,new Label("To:"), toBox);
        dropDown.setPadding(new Insets(10,10,10,10));

        // Initialisation of user panes. This window is passed as a parameter.
        loginRegisterPane = new LoginRegisterPane(this);
        userPane = new UserPane(this);

        BorderPane pane =  new BorderPane(null, null, dropDown, null, loginRegisterPane);
        pane.getStyleClass().add("pane");

        return pane;

    }

    /**
     * Takes content of fromBox to change range (the minimum price) 
     * of the dataset.
     */
    private void changeFromValue(){
        Integer val = fromBox.getValue();
        if (val != null){
            welcomePanel.setFromPrice(val);
            if(validRange()){;
                panelManager.setFromValue(val);
                resetCenterPane();
            }
        }
    }

    /**
     * Takes content of toBox to change range (the maximum price) 
     * of the dataset.
     */
    private void changeToValue(){
        Integer val = toBox.getValue();
        if (val != null){
            welcomePanel.setToPrice(val);
            if(validRange()){
                panelManager.setToValue(val);
                resetCenterPane();
            }
        }
    }
    
    /** 
     * Move to the previous, or circle to the last panel
     * when clicked.
     * @param event The event received.
     */
    private void pressLeft(ActionEvent event)
    {
        panelManager.goPreviousPanel();
        resetCenterPane();
        resize();
        paneFadeIn();
    }
    
    /** 
     * Move to the next, or circle to the first panel
     * when clicked.
     * @param event The event received.
     */
    private void pressRight(ActionEvent event)
    {
        panelManager.goNextPanel();
        resetCenterPane();
        resize();
        paneFadeIn();
    }

    /**
     * Update the centre of the borderpane, 
     * to show current panel.
     */
    private void resetCenterPane(){
        panelPane = panelManager.getCurrentPanel();
        mainPane.setCenter(panelPane);
    }
    
    /**
     * Creates the intervals of prices for 
     * the fromBox and toBox dropdowns.
     * 
     */
    private void createRange()
    {
        for (int i = 0; i < 1001; i += 50)
        {
            fromBox.getItems().add(i);
            toBox.getItems().add(i);
        }
    }

    /**
     * Checks whether the price range (from *minimun* to *maximum*) is 
     * valid or not.
     * @return True if range is valid, False otherwise.
     */
    private boolean validRange(){

        if (fromBox.getValue() == null || toBox.getValue() == null){
            return false;
        }

        Integer fromPrice = fromBox.getValue();
        Integer toPrice = toBox.getValue();

        if (fromPrice > toPrice){
            alertUser("Minimum is greater than maximum, change again");
            loadRange();
            return false;
        }

        saveRange();

        panelManager.addOtherPanels();
        return true;
    }

    /**
     * Saves the range, in case user enters wrong range.
     */
    private void saveRange()
    {
        fromBoxSaved = fromBox.getValue();
        toBoxSaved = toBox.getValue();
    }

    /**
     * Loads the range, in case the user enters wrong range.
     */
    private void loadRange()
    {
        if (fromBox.getValue() != null){
            fromBox.setValue(fromBoxSaved);
        }
        if (toBox.getValue() != null){
            toBox.setValue(toBoxSaved);

        }
    }

    /**
     * Produces a window, notifying the user to 
     * change again the range again.
     * @param reason The reason for this alert.
     */
    private void alertUser(String reason)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Range notice");
        alert.setHeaderText(null);
        alert.setContentText(reason);
        alert.showAndWait();
    }

    /**
     * Makes Fade-In effect when panel appears on screen.
     */
    private void paneFadeIn(){
        FadeTransition effect = new FadeTransition(Duration.millis(750), panelPane);
        effect.setFromValue(0.0);
        effect.setToValue(1.0);

        effect.play();
    }

    // V.P.

    /**
     * Used to change a user pane (at the top left corner) to a new one.
     * @param pane A pane to change to.
     */
    public void changeUserPane(Pane pane) {
        topPane.setLeft(pane);
        resize();
    }

    /**
     * Used to change the pane to show user details at the top left corner.
     */
    public void loginUser() {
        userPane.setUser(userManager.getCurrentUser());
        changeUserPane(userPane);
        panelManager.updatePanel();
    }

    /**
     * Used to change the pane and show a logged out state at the top left corner.
     */
    public void logoutUser() {
        userManager.logoutUser();
        changeUserPane(loginRegisterPane);
        panelManager.updatePanel();
    }

    /**
     * Used to adjust the size of the stage according to the changes.
     */
    private void resize() {
        primaryStage.sizeToScene();
    }

    /**
     * Main method to run the application from the terminal.
     * @param args The arguments given from the terminal (not necessary).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
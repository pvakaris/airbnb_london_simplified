import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    // Constants for window launch size
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private AirbnbProperties propertyData;

    @Override
    public void start(Stage primaryStage) throws Exception {

        propertyData = new AirbnbProperties();
        propertyData.setFromPrice(0);
        propertyData.setToPrice(100);

        primaryStage.setTitle("London Properties");
        MapPanel map = new MapPanel(propertyData);
        Pane root = new BorderPane(map.getPane());

        primaryStage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        primaryStage.show();
    }
}

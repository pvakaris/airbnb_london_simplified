import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

/**
 * A panel showing 4 statistic boxes. Each box has "next" and "previous" buttons.
 * No statistic is ever shown twice at the same time.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public class StatisticsPanel implements Panel {

    private StatisticsModel model;

    @FXML
    private Label statistic1;
    @FXML
    private Label statisticValue1;

    @FXML
    private Label statistic2;
    @FXML
    private Label statisticValue2;

    @FXML
    private Label statistic3;
    @FXML
    private Label statisticValue3;

    @FXML
    private Label statistic4;
    @FXML
    private Label statisticValue4;

    public StatisticsPanel(AirbnbProperties propertyData) {
        model = new StatisticsModel(propertyData);

        initialiseLabels();

        update();
    }

    /**
     * Return the Pane that contains the statistics panel.
     * @return The pane with the statistics panel.
     */
    public Pane getPane() throws Exception {
        StackPane pane = new StackPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statisticsPanel.fxml"));
        loader.setController(this);
        Parent stats = loader.load();
        pane.getChildren().add(stats);
        update();
        return pane;
    }

    /**
     * Update the panel after underlying data has been changed.
     */
    public void update() {
        model.updateStatistics();
        redisplayStatistic1();
        redisplayStatistic2();
        redisplayStatistic3();
        redisplayStatistic4();
    }

    private void initialiseLabels() {
        statistic1 = new Label();
        statistic2 = new Label();
        statistic3 = new Label();
        statistic4 = new Label();

        statisticValue1 = new Label();
        statisticValue2 = new Label();
        statisticValue3 = new Label();
        statisticValue4 = new Label();
    }

    private void redisplayStatistic1() {
        statistic1.setText(model.getStatisticName(0));
        statisticValue1.setText(model.getStatisticValue(0));
    }

    private void redisplayStatistic2() {
        statistic2.setText(model.getStatisticName(1));
        statisticValue2.setText(model.getStatisticValue(1));
    }

    private void redisplayStatistic3() {
        statistic3.setText(model.getStatisticName(2));
        statisticValue3.setText(model.getStatisticValue(2));
    }

    private void redisplayStatistic4() {
        statistic4.setText(model.getStatisticName(3));
        statisticValue4.setText(model.getStatisticValue(3));
    }

    @FXML
    private void next1(ActionEvent event) {
        model.nextStatistic(0);
        redisplayStatistic1();
    }

    @FXML
    private void previous1(ActionEvent event) {
        model.previousStatistic(0);
        redisplayStatistic1();
    }

    @FXML
    private void next2(ActionEvent event) {
        model.nextStatistic(1);
        redisplayStatistic2();
    }

    @FXML
    private void previous2(ActionEvent event) {
        model.previousStatistic(1);
        redisplayStatistic2();
    }

    @FXML
    private void next3(ActionEvent event) {
        model.nextStatistic(2);
        redisplayStatistic3();
    }

    @FXML
    private void previous3(ActionEvent event) {
        model.previousStatistic(2);
        redisplayStatistic3();
    }

    @FXML
    private void next4(ActionEvent event) {
        model.nextStatistic(3);
        redisplayStatistic4();
    }

    @FXML
    private void previous4(ActionEvent event) {
        model.previousStatistic(3);
        redisplayStatistic4();
    }
}

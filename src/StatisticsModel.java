import java.util.Arrays;
import java.util.LinkedList;
import java.util.Deque;

/**
 * Models the core functionality of the statistics panel.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.26
 */
public class StatisticsModel {

    // Statistics not currently displayed
    private Deque<Statistic> queuedStatistics;
    // Statistics currently being displayed
    private Statistic[] displayedStatistics;

    public StatisticsModel(Statistic... statistics) {
        queuedStatistics = new LinkedList<>();
        displayedStatistics = new Statistic[4];

        queuedStatistics.addAll(Arrays.asList(statistics));

        for(int i = 0; i < 4; i++) {
            displayedStatistics[i] = queuedStatistics.remove();
        }
    }

    public StatisticsModel(AirbnbProperties properties) {
        this(new AverageReviewsStatistic(properties),
                new NumberOfPropertiesStatistic(properties),
                new HomesAndApartmentsStatistic(properties),
                new MostExpensiveBoroughStatistic(properties),
                new NumberOfReservationsStatistic(),
                new AverageReservedPropertyPrice(),
                new MostReservedBoroughStatistic(),
                new AverageReservationsPerUserStatistic()
            );
    }

    public void updateStatistics() {
        for(Statistic statistic : queuedStatistics) {
            statistic.updateValue();
        }
        for(int i = 0; i < 4; i++) {
            displayedStatistics[i].updateValue();
        }
    }

    public void nextStatistic(int index) {
        if(index >= 0 && index < 4 && !queuedStatistics.isEmpty()) {
            Statistic currentStatistic = displayedStatistics[index];
            Statistic nextStatistic = queuedStatistics.removeFirst();
            queuedStatistics.addLast(currentStatistic);
            displayedStatistics[index] = nextStatistic;
        }
    }

    public void previousStatistic(int index) {
        if(index >= 0 && index < 4 && !queuedStatistics.isEmpty()) {
            Statistic currentStatistic = displayedStatistics[index];
            Statistic previousStatistic = queuedStatistics.removeLast();
            queuedStatistics.addFirst(currentStatistic);
            displayedStatistics[index] = previousStatistic;
        }
    }

    public String getStatisticName(int index) {
        if(index < 0 || index > 3) {
            return "Invalid index";
        }

        Statistic statistic = displayedStatistics[index];

        if(statistic == null) {
            return "No statistic";
        }

        return statistic.getName();
    }

    public String getStatisticValue(int index) {
        if(index < 0 || index > 3) {
            return "-";
        }

        Statistic statistic = displayedStatistics[index];

        if(statistic == null) {
            return "-";
        }

        return statistic.getValue();
    }
}

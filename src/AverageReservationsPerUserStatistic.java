import java.util.List;

/**
 * Statistic referring to the average number of reservations made by each user.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.26
 */
public class AverageReservationsPerUserStatistic extends AllUsersStatistic {

    public AverageReservationsPerUserStatistic() {
        super("Average Reservations per User");
    }

    @Override
    public void updateValue() {
        List<User> users = super.getAllUsers();
        int totalReservations = 0;
        for(User user : users) {
            if(user != null) {
                List<AirbnbListing> reservations = user.getReservations();
                totalReservations += reservations.size();
            }
        }

        int numberOfUsers = users.size();
        if(numberOfUsers > 0) {
            double averageReservations = (double) totalReservations / numberOfUsers;
            double roundedAverage = (double) ((int) (averageReservations * 10)) / 10;

            super.setValue("~ " + roundedAverage);
        }
        else {
            super.setValue("-");
        }
    }
}

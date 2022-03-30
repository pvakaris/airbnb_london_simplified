import java.util.ArrayList;
import java.util.List;

/**
 * Represents a "statistic" referring to all users stored in the database.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.26
 */
public abstract class AllUsersStatistic extends Statistic {

    public AllUsersStatistic(String name) {
        super(name);
    }

    protected List<User> getAllUsers() {
        DatabaseController databaseController = DatabaseController.getInstance();
        List<User> users = databaseController.getAllUsers();
        return databaseController.getAllUsers();
    }

    @Override
    protected List<AirbnbListing> getPropertyList() {
        List<User> users = getAllUsers();
        List<AirbnbListing> allReservations = new ArrayList<>();
        for(User user : users) {
            if(user != null) {
                allReservations.addAll(user.getReservations());
            }
        }
        return allReservations;
    }
}

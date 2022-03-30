import java.util.List;

/**
 * Represents a "statistic" referring to the currently logged in user.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public abstract class UserStatistic extends Statistic {

    public UserStatistic (String name) {
        super(name);
    }

    @Override
    protected List<AirbnbListing> getPropertyList() {
        User user = UserManager.getInstance().getCurrentUser();
        if(user != null) {
            return user.getReservations();
        }
        else {
            return null;
        }
    }
}

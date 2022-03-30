import java.util.List;

/**
 * Statistic referring to how many reservations the current user has.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public class NumberOfReservationsStatistic extends UserStatistic {

    public NumberOfReservationsStatistic() {
        super("Number of Reservations");
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> reservations = super.getPropertyList();
        if(reservations != null) {
            super.setValue(reservations.size() + "");
        }
        else {
            super.setValue("-");
        }
    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Statistic referring to the most reserved Borough among all users.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.26
 */
public class MostReservedBoroughStatistic extends AllUsersStatistic {

    public MostReservedBoroughStatistic() {
        super("Most Reserved Borough");
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> allReservations = super.getPropertyList();
        Map<Borough, Integer> boroughReservations = new HashMap<>();

        for(AirbnbListing reservation : allReservations) {
            if(reservation != null) {
                Borough borough = reservation.getNeighbourhood();
                if(boroughReservations.containsKey(borough)) {
                    boroughReservations.put(borough, boroughReservations.get(borough) + 1);
                }
                else {
                    boroughReservations.put(borough, 1);
                }
            }
        }

        Set<Borough> boroughSet = boroughReservations.keySet();
        Borough mostReservedBorough = null;
        int maxReservations = 0;
        for(Borough borough : boroughSet) {
            int reservations = boroughReservations.get(borough);
            if(reservations > maxReservations) {
                mostReservedBorough = borough;
                maxReservations = reservations;
            }
        }

        if(mostReservedBorough != null) {
            super.setValue(mostReservedBorough.toString());
        }
        else {
            super.setValue("-");
        }
    }
}

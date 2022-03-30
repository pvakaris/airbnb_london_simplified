import java.util.List;

/**
 * Statistic referring to the average price of the current user's reserved properties.
 * Does not take the minimum number of nights into account.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.26
 */
public class AverageReservedPropertyPrice extends UserStatistic {

    public AverageReservedPropertyPrice() {
        super("Average Price of Reserved Properties");
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> reservations = super.getPropertyList();
        if(reservations != null && reservations.size() > 0) {
            int totalPrice = 0;
            for(AirbnbListing listing : reservations) {
                if(listing != null) {
                    totalPrice += listing.getPrice();
                }
            }
            double averagePrice = (double) totalPrice / reservations.size();
            double roundedAverage = (double) ((int) (averagePrice * 10)) / 10;
            super.setValue("~ " + roundedAverage);
        }
        else {
            super.setValue("-");
        }
    }
}


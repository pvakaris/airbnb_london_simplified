import java.util.List;

/**
 * Statistic referring to the number of entire homes/apartments.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */

public class HomesAndApartmentsStatistic extends PropertiesStatistic{

    public HomesAndApartmentsStatistic(AirbnbProperties properties) {
        super("Number of Entire Homes/Apartments" , properties);
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> propertyList = super.getPropertyList();
        int numberOfNonPrivateApartments = 0;

        for(AirbnbListing property : propertyList) {
            if(property != null && property.getRoom_type().equals("Entire home/apt")) {
                numberOfNonPrivateApartments++;
            }
        }

        super.setValue(numberOfNonPrivateApartments + "");
    }
}

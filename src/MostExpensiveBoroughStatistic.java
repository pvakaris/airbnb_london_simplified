import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Statistic referring to the Borough with the most expensive properties on average.
 * Takes the minimum number of nights into account.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public class MostExpensiveBoroughStatistic extends PropertiesStatistic {

    public MostExpensiveBoroughStatistic(AirbnbProperties properties) {
        super("Most Expensive Borough", properties);
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> propertyList = super.getPropertyList();

        // Maps boroughs to the total price of all their properties.
        // The minimum number of nights is taken into account when calculating property prices.
        Map<Borough, Integer> totalPricePerBorough = new HashMap<>();

        // Maps boroughs to the number of properties in each borough.
        Map<Borough, Integer> propertiesPerBorough = new HashMap<>();

        for(AirbnbListing property : propertyList) {
            if(property != null) {
                Borough borough = property.getNeighbourhood();
                int price = property.getPrice() * property.getMinimumNights();

                if(totalPricePerBorough.containsKey(borough)) {
                    totalPricePerBorough.put(borough, totalPricePerBorough.get(borough) + price);
                }
                else {
                    totalPricePerBorough.put(borough, price);
                }

                if(propertiesPerBorough.containsKey(borough)) {
                    propertiesPerBorough.put(borough, propertiesPerBorough.get(borough) + 1);
                }
                else {
                    propertiesPerBorough.put(borough, 1);
                }
            }
        }

        Borough mostExpensiveBorough = null;
        double averagePriceOfMostExpensiveBorough = 0;

        Set<Borough> boroughSet = propertiesPerBorough.keySet();

        for(Borough borough : boroughSet) {
            double averagePrice = (double) totalPricePerBorough.get(borough) / (double) propertiesPerBorough.get(borough);

            if(mostExpensiveBorough == null || averagePrice > averagePriceOfMostExpensiveBorough) {
                mostExpensiveBorough = borough;
                averagePriceOfMostExpensiveBorough = averagePrice;
            }
        }

        if(mostExpensiveBorough != null) {
            super.setValue(mostExpensiveBorough.toString());
        }
    }
}
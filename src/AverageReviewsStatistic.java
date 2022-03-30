import java.util.List;

/**
 * Statistic referring to the average number of reviews per property.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public class AverageReviewsStatistic extends PropertiesStatistic {

    public AverageReviewsStatistic(AirbnbProperties properties) {
        super("Average Reviews per Property" , properties);
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> propertyList = super.getPropertyList();
        int numberOfProperties = propertyList.size();
        int numberOfReviews = 0;

        for(AirbnbListing property : propertyList) {
            if(property != null) {
                numberOfReviews += property.getNumberOfReviews();
            }
        }

        double averageReviews = (double) numberOfReviews/numberOfProperties;
        averageReviews = (double) ((int) (averageReviews * 10))/10;

        super.setValue("~ " + averageReviews);
    }
}

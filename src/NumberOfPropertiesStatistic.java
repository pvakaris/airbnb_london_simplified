import java.util.List;

/**
 * Statistic referring to the total number of properties.
 */
public class NumberOfPropertiesStatistic extends PropertiesStatistic {

    public NumberOfPropertiesStatistic(AirbnbProperties properties) {
        super("Number of Properties" , properties);
    }

    @Override
    public void updateValue() {
        List<AirbnbListing> propertyList = super.getPropertyList();
        int numberOfProperties = propertyList.size();

        super.setValue(numberOfProperties + "");
    }
}

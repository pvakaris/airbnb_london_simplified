import java.util.List;

/**
 * Represents a "statistic" referring to a ranged set of properties,
 * ranged between two priced values.
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public abstract class PropertiesStatistic extends Statistic {

    private AirbnbProperties properties;

    public PropertiesStatistic (String name, AirbnbProperties properties) {
        super(name);
        this.properties = properties;
    }

    protected List<AirbnbListing> getPropertyList() {
        return properties.getPropertyList();
    }
}

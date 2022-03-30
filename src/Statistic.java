import java.util.List;

/**
 * Represents a "statistic" referring to a set of property listings.
 * It has a name (e.g., "Number of Properties") and a value (e.g., "20").
 *
 * @author Flavio Melinte Citea
 * @version 2021.03.25
 */
public abstract class Statistic {

    private String name;
    private String value;

    public Statistic (String name) {
        if(name == null) {
            name = "Unknown Statistic";
        }
        this.name = name;

        value = "-";
    }

    public abstract void updateValue();

    protected abstract List<AirbnbListing> getPropertyList();

    protected void setValue(String newValue) {
        value = newValue;
        if(value == null) {
            value = "-";
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

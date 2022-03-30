import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class that loads property data into an ArrayList,
 * and manipulates data before being sent to the GUI.
 *
 * @author Talha Abdulkuddus (20076327)
 * @version 2021.03.23
 */
public class AirbnbProperties {

    // List of all properties available.
    private final List<AirbnbListing> propertyList;

    // The price bounds to return data between.
    private int fromPrice;
    private int toPrice;

    public AirbnbProperties() {
        propertyList = DatabaseController.getInstance().getProperties();
    }

    /**
     * Returns all properties in between the given prices.
     * @return All properties in the given range.
     */
    public List<AirbnbListing> getPropertyList() {
        return propertyList.stream()
                .filter(property -> property.getPrice() >= fromPrice)
                .filter(property -> property.getPrice() <= toPrice)
                .collect(Collectors.toList());
    }

    /**
     * Returns all properties in between the given prices for a specific borough.
     * @param borough The borough to return properties for.
     * @return All properties in that borough within the price range.
     */
    public List<AirbnbListing> getBoroughPropertyList(Borough borough) {
        return getPropertyList().stream()
                .filter(property -> property.getNeighbourhood() == borough)
                .collect(Collectors.toList());
    }

    /**
     * Returns the property with the given id, or null if not found. Can optionally specify whether to restrict
     * properties to lie in price range or not.
     * @param id The id of the property to search for.
     * @param inPriceRange Whether the property should be within the set price range.
     * @return The property with the corresponding id, or null if not found.
     */
    public AirbnbListing getPropertyById(String id, boolean inPriceRange) {
        Stream<AirbnbListing> properties = inPriceRange ? getPropertyList().stream() : propertyList.stream();
        return properties
                    .filter(p -> id.equals(p.getId()))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Set the minimum price for properties (inclusive).
     * @param fromPrice The minimum price.
     */
    public void setFromPrice(int fromPrice) {
        this.fromPrice = fromPrice;
    }

    /**
     * Set the maximum price for properties (inclusive).
     * @param toPrice The maximum price.
     */
    public void setToPrice(int toPrice) {
        this.toPrice = toPrice;
    }

}

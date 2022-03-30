import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import java.net.URISyntaxException;

public class AirbnbDataLoader {

    /**
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     */
    public ArrayList<AirbnbListing> load() {
        System.out.print("Begin loading Airbnb london dataset...");
        ArrayList<AirbnbListing> listings = new ArrayList<>();
        try {
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String[] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                Borough neighbourhood = convertNeighbourhood(line[4]);
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                );
                listings.add(listing);
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }

    /**
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString) {
        if (doubleString != null && !doubleString.trim().equals("")) {
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is
     * either empty or just whitespace
     */
    private Integer convertInt(String intString) {
        if (intString != null && !intString.trim().equals("")) {
            return Integer.parseInt(intString);
        }
        return -1;
    }

    /**
     * @param boroughString the string to be converted to a Borough enum
     * @return the Borough value of the string, or null if not matching.
     */
    private Borough convertNeighbourhood(String boroughString) {
        for (Borough borough : Borough.values()) {
            if (borough.toString().equals(boroughString)) {
                return borough;
            }
        }
        return null;
    }

}

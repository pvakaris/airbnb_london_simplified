import com.opencsv.CSVReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.*;

/**
 * This class is responsible for retrieving, holding, modifying user data as well as their reservations.
 * At the first initialisation of the DatabaseController, all the users are loaded to the local users database
 * (ArrayList users).
 *
 * @author Vakaris Paulavičius (Student number: 20062023)
 * @version 1.6.1
 */
public class DatabaseController {

    // SINGLETON PATTERN - only one database controller is needed.
    private static DatabaseController instance;
    // Path to the user database.
    private final String userDatabasePath = "users-data.csv";
    // Path to the database which stores user reservations.
    private final String reservationsDatabasePath = "users-reservations.csv";

    // Local users database.
    private final ArrayList<User> users;
    // An ArrayList that stores all the properties.
    private final ArrayList<AirbnbListing> properties;

    /**
     * Private constructor of the database controller.
     */
    private DatabaseController() {
        users = new ArrayList<>();
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        properties = dataLoader.load();
        readUsers();
    }

    /**
     * Used to get the DatabaseController object.
     * If it does not exist yet, a new static object of this class is initialised and returned.
     * @return DatabaseController object.
     */
    public static DatabaseController getInstance() {
        if(instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    /**
     * Used to get an ArrayList with all of the properties that are available in Airbnb London.
     * @return A list of properties.
     */
    public ArrayList<AirbnbListing> getProperties() {
        return properties;
    }

    /**
     * Used to add reservation for the user.
     * @param user A user which made the reservation.
     * @param reservation Description of a reservation in a clear and concise String format.
     */
    public void addReservation(User user, AirbnbListing reservation) {
        if (!user.getReservations().contains(reservation)) {
            user.addReservation(reservation);
            // Username is unique (A Primary Key), so it's easy to identify which reservations belong to which user.
            putReservationToRemoteDatabase(user.getUsername(), reservation.getId());
        }
    }

    /**
     * Used to add a new user to the database.
     * @param newUser A new user to add.
     */
    public void addUser(User newUser) {
        // Adds to the local database.
        users.add(newUser);
        // Adds to the remote database.
        putUserToRemoteDatabase(newUser);
    }

    /**
     * Used to get a user from the local database according to the provided
     * username and password. This method is primarily used in the login process.
     * @param username User's username.
     * @param password User's password.
     * @return User object according to the parameters or null if there is no such user in the local database.
     */
    public User getUser(String username, String password) {
        if(username != null && password != null) {
            for(User user : users) {
                if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
            return null;
        }
        return null;
    }

    /**
     * Checks if a user with the provided username can be found in the database.
     * Primarily used to check whether the username is free and can be used in the registration.
     * @param username Username to check.
     * @return true if such a username exists, false otherwise.
     */
    public boolean usernameExists(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Used to get a list of all the users that exist in the system.
     * @return An ArrayList of users.
     */
    public ArrayList<User> getAllUsers() {
        return users;
    }

    /////////////////            PRIVATE METHODS

    /**
     * Used to find an AirbnbListing property using its id.
     * @param id According to which to look.
     * @return An AirbnbListing.
     */
    private AirbnbListing findPropertyUsingItsId(String id) {
        for(AirbnbListing property : properties) {
            if(property.getId().equals(id)) {
                return property;
            }
        }
        return null;
    }

    /**
     * Used to create a user object and add it to the local database.
     * @param details All the personal details of a user stored in an array.
     * @return A newly created User object.
     */
    private User createUser(String[] details) {
        User user = new User(details[0], details[1], details[2], details[3], details[4], details[5]);
        lookForReservations(user);
        return user;
    }

    /**
     * Used to get all the data about users from the database.
     */
    private void readUsers() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(new File(getClass().getResource(userDatabasePath).toURI()).getAbsolutePath()));
            String[] line;
            reader.readNext();
            while((line = reader.readNext()) != null) {
                users.add(createUser(line));
            }
        }
        catch (IOException | URISyntaxException exception) {
            System.err.println("File [" + userDatabasePath + "] was not found.");
        }
        finally {
            if(reader != null) {
                try {
                    reader.close();
                }
                catch (IOException exception) {
                    System.err.println("Something went wrong when trying to close the [" + userDatabasePath + "] file.");
                }
            }
        }
        System.out.println("Users were loaded successfully!");
    }

    /**
     * Used to insert a new user to the remote database.
     * @param user A new user to insert.
     */
    private void putUserToRemoteDatabase(User user){
        FileWriter writer = null;
        try {
            writer = new FileWriter(("src//" + userDatabasePath), true);
            writer.write("\n" + user.getFirstName() + "," + user.getLastName() + "," + user.getUsername() + "," + user.getPhoneNumber() + "," + user.getEmail() + "," + user.getPassword());
        }
        catch(IOException exception) {
            System.err.println("File [" + userDatabasePath + "] was not found.");
        }
        finally {
            if(writer != null) {
                try {
                    writer.close();
                }
                catch (IOException exception) {
                    System.err.println("Something went wrong when trying to close the [" + userDatabasePath + "] file.");
                }
            }
        }
        System.out.println("User [" + user.getUsername() + "] was successfully added to the [" + userDatabasePath + "] file!");
    }

    /**
     * Used to get all the data about reservations from the database.
     * @param user User whose reservations to look for.
     */
    private void lookForReservations(User user) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(new File(getClass().getResource(reservationsDatabasePath).toURI()).getAbsolutePath()));
            String[] line;
            reader.readNext();
            while((line = reader.readNext()) != null) {
                if(user.getUsername().equals(line[0])) {
                    // If the username matches, add reservation to users local list of reservations.
                    AirbnbListing property = findPropertyUsingItsId(line[1]);
                    if(property != null) {
                        user.addReservation(property);
                    }
                }
            }
        }
        catch (IOException | URISyntaxException exception) {
            System.err.println("File [" + reservationsDatabasePath + "] was not found.");
        }
        finally {
            if(reader != null) {
                try {
                    reader.close();
                }
                catch (IOException exception) {
                    System.err.println("Something went wrong when trying to close the [" + reservationsDatabasePath + "] file.");
                }
            }
        }
        System.out.println("Reservations of the user [" + user.getUsername() + "] were loaded.");
    }

    /**
     * Used to insert a reservation to the remote database.
     * @param username Username of a user who made the reservation
     */
    private void putReservationToRemoteDatabase(String username, String reservationId){
        FileWriter writer = null;
        try {
            writer = new FileWriter(("src//" + reservationsDatabasePath),true);
            writer.write("\n" + username + "," + reservationId);
        }
        catch(IOException exception) {
            System.err.println("File [" + reservationsDatabasePath + "] was not found.");
        }
        finally {
            if(writer != null) {
                try {
                    writer.close();
                }
                catch (IOException exception) {
                    System.err.println("Something went wrong when trying to close the [" + reservationsDatabasePath + "] file.");
                }
            }
        }
        System.out.println("Reservation of the user [" + username + "] was successfully added to the file [" + reservationsDatabasePath + "]!");
    }
}
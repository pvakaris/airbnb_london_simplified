import java.util.ArrayList;

/**
 * This class represent a user's account that exists in the database.
 * At the start of the application, for each user from the database, an object is created and put to the local database.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.1
 */
public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    // A list of all the reservations this user has.
    private ArrayList<AirbnbListing> reservations;

    /**
     * Constructor of the user object.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param username User's username used to log in to the system.
     * @param phoneNumber User's mobile phone number.
     * @param email User's email address.
     * @param password User's password.
     */
    public User(String firstName, String lastName, String username, String phoneNumber, String email, String password) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUsername(username);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
        this.setPassword(password);
        reservations = new ArrayList<>();
    }

    /**
     * Used to get the first name of the user.
     * @return User's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Used to set the first name of the user.
     * @param firstName User's last name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Used to get the last name of the user.
     * @return User's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Used to set the last name of the user.
     * @param lastName User's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Used to get the phone number of the user.
     * @return User's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Used to set a new phone number for the user.
     * @param phoneNumber New phone number of this user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Used to get an ArrayList of all the reservations this user has in the system.
     * @return An ArrayList with reservations.
     */
    public ArrayList<AirbnbListing> getReservations() {
        return reservations;
    }

    /**
     * Used to set the reservations list for this user.
     * @param reservations A new list which to apply.
     */
    public void setReservations(ArrayList<AirbnbListing> reservations) {
        this.reservations = reservations;
    }

    /**
     * Used to add a new reservation to the list that contains all the current reservations.
     * @param reservation A new reservation which to add.
     */
    public void addReservation(AirbnbListing reservation) {
        getReservations().add(reservation);
    }

    /**
     * Used to get the username of this user.
     * @return User's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Used to set the new username for the user.
     * @param username User's new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Used to get the email of this user.
     * @return User's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Used to set the new email address for this user.
     * @param email User's new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Used to get the password of this user.
     * @return User's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Used to set a new password for the user.
     * @param password User's new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
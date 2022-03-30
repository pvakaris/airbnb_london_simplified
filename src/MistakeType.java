/**
 * A collection of all possible mistakes a user can make in the registration form.
 * If a mistake is detected, an alert with appropriate message is displayed to the user.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.2
 */
public enum MistakeType {

    ILLEGAL_NAME_FORMAT("The first letter of the name/surname must be capital. Other letters must be lowercase."),
    NON_LETTER_IN_NAME("Names and surnames can only consist of letters."),
    WRONG_PHONE_FORMAT("The phone number must be of the format +44 *** *** ****."),
    PASSWORDS_DONT_MATCH("Passwords do not match."),
    USERNAME_EXISTS("Username already exists. Please choose another one."),
    WRONG_EMAIL_FORMAT("The email address is of a wrong format."),
    EMPTY_STRING("None of the fields can be empty."),
    ILLEGAL_SYMBOL_IN_USERNAME("Usernames can only consist of letters and numbers."),
    NAME_TOO_LONG("Name and surnames can be of at most 32 letters."),
    USERNAME_TOO_LONG("Username can be of at most 16 characters."),
    EMAIL_TOO_LONG("Emails can consist of at most 64 characters."),
    PASSWORD_HAS_ILLEGAL_SYMBOLS("Password cannot contain symbols like comma (,) or spaces."),
    PASSWORD_TOO_LONG("The password can consist of at most 32 characters.");


    // An error message of the mistake type.
    private final String errorMessage;

    /**
     * Constructor of a MistakeType
     * @param errorMessage Message which should be displayed when this type of mistake occurs.
     */
    MistakeType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Return the message of the mistake.
     * @return message, which should be provided.
     */
    public String toString() {
        return errorMessage;
    }
}
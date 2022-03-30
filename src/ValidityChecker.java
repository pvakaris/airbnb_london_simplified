import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for checking whether user input data is valid.
 * This class contains a series of methods that check different validity aspects.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.3
 */
public class ValidityChecker {

    // SINGLETON PATTERN - only one validity checker is needed.
    private static ValidityChecker instance;

    /**
     * Private constructor of the ValidityChecker.
     */
    private ValidityChecker() {}

    /**
     * Used to get the ValidityChecker object.
     * If it does not exist yet, a new static object of this class is initialised and returned.
     * @return ValidityChecker object.
     */
    public static ValidityChecker getInstance() {
        if(instance == null) {
            instance = new ValidityChecker();
        }
        return instance;
    }

    /**
     * Checks if the provided email is of a valid email format.
     * @param email Email which to check.
     * @return MistakeType if a mistake was found, null if the email address is valid.
     */
    public MistakeType emailFormatValid(String email) {
        if(!stringEmpty(email)) {
            if(email.length() <= 64) {
                // The regular expression below is borrowed from: https://stackoverflow.com/a/8204716/14825089
                // I couldn't come up with my own regEx, so I've decided to look for an answer.

                Pattern emailFormat = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = emailFormat.matcher(email);
                if(!matcher.matches()) {
                    return MistakeType.WRONG_EMAIL_FORMAT;
                }
                else {
                    return null;
                }
            }
            else {
                return MistakeType.EMAIL_TOO_LONG;
            }
        }
        else {
            return MistakeType.EMPTY_STRING;
        }
    }

    /**
     * Checks if the given phone number in a String object is of a valid UK phone number format:
     * +44 *** *** ****
     * @param phoneNumber A phone number which to check.
     * @return MistakeType if a mistake was found, null if the phone number format is valid.
     */
    public MistakeType ukPhoneNumberFormatValid(String phoneNumber) {
        if(!stringEmpty(phoneNumber)) {
            // UK FORMAT: starts with a +, then has two 4s, and then 10 digits [0-9].
            // The least messy way to check the validity is to use a regular expression.

            Pattern numberFormat = Pattern.compile("^\\+[4]{2}[0-9]{10}$");
            Matcher matcher = numberFormat.matcher(phoneNumber);
            if(!matcher.matches()) {
                return MistakeType.WRONG_PHONE_FORMAT;
            }
            else {
                return null;
            }
        }
        else {
            return MistakeType.EMPTY_STRING;
        }
    }

    /**
     * Checks if the given string already exists in the database as someone's username.
     * @param username A username to check.
     * @return MistakeType if a mistake was found, null if the username is valid.
     */
    public MistakeType usernameValid(String username) {
        if(!stringEmpty(username)) {
            if(username.length() <= 16) {
                DatabaseController dataBase = DatabaseController.getInstance();
                if(!dataBase.usernameExists(username)) {
                    char[] letters = username.toCharArray();

                    // Check for illegal symbols.
                    for(char ch : letters) {
                        if(!Character.isLetterOrDigit(ch)) {
                            return MistakeType.ILLEGAL_SYMBOL_IN_USERNAME;
                        }
                    }
                    return null;
                }
                else {
                    return MistakeType.USERNAME_EXISTS;
                }
            }
            else {
                return MistakeType.USERNAME_TOO_LONG;
            }
        }
        else {
            return MistakeType.EMPTY_STRING;
        }
    }

    /**
     * Checks if the strings are identical in text.
     * @return MistakeType if a mistake was found, null if the password is valid.
     */
    public MistakeType passwordsMatch(String string1, String string2) {
        if(!stringEmpty(string1) && !stringEmpty(string2)) {
            if(string1.equals(string2)) {
                return null;
            }
            else {
                return MistakeType.PASSWORDS_DONT_MATCH;
            }
        }
        else {
            return MistakeType.EMPTY_STRING;
        }
    }

    /**
     * Checks if the name is of a valid format.
     * If no, sends an alert to the user.
     * @param name A name which to check.
     * @return MistakeType if a mistake was found, null if the name has no mistakes.
     */
    public MistakeType nameValid(String name) {
        if(!stringEmpty(name)) {
            if(name.length() <= 32) {
                char[] letters = name.toCharArray();
                // Check for non letter symbols.
                for(char ch : letters) {
                    if(!Character.isLetter(ch)) {
                        return MistakeType.NON_LETTER_IN_NAME;
                    }
                }
                // Check if the first letter is the capital one
                if(name.charAt(0) == name.toLowerCase().charAt(0)) {
                    return MistakeType.ILLEGAL_NAME_FORMAT;
                }
                // Check if all the other letters are lowercase.
                for(int i = 1; i < letters.length; i ++) {
                    if(name.charAt(i) == name.toUpperCase().charAt(i)) {
                        return MistakeType.ILLEGAL_NAME_FORMAT;
                    }
                }
                return null;
            }
            else {
                // If the name's length exceeds 32 characters.
                return MistakeType.NAME_TOO_LONG;
            }
        }
        else {
            return MistakeType.EMPTY_STRING;
        }
    }

    /**
     * Checks if the provided password is of a valid format.
     * @param password A password to check.
     * @return MistakeType if a mistake was found, null if the password is valid.
     */
    public MistakeType passwordValid(String password) {
        if(!stringEmpty(password)) {
            if(password.length() <= 32) {
                if(!password.contains(" ") && !password.contains(",")) {
                    return null;
                }
                else {
                    return MistakeType.PASSWORD_HAS_ILLEGAL_SYMBOLS;
                }
            }
            else {
                return MistakeType.PASSWORD_TOO_LONG;
            }
        }
        else {
            return MistakeType.EMPTY_STRING;
        }
    }

    /////////////////            PRIVATE METHODS

    /**
     * Checks if the provided string is empty or is null.
     * If it is, this method also send an alert to the user.
     * @param string A string which to check.
     * @return true if the provided string is empty or is null, false otherwise.
     */
    private boolean stringEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
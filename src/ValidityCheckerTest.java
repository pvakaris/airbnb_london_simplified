import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible to check, whether the ValidityChecker evaluates the user input correctly.
 * If the output is correct, the ValidityChecker returns null, else returns MistakeType.
 *
 * @author Vakaris Paulavičius (Student number: K20062023)
 * @version 1.1
 */
class ValidityCheckerTest {

    // A ValidityChecker object used to test data.
    private final ValidityChecker checker = ValidityChecker.getInstance();

    /**
     * Considers some of the possible variants of emails and evaluates them.
     */
    @Test
    void emailFormatValid() {
        String email;

        email = "k20062023@kcl.ac.uk";
        assertNull(checker.emailFormatValid(email));

        email = "john.roof@yahoo.net";
        assertNull(checker.emailFormatValid(email));

        email = "orange@juice@gmail.com";
        assertEquals(MistakeType.WRONG_EMAIL_FORMAT, checker.emailFormatValid(email));

        email = "orange@gmail.c";
        assertEquals(MistakeType.WRONG_EMAIL_FORMAT, checker.emailFormatValid(email));

        email = "ora,ngejuice@gmail.com";
        assertEquals(MistakeType.WRONG_EMAIL_FORMAT, checker.emailFormatValid(email));

        email = "ora ngejuice@gmail.com";
        assertEquals(MistakeType.WRONG_EMAIL_FORMAT, checker.emailFormatValid(email));

        email = "";
        assertEquals(MistakeType.EMPTY_STRING, checker.emailFormatValid(email));

        // An email can consist of no more than 64 characters.
        // 64Characters
        email = "OneOfTheLongestEmailAdressesICouldComeUpWith1234567654@gmail.com";
        assertNull(checker.emailFormatValid(email));
        // 65Characters
        email = "OneOfTheLongestEmailAdressesICouldComeUpWith12345676543@gmail.com";
        assertEquals(MistakeType.EMAIL_TOO_LONG, checker.emailFormatValid(email));
    }

    /**
     * Considers some of the possible variants of phone numbers and evaluates
     * whether they are of a valid international UK phone number format.
     */
    @Test
    void ukPhoneNumberFormatValid() {
        String number;
        // Only the full international UK phone number format +44 *** *** **** is accepted.

        number = "+446789345621";
        assertNull(checker.ukPhoneNumberFormatValid(number));

        number = "06789345621";
        assertEquals(MistakeType.WRONG_PHONE_FORMAT, checker.ukPhoneNumberFormatValid(number));

        number = "00446789345621";
        assertEquals(MistakeType.WRONG_PHONE_FORMAT, checker.ukPhoneNumberFormatValid(number));

        number = "+37063344678";
        assertEquals(MistakeType.WRONG_PHONE_FORMAT, checker.ukPhoneNumberFormatValid(number));

        // Spaces are not allowed.
        number = "+44 678 934 5621";
        assertEquals(MistakeType.WRONG_PHONE_FORMAT, checker.ukPhoneNumberFormatValid(number));

        number = "";
        assertEquals(MistakeType.EMPTY_STRING, checker.ukPhoneNumberFormatValid(number));
    }

    /**
     * Considers some of the possible variants of usernames and evaluates them.
     */
    @Test
    void usernameValid() {
        // Usernames can only consist of letters and digits.
        String username;

        username = "magicDragon323";
        assertNull(checker.usernameValid(username));

        username = "m@gicDragon#323";
        assertEquals(MistakeType.ILLEGAL_SYMBOL_IN_USERNAME, checker.usernameValid(username));

        username = "";
        assertEquals(MistakeType.EMPTY_STRING, checker.usernameValid(username));

        // At most, the username can have 16 characters.
        // 17 characters username
        username = "DragonSlayer12345";
        assertEquals(MistakeType.USERNAME_TOO_LONG, checker.usernameValid(username));

        // 16 characters username
        username = "DragonSlayer1234";
        assertNull(checker.usernameValid(username));
    }

    /**
     * Considers whether the two strings are identical.
     * Empty strings are considered not identical and return a MistakeType.
     */
    @Test
    void passwordsMatch() {
        String first;
        String second;

        first = "gaga";
        second = "gaga";
        assertNull(checker.passwordsMatch(first, second));

        first = "";
        second = "";
        assertEquals(MistakeType.EMPTY_STRING, checker.passwordsMatch(first, second));

        first = "";
        second = "apple";
        assertEquals(MistakeType.EMPTY_STRING, checker.passwordsMatch(first, second));

        first = "Apple";
        second = "apple";
        assertEquals(MistakeType.PASSWORDS_DONT_MATCH, checker.passwordsMatch(first, second));
    }

    /**
     * Considers some of the possible variants of names and surnames and evaluates them.
     */
    @Test
    void nameValid() {
        // Users are not expected to have names or surnames that exceed 32 characters.
        String name;

        // 35 characters name.
        name = "Wolfeschlegelsteinhausenbergerdorff";
        assertEquals(MistakeType.NAME_TOO_LONG, checker.nameValid(name));

        // 33 characters name.
        name = "Wolfeschlegelsteinhausenbergerdor";
        assertEquals(MistakeType.NAME_TOO_LONG, checker.nameValid(name));

        // 30 characters name.
        name = "Wolfeschlegelsteinhausenberger";
        assertNull(checker.nameValid(name));

        // 32 characters name.
        name = "Wolfeschlegelsteinhausenbergerde";
        assertNull(checker.nameValid(name));

        name = "JOhn";
        assertEquals(MistakeType.ILLEGAL_NAME_FORMAT, checker.nameValid(name));

        name = "john";
        assertEquals(MistakeType.ILLEGAL_NAME_FORMAT, checker.nameValid(name));

        name = "Abrah@m";
        assertEquals(MistakeType.NON_LETTER_IN_NAME, checker.nameValid(name));

        name = "Tyrone345";
        assertEquals(MistakeType.NON_LETTER_IN_NAME, checker.nameValid(name));

        name = "";
        assertEquals(MistakeType.EMPTY_STRING, checker.nameValid(name));
    }

    /**
     * Considers some of the possible variants of passwords and evaluates them.
     * Symbols like comma (,) or spaces are not allowed.
     */
    @Test
    void passwordValid() {
        String password;

        password = "You3Wont4Guess5";
        assertNull(checker.passwordValid(password));

        password = "Apple3,45";
        assertEquals(MistakeType.PASSWORD_HAS_ILLEGAL_SYMBOLS, checker.passwordValid(password));

        password = "App le345";
        assertEquals(MistakeType.PASSWORD_HAS_ILLEGAL_SYMBOLS, checker.passwordValid(password));

        password = "";
        assertEquals(MistakeType.EMPTY_STRING, checker.passwordValid(password));

        // At most, a password can have 32 characters.
        // 33 characters password.
        password = "gteuUfm48763HxvwtuGrTYUIOP5643213";
        assertEquals(MistakeType.PASSWORD_TOO_LONG, checker.passwordValid(password));

        // 45 characters password.
        password = "gteuUfm48763HxvwtuGrTYUIOP5643213dTrjuOpdt567";
        assertEquals(MistakeType.PASSWORD_TOO_LONG, checker.passwordValid(password));

        // 32 characters password.
        password = "gteuUfm48763HxvwtuGrTYUIOP564323";
        assertNull(checker.passwordValid(password));
    }
}
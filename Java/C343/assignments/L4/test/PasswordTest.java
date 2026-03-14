import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Password and Attack classes.
 */

class PasswordTest {

    /**
     * Tests the checkPassword method of the Password class.
     * Ensures the correct password matches and incorrect passwords do not.
     */

    @Test
    void checkPassword() throws NoSuchAlgorithmException {
        Password password = new Password("password");
        assertTrue(password.checkPassword("password"));
        assertFalse(password.checkPassword("password2"));
    }

    /**
     * Tests the brute-force attack logic of the Attack class.
     * Ensures it can correctly guess passwords of different lengths.
     */

    @Test
    void guesses() throws NoSuchAlgorithmException {
        Password password = new Password("ab");
        Attack attack = new Attack(password, 2);
        String cracked = attack.crack();
        assertEquals("ab", cracked);

        password = new Password("abcdefg");
        attack = new Attack(password, 7);
        cracked = attack.crack();
        assertEquals("abcdefg", cracked);

    }

    @Test
    void additionalTestCases() throws NoSuchAlgorithmException {
        //TODO  * Add your own test cases here!

        /**
         * -----------------------------
         * Examples of what you can test:
         * 1. Check if very short passwords (e.g., "a", "1") work correctly.
         * 2. Check if the `Attack` class can guess passwords containing numbers or special characters.
         * 3. Try passwords longer than 7 characters and observe performance.
         * 4. Use passwords with mixed cases (e.g., "AbC123") and test if they are guessed correctly.
         */


    }
}

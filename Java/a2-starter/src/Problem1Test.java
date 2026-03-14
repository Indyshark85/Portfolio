import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem1Test {

    @Test
    void testProblem1() {
        assertAll(
                () -> assertEquals(true, Problem1.isPalindromeTR("racecar")),
                () -> assertEquals(true, Problem1.isPalindromeTR("A")),
                () -> assertEquals(false, Problem1.isPalindromeTR("hello")),
                () -> assertEquals(true, Problem1.isPalindromeTR("madam")),
                () -> assertEquals(false, Problem1.isPalindromeTR("java")),
                () -> assertEquals(true, Problem1.isPalindromeLoop("racecar")),
                () -> assertEquals(true, Problem1.isPalindromeLoop("A")),
                () -> assertEquals(false, Problem1.isPalindromeLoop("hello")),
                () -> assertEquals(true, Problem1.isPalindromeLoop("madam")),
                () -> assertEquals(false, Problem1.isPalindromeLoop("java"))
        );
    }
}

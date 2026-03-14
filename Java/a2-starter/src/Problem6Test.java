import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem6Test {

    @Test
    void testProblem6() {
        assertAll(
                () -> assertEquals(null, Problem6.guessWord("PLANS", "TRAP")),
                () -> assertEquals("--A-*", Problem6.guessWord("PLANS", "TRAIN")),
                () -> assertEquals("PLAN-", Problem6.guessWord("PLANS", "PLANE")),
                () -> assertEquals("PLANS", Problem6.guessWord("PLANS", "PLANS")),
                () -> assertEquals("*****", Problem6.guessWord("PLANS", "SNLPA")),
                () -> assertEquals(null, Problem6.guessWord("WORD", "WORDLE")),
                () -> assertEquals("****", Problem6.guessWord("GAME", "MEGA")),
                () -> assertEquals("-----", Problem6.guessWord("ABCDE", "FGHIJ")),
                () -> assertEquals("GA-ES", Problem6.guessWord("GAMES", "GALES")),
                () -> assertEquals("GA-E-", Problem6.guessWord("GAMES", "GATEO"))
        );
    }
}

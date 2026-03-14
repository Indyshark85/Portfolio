import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem5Test {

    @Test
    void testProblem5() {
        assertAll(
                () -> assertEquals(0, Problem5.atoi("ABCD")),
                () -> assertEquals(42, Problem5.atoi("42")),
                () -> assertEquals(42, Problem5.atoi("000042")),
                () -> assertEquals(4200, Problem5.atoi("004200")),
                () -> assertEquals(42, Problem5.atoi("ABCD42ABCD")),
                () -> assertEquals(42, Problem5.atoi("ABCD+42ABCD")),
                () -> assertEquals(-42, Problem5.atoi("ABCD-42ABCD")),
                () -> assertEquals(-42000, Problem5.atoi("000-42000")),
                () -> assertEquals(0, Problem5.atoi("000-ABCD")),
                () -> assertEquals(0, Problem5.atoi("-+-+1234")),
                () -> assertEquals(0, Problem5.atoi("-A1234")),
                () -> assertEquals(42, Problem5.atoi("000+42ABCD")),
                () -> assertEquals(8080, Problem5.atoi("8080*8080"))
        );
    }
}

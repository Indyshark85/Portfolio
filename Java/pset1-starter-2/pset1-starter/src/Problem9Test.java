import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem9Test {

    @Test
    void testProblem9() {
        assertAll(
                () -> assertEquals(true, Problem9.isEvenlySpaced(2, 4, 6)),
                () -> assertEquals(true, Problem9.isEvenlySpaced(4, 6, 2)),
                () -> assertEquals(false, Problem9.isEvenlySpaced(4, 6, 3)),
                () -> assertEquals(true, Problem9.isEvenlySpaced(4, 8, 6)),
                () -> assertEquals(true, Problem9.isEvenlySpaced(1, 3, 5)),
                () -> assertEquals(false, Problem9.isEvenlySpaced(2, 4, 5))
        );

    }
}

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem3Test {

    @Test
    void testProblem3() {
        assertAll(
                () -> assertEquals(1, Problem3.subfactorial(0)),
                () -> assertEquals(0, Problem3.subfactorial(1)),
                () -> assertEquals(1, Problem3.subfactorial(2)),
                () -> assertEquals(2, Problem3.subfactorial(3)),
                () -> assertEquals(9, Problem3.subfactorial(4)),
                () -> assertEquals(44, Problem3.subfactorial(5)),
                () -> assertEquals(265, Problem3.subfactorial(6)),

                () -> assertEquals(1, Problem3.subfactorialTR(0)),
                () -> assertEquals(0, Problem3.subfactorialTR(1)),
                () -> assertEquals(1, Problem3.subfactorialTR(2)),
                () -> assertEquals(2, Problem3.subfactorialTR(3)),
                () -> assertEquals(9, Problem3.subfactorialTR(4)),
                () -> assertEquals(44, Problem3.subfactorialTR(5)),
                () -> assertEquals(265, Problem3.subfactorialTR(6)),

                () -> assertEquals(1, Problem3.subfactorialLoop(0)),
                () -> assertEquals(0, Problem3.subfactorialLoop(1)),
                () -> assertEquals(1, Problem3.subfactorialLoop(2)),
                () -> assertEquals(2, Problem3.subfactorialLoop(3)),
                () -> assertEquals(9, Problem3.subfactorialLoop(4)),
                () -> assertEquals(44, Problem3.subfactorialLoop(5)),
                () -> assertEquals(265, Problem3.subfactorialLoop(6))


        );
    }
}

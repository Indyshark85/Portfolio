import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem2Test {

    @Test
    void testProblem2() {
        assertAll(
                () -> assertEquals(1, Problem2.hyperfactorial(1)),
                () -> assertEquals(4, Problem2.hyperfactorial(2)),
                () -> assertEquals(108, Problem2.hyperfactorial(3)),
                () -> assertEquals(27648, Problem2.hyperfactorial(4)),
                () -> assertEquals(86400000, Problem2.hyperfactorial(5)),

                () -> assertEquals(1, Problem2.hyperfactorialTR(1)),
                () -> assertEquals(4, Problem2.hyperfactorialTR(2)),
                () -> assertEquals(108, Problem2.hyperfactorialTR(3)),
                () -> assertEquals(27648, Problem2.hyperfactorialTR(4)),
                () -> assertEquals(86400000, Problem2.hyperfactorialTR(5)),

                () -> assertEquals(1, Problem2.hyperfactorialLoop(1)),
                () -> assertEquals(4, Problem2.hyperfactorialLoop(2)),
                () -> assertEquals(108, Problem2.hyperfactorialLoop(3)),
                () -> assertEquals(27648, Problem2.hyperfactorialLoop(4)),
                () -> assertEquals(86400000, Problem2.hyperfactorialLoop(5))
        );
    }
}

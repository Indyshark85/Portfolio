import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem4Test {

    @Test
    void testProblem4() {
        assertAll(
                () -> assertEquals("1", Problem4.collatz(1)),
                () -> assertEquals("2,1", Problem4.collatz(2)),
                () -> assertEquals("3,10,5,16,8,4,2,1", Problem4.collatz(3)),
                () -> assertEquals("4,2,1", Problem4.collatz(4)),
                () -> assertEquals("5,16,8,4,2,1", Problem4.collatz(5)),

                () -> assertEquals("1", Problem4.collatzTR(1)),
                () -> assertEquals("2,1", Problem4.collatzTR(2)),
                () -> assertEquals("3,10,5,16,8,4,2,1", Problem4.collatzTR(3)),
                () -> assertEquals("4,2,1", Problem4.collatzTR(4)),
                () -> assertEquals("5,16,8,4,2,1", Problem4.collatzTR(5)),

                () -> assertEquals("1", Problem4.collatzLoop(1)),
                () -> assertEquals("2,1", Problem4.collatzLoop(2)),
                () -> assertEquals("3,10,5,16,8,4,2,1", Problem4.collatzLoop(3)),
                () -> assertEquals("4,2,1", Problem4.collatzLoop(4)),
                () -> assertEquals("5,16,8,4,2,1", Problem4.collatzLoop(5))
        );
    }
}

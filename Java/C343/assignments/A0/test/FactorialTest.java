import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    void factorial() {
        assertEquals(1, Factorial.factorial(0));
        assertEquals(1, Factorial.factorial(1));
        assertEquals(2, Factorial.factorial(2));
        assertEquals(6, Factorial.factorial(3));
        assertEquals(24, Factorial.factorial(4));
        assertEquals(120, Factorial.factorial(5));
        assertEquals(3_628_800, Factorial.factorial(10));
        assertEquals(479_001_600, Factorial.factorial(12)); // highest safe in int
    }

    @Test
    void factorial_acc() {
        // When acc = 1, it should match the plain recursive version
        assertEquals(Factorial.factorial(0), Factorial.factorial_acc(0, 1));
        assertEquals(Factorial.factorial(1), Factorial.factorial_acc(1, 1));
        assertEquals(Factorial.factorial(2), Factorial.factorial_acc(2, 1));
        assertEquals(Factorial.factorial(5), Factorial.factorial_acc(5, 1));
        assertEquals(Factorial.factorial(10), Factorial.factorial_acc(10, 1));
        assertEquals(Factorial.factorial(12), Factorial.factorial_acc(12, 1));

        // Accumulator “pass-through”
        assertEquals(7,  Factorial.factorial_acc(0, 7));
        assertEquals(7,  Factorial.factorial_acc(1, 7));
        assertEquals(14, Factorial.factorial_acc(2, 7));
    }

    @Test
    void factorial_iter() {
        // When acc = 1, it should match the plain recursive version
        for (int n = 0; n <= 12; n++) {
            assertEquals(Factorial.factorial(n),
                    Factorial.factorial_iter(n, 1),
                    "mismatch at n=" + n);
        }

        // Accumulator “pass-through”
        assertEquals(9,  Factorial.factorial_iter(0, 9));
        assertEquals(9,  Factorial.factorial_iter(1, 9));
        assertEquals(18, Factorial.factorial_iter(2, 9));
    }
}
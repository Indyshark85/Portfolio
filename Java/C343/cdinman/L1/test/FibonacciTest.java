import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciTest {

    @Test
    void fibonacci() {
        // Base cases
        assertEquals(0, Fibonacci.fibonacci(0));
        assertEquals(1, Fibonacci.fibonacci(1));

        // Small n
        assertEquals(1, Fibonacci.fibonacci(2));
        assertEquals(5, Fibonacci.fibonacci(5));

        // Larger n
        assertEquals(55, Fibonacci.fibonacci(10));
        assertEquals(6765, Fibonacci.fibonacci(20));
    }

    @Test
    void fibonacci_iter() {
        // Base cases
        assertEquals(0, Fibonacci.fibonacci_iter(0));
        assertEquals(1, Fibonacci.fibonacci_iter(1));

        // Small n
        assertEquals(1, Fibonacci.fibonacci_iter(2));
        assertEquals(5, Fibonacci.fibonacci_iter(5));

        // Larger n
        assertEquals(55, Fibonacci.fibonacci_iter(10));
        assertEquals(6765, Fibonacci.fibonacci_iter(20));

    }

    @Test
    void same_output_rec_and_iter() {
        // Verify that both implementations agree for n = 0…20
        for (int i = 0; i <= 20; i++) {
            final int n = i;

            assertEquals(
                    Fibonacci.fibonacci(n),
                    Fibonacci.fibonacci_iter(n),
                    () -> "Mismatch at n = " + n
            );
        }
    }


    //From what I can observe the iterative process handles this better. As it is going through the problem one by one
    //Rather than going through the list essentially twice then doing the calculations making the iterative much more
    //efficient.
}
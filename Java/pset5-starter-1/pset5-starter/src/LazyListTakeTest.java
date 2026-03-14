import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LazyListTakeTest {

    @Test
    void testLazyListTake() {
        LazyListTake llt1 = new LazyListTake(new FibonacciLazyList(), 10);
        LazyListTake llt2 = new LazyListTake(new FibonacciLazyList(), 5);
        LazyListTake llt3 = new LazyListTake(new FibonacciLazyList(), 3);
        LazyListTake llt4 = new LazyListTake(new FibonacciLazyList(), 1);
        LazyListTake llt5 = new LazyListTake(new FibonacciLazyList(), 2);
        assertAll(
                () -> assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]",
                        llt1.getList().toString()),
                () -> assertEquals("[0, 1, 1, 2, 3]",
                        llt2.getList().toString()),
                () -> assertEquals("[0, 1, 1]",
                        llt3.getList().toString()),
                () -> assertEquals("[0]",
                        llt4.getList().toString()),
                () -> assertEquals("[0, 1]",
                        llt5.getList().toString())
                );
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciLazyListTest {

    @Test
    void testFibonacciLazyList() {
        ILazyList<Integer> FS = new FibonacciLazyList();
        assertAll(
                () -> assertEquals(0, FS.next()),
                () -> assertEquals(1, FS.next()),
                () -> assertEquals(1, FS.next()),
                () -> assertEquals(2, FS.next()),
                () -> assertEquals(3, FS.next()),
                () -> assertEquals(5, FS.next())
        );

    }
}

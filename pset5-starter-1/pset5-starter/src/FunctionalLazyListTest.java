import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionalLazyListTest {

    @Test
    void testFunctionalLazyList() {
        ILazyList<Integer> mtll = new FunctionalLazyList<>(x -> x + 3, 0);
        assertAll(
                () -> assertEquals(0, mtll.next()),
                () -> assertEquals(3, mtll.next()),
                () -> assertEquals(6, mtll.next()),
                () -> assertEquals(9, mtll.next()),
                () -> assertEquals(12, mtll.next())
        );
    }
}

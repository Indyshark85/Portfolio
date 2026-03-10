import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CyclicLazyListTest {

    @Test
    void testCyclicLazyList() {
        ILazyList<Integer> test= new CyclicLazyList<>(10,20,30);
        assertAll(
                ()-> assertEquals(10,test.next()),
                ()-> assertEquals(20,test.next()),
                ()-> assertEquals(30,test.next()),
                ()-> assertEquals(10,test.next()),
                ()-> assertEquals(20,test.next())
        );
    }
}

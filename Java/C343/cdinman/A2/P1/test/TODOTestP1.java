import dynamicArray.DequeueA;
import exceptions.EmptyDequeueE;
import org.junit.jupiter.api.Test;
import pointers.DequeueP;

import static org.junit.jupiter.api.Assertions.*;


public class TODOTestP1 {
    @Test
    void testDequeueP() {
        DequeueP<String> dp1 = new DequeueP<>();
        DequeueP<String> dp2 = new DequeueP<>();

        dp1.enqueueBack("A");
        dp1.enqueueBack("B");
        dp1.enqueueBack("C");

        dp2.enqueueBack("A");
        dp2.enqueueBack("B");
        dp2.enqueueBack("C");

        assertEquals(dp1, dp2);
        assertEquals(dp1.hashCode(), dp2.hashCode());

        dp2.enqueueBack("D");
        assertNotEquals(dp1, dp2);

        try {
            assertEquals("A", dp1.peekFront());
            assertEquals("C", dp1.peekBack());
        } catch (EmptyDequeueE e) {
            fail("Unexpected EmptyDequeueE during peek");
        }
    }

    @Test
    void testDequeueA() {
        DequeueA<String> da1 = new DequeueA<>(3);
        DequeueA<String> da2 = new DequeueA<>(3);

        da1.enqueueBack("X");
        da1.enqueueBack("Y");
        da1.enqueueBack("Z");

        da2.enqueueBack("X");
        da2.enqueueBack("Y");
        da2.enqueueBack("Z");

        assertEquals(da1, da2);
        assertEquals(da1.hashCode(), da2.hashCode());

        da1.enqueueBack("W"); // triggers doubleCapacity
        da2.enqueueBack("W");

        assertEquals(da1, da2);
        assertEquals(da1.hashCode(), da2.hashCode());

        da2.enqueueBack("Q");
        assertNotEquals(da1, da2);

        try {
            assertEquals("X", da1.peekFront());
            assertEquals("W", da1.peekBack());
        } catch (EmptyDequeueE e) {
            fail("Unexpected EmptyDequeueE during peek");
        }
    }

}

import exceptions.EmptyDequeueE;
import org.junit.jupiter.api.Test;
import pointers.DequeueP;

import static org.junit.jupiter.api.Assertions.*;

class DequeuePTest {
    @Test
    void testFront() throws EmptyDequeueE {
        DequeueP<Integer> q = new DequeueP<>();
        q.enqueueFront(1);
        q.enqueueFront(2);
        q.enqueueFront(3);
        assertEquals("F[3, 2, 1]B", q.toString());
        assertEquals(3, q.dequeueFront());
        assertEquals(2, q.dequeueFront());
        assertEquals(1, q.dequeueFront());
    }

    @Test
    void testBack() throws EmptyDequeueE {
        DequeueP<Integer> q = new DequeueP<>();
        q.enqueueBack(1);
        q.enqueueBack(2);
        q.enqueueBack(3);
        assertEquals("F[1, 2, 3]B", q.toString());
        assertEquals(3, q.dequeueBack());
        assertEquals(2, q.dequeueBack());
        assertEquals(1, q.dequeueBack());
    }

    @Test
    void testFrontBack() throws EmptyDequeueE {
        DequeueP<Integer> q = new DequeueP<>();
        q.enqueueFront(1);
        q.enqueueFront(2);
        q.enqueueFront(3);
        assertEquals("F[3, 2, 1]B", q.toString());
        assertEquals(1, q.dequeueBack());
        assertEquals(2, q.dequeueBack());
        assertEquals(3, q.dequeueBack());
    }
}
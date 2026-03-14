import exceptions.EmptyListE;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistentLLTest {
    @Test
    void test1 () throws EmptyListE {
        PersistentLL<Integer> list = PersistentLL.makeList(1, 2, 3, 4, 5);
        assertEquals(5, list.length());
        assertTrue(list.find(3));
        assertFalse(list.find(6));
        assertEquals("1 , 2 , 3 , 4 , 5 , *", list.toString());
        list = list.insert(6, 2);
        assertEquals("1 , 2 , 6 , 3 , 4 , 5 , *", list.toString());
        list = list.delete(2);
        assertEquals("1 , 2 , 3 , 4 , 5 , *", list.toString());
    }

    @Test
    void testPersistence() throws EmptyListE {
        PersistentLL<Integer> original = PersistentLL.makeList(1, 2, 3);
        PersistentLL<Integer> modified = original.insert(99, 1);

        assertEquals("1 , 2 , 3 , *", original.toString()); // original unchanged
        assertEquals("1 , 99 , 2 , 3 , *", modified.toString());
    }

    @Test
    void testInsertDeleteBoundaries() throws EmptyListE {
        PersistentLL<Integer> list = PersistentLL.makeList(10, 20);
        list = list.insert(5, 0); // insert at head
        list = list.insert(30, list.length()); // insert at tail
        assertEquals("5 , 10 , 20 , 30 , *", list.toString());

        list = list.delete(0); // delete head
        list = list.delete(list.length() - 1); // delete tail
        assertEquals("10 , 20 , *", list.toString());
    }
}
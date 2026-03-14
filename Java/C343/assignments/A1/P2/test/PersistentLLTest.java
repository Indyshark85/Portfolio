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
}
import hash.chains.Chained;
import hash.exceptions.KeyNotFoundE;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChainedTest {

    @Test
    public void test1() throws KeyNotFoundE {
        Chained<Integer,String> ht = new Chained<>(5, k -> k);
        ht.put(1, "A");
        assertEquals("A", ht.get(1));
        assertEquals(1, ht.size());
        assertEquals(0.2, ht.getLoadFactor(), 0.01);
        ht.clear();
        assertEquals(0, ht.size());
        assertEquals(0.0, ht.getLoadFactor(), 0.01);
        ht.put(1, "A");
        ht.put(6, "B");
        ht.put(11, "C");
        ht.put(16, "D");
        ht.put(21, "E");
        Set<Integer> keys = ht.keySet();
        assertEquals(5, keys.size());
        Set<String> values = ht.values();
        assertEquals(5, values.size());
        assertEquals("A", ht.get(1));
        assertTrue(ht.containsKey(1));
        assertTrue(ht.containsValue("A"));
        ht.remove(1);
        assertEquals(4, ht.size());
        assertEquals("B", ht.get(6));
        assertEquals("C", ht.get(11));
        assertEquals("D", ht.get(16));
        assertEquals("E", ht.get(21));
        ht.rehash();
        assertEquals(4, ht.size());
        assertEquals("B", ht.get(6));
        assertEquals("C", ht.get(11));
        assertEquals("D", ht.get(16));
        assertEquals("E", ht.get(21));
        assertThrows(KeyNotFoundE.class, () -> ht.get(1));
        assertThrows(KeyNotFoundE.class, () -> ht.remove(1));
        assertFalse(ht.containsKey(1));
        assertFalse(ht.containsValue("A"));
    }


}
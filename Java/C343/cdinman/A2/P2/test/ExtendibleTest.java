import hash.exceptions.KeyNotFoundE;
import hash.extendible.Extendible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendibleTest {

    private Extendible<String, Integer> hashMap;

    @BeforeEach
    void setUp() {
        hashMap = new Extendible<>();
    }

    // Test 1 for get(): Valid key that exists
    @Test
    void testGet_validKey() throws KeyNotFoundE {
        hashMap.put("key1", 100);
        hashMap.put("key2", 200);
        hashMap.put("key3", 300);

        assertEquals(100, hashMap.get("key1"));
        assertEquals(200, hashMap.get("key2"));
        assertEquals(300, hashMap.get("key3"));
    }


    // Test 1 for remove(): Valid key that exists
    @Test
    void testRemove_validKey() throws KeyNotFoundE {
        hashMap.put("key1", 100);
        hashMap.put("key2", 200);
        int initialSize = hashMap.size();

        hashMap.remove("key1");

        assertEquals(initialSize - 1, hashMap.size());
        assertFalse(hashMap.containsKey("key1"));
        assertTrue(hashMap.containsKey("key2"));
    }


    // normal inserts should return all keys
    @Test
    void testKeySet_basic() {
        hashMap.put("A", 1);
        hashMap.put("B", 2);
        hashMap.put("C", 3);
        assertEquals(Set.of("A","B","C"), hashMap.keySet());
    }

    // normal inserts should return all values
    @Test
    void testValues_basic() {
        hashMap.put("A", 100);
        hashMap.put("B", 200);
        hashMap.put("C", 300);
        assertEquals(Set.of(100,200,300), hashMap.values());
    }

    // duplicate values should collapse into unique set
    @Test
    void testValues_duplicates() {
        hashMap.put("A", 1);
        hashMap.put("B", 1);
        hashMap.put("C", 2);
        assertEquals(Set.of(1,2), hashMap.values());
    }

    @Test
    void allExtendibleBehaviors() throws KeyNotFoundE {
        Extendible<Integer, String> ht = new Extendible<>();

        // empty basics
        assertTrue(ht.isEmpty());
        assertEquals(0, ht.size());

        // put + update same key (size unchanged on update)
        ht.put(1, "A");
        assertFalse(ht.isEmpty());
        assertEquals(1, ht.size());
        assertEquals("A", ht.get(1));
        ht.put(1, "A2");
        assertEquals(1, ht.size());
        assertEquals("A2", ht.get(1));
        ht.remove(1);

        // bulk inserts to exercise bucket splits and directory growth
        int N = 4096;
        int bound = 50_000;
        Random r = new Random(42);
        Map<Integer, String> expected = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int k = r.nextInt(bound);       // allow duplicates to test updates
            String v = "V" + i;             // last write wins
            ht.put(k, v);
            expected.put(k, v);
        }
        assertEquals(expected.size(), ht.size());

        // spot-check membership and retrieval
        for (Map.Entry<Integer,String> e : expected.entrySet()) {
            assertTrue(ht.containsKey(e.getKey()));
            assertEquals(e.getValue(), ht.get(e.getKey()));
        }

        // keySet/values equality as sets (order-free)
        assertEquals(new HashSet<>(expected.keySet()), ht.keySet());
        assertEquals(new HashSet<>(expected.values()), ht.values());

        // containsValue hits and misses
        assertTrue(ht.containsValue("V0") || expected.values().isEmpty()); // if key 0 was overwritten, may differ
        assertFalse(ht.containsValue("__NOPE__"));

        // remove a subset and validate size + absence
        Iterator<Integer> it = expected.keySet().iterator();
        int removed = 0;
        List<Integer> removedKeys = new ArrayList<>();
        while (it.hasNext() && removed < 100) {
            Integer k = it.next();
            ht.remove(k);
            it.remove();
            removedKeys.add(k);
            removed++;
        }
        assertEquals(expected.size(), ht.size());
        for (int k : removedKeys) {
            assertFalse(ht.containsKey(k));
            assertThrows(KeyNotFoundE.class, () -> ht.get(k));
        }

        // rehash must preserve all remaining mappings
        ht.rehash();
        assertEquals(expected.size(), ht.size());
        for (Map.Entry<Integer,String> e : expected.entrySet()) {
            assertEquals(e.getValue(), ht.get(e.getKey()));
        }
        assertEquals(new HashSet<>(expected.keySet()), ht.keySet());
        assertEquals(new HashSet<>(expected.values()), ht.values());

        // missing-key behaviors
        assertFalse(ht.containsKey(Integer.MIN_VALUE));
        assertThrows(KeyNotFoundE.class, () -> ht.get(Integer.MIN_VALUE));
        assertThrows(KeyNotFoundE.class, () -> ht.remove(Integer.MIN_VALUE));
    }
}

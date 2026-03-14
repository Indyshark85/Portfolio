import hash.exceptions.KeyNotFoundE;
import hash.probing.DoubleHashing;
import hash.probing.Linear;
import hash.probing.Probing;
import hash.probing.Quadratic;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ProbingTest {

    @Test
    void testHash () {
        Function<Integer, Integer> hashFun = k -> k;
        Function<Integer, Integer> hashFun2 = k -> k + 7;

        Probing<Integer, String> linear = new Linear<>(5, hashFun);
        Probing<Integer, String> quadratic = new Quadratic<>(5, hashFun);
        Probing<Integer, String> doubleHashing = new DoubleHashing<>(5, hashFun, hashFun2);

        assertEquals(1, linear.hash(1, 0));
        assertEquals(2, linear.hash(1, 1));
        assertEquals(3, linear.hash(1, 2));
        assertEquals(4, linear.hash(1, 3));
        assertEquals(0, linear.hash(1, 4));
        assertEquals(1, linear.hash(1, 5));

        assertEquals(1, quadratic.hash(1, 0));
        assertEquals(2, quadratic.hash(1, 1));
        assertEquals(0, quadratic.hash(1, 2));
        assertEquals(0, quadratic.hash(1, 3));
        assertEquals(2, quadratic.hash(1, 4));
        assertEquals(1, quadratic.hash(1, 5));

        assertEquals(1, doubleHashing.hash(1, 0));
        assertEquals(4, doubleHashing.hash(1, 1));
        assertEquals(2, doubleHashing.hash(1, 2));
        assertEquals(0, doubleHashing.hash(1, 3));
        assertEquals(3, doubleHashing.hash(1, 4));
        assertEquals(1, doubleHashing.hash(1, 5));
    }

    @Test
    void testProbing() throws KeyNotFoundE {
        Function<Integer, Integer> hashFun = k -> k;
        Function<Integer, Integer> hashFun2 = k -> k + 7;

        Probing<Integer, String> linear = new Linear<>(5, hashFun);
        Probing<Integer, String> quadratic = new Quadratic<>(5, hashFun);
        Probing<Integer, String> doubleHashing = new DoubleHashing<>(5, hashFun, hashFun2);

        assertTrue(linear.isEmpty());
        assertTrue(quadratic.isEmpty());
        assertTrue(doubleHashing.isEmpty());

        linear.put(1, "A");
        quadratic.put(1, "A");
        doubleHashing.put(1, "A");

        linear.clear();
        quadratic.clear();
        doubleHashing.clear();

        assertTrue(linear.isEmpty());
        assertTrue(quadratic.isEmpty());
        assertTrue(doubleHashing.isEmpty());

        linear.put(1, "A");
        linear.put(6, "B");
        linear.put(11, "C");
        quadratic.put(1, "A");
        quadratic.put(6, "B");
        quadratic.put(11, "C");
        doubleHashing.put(1, "A");
        doubleHashing.put(6, "B");
        doubleHashing.put(11, "C");

        Set<Integer> keysLinear = linear.keySet();
        Set<Integer> keysQuadratic = quadratic.keySet();
        Set<Integer> keysDoubleHashing = doubleHashing.keySet();

        Set<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(6);
        expectedKeys.add(11);

        assertEquals(keysLinear, expectedKeys);
        assertEquals(keysQuadratic, expectedKeys);
        assertEquals(keysDoubleHashing, expectedKeys);

        Set<String> valuesLinear = linear.values();
        Set<String> valuesQuadratic = quadratic.values();
        Set<String> valuesDoubleHashing = doubleHashing.values();

        Set<String> expectedValues = new HashSet<>();
        expectedValues.add("A");
        expectedValues.add("B");
        expectedValues.add("C");

        assertEquals(valuesLinear, expectedValues);
        assertEquals(valuesQuadratic, expectedValues);
        assertEquals(valuesDoubleHashing, expectedValues);

        assertTrue(linear.containsKey(1));
        assertTrue(linear.containsKey(6));
        assertTrue(linear.containsKey(11));
        assertTrue(quadratic.containsKey(1));
        assertTrue(quadratic.containsKey(6));
        assertTrue(quadratic.containsKey(11));
        assertTrue(doubleHashing.containsKey(1));
        assertTrue(doubleHashing.containsKey(6));
        assertTrue(doubleHashing.containsKey(11));

        assertTrue(linear.containsValue("A"));
        assertTrue(linear.containsValue("B"));
        assertTrue(linear.containsValue("C"));
        assertTrue(quadratic.containsValue("A"));
        assertTrue(quadratic.containsValue("B"));
        assertTrue(quadratic.containsValue("C"));
        assertTrue(doubleHashing.containsValue("A"));
        assertTrue(doubleHashing.containsValue("B"));
        assertTrue(doubleHashing.containsValue("C"));

        linear.rehash();
        quadratic.rehash();
        doubleHashing.rehash();

        assertEquals(3, linear.size());
        assertEquals(3, quadratic.size());
        assertEquals(3, doubleHashing.size());

        assertEquals("A", linear.get(1));
        assertEquals("B", linear.get(6));
        assertEquals("C", linear.get(11));

        assertEquals("A", quadratic.get(1));
        assertEquals("B", quadratic.get(6));
        assertEquals("C", quadratic.get(11));

        assertEquals("A", doubleHashing.get(1));
        assertEquals("B", doubleHashing.get(6));
        assertEquals("C", doubleHashing.get(11));

        linear.remove(1);
        quadratic.remove(1);
        doubleHashing.remove(1);

        assertEquals(2, linear.size());
        assertEquals(2, quadratic.size());
        assertEquals(2, doubleHashing.size());

        assertEquals("B", linear.get(6));
        assertEquals("C", linear.get(11));

        assertEquals("B", quadratic.get(6));
        assertEquals("C", quadratic.get(11));

        assertEquals("B", doubleHashing.get(6));
        assertEquals("C", doubleHashing.get(11));
    }

    @Test
    void testMarked () throws KeyNotFoundE {
        Function<Integer, Integer> hashFun = k -> k;
        Function<Integer, Integer> hashFun2 = k -> k + 7;

        Probing<Integer, String> linear = new Linear<>(5, hashFun);
        Probing<Integer, String> quadratic = new Quadratic<>(5, hashFun);
        Probing<Integer, String> doubleHashing = new DoubleHashing<>(5, hashFun, hashFun2);

        linear.put(1, "A");
        linear.put(6, "B");
        linear.put(11, "C");
        quadratic.put(1, "A");
        quadratic.put(6, "B");
        quadratic.put(11, "C");
        doubleHashing.put(1, "A");
        doubleHashing.put(6, "B");
        doubleHashing.put(11, "C");

        linear.remove(1);
        quadratic.remove(1);
        doubleHashing.remove(1);

        assertTrue(linear.containsKey(6));
        assertTrue(linear.containsKey(11));
        assertTrue(quadratic.containsKey(6));
        assertTrue(quadratic.containsKey(11));
        assertTrue(doubleHashing.containsKey(6));
        assertTrue(doubleHashing.containsKey(11));
    }

    @Test
    void testDelete () throws KeyNotFoundE {
        Function<Integer, Integer> hashFun = k -> k;
        Function<Integer, Integer> hashFun2 = k -> k + 7;

        Probing<Integer, String> linear = new Linear<>(5, hashFun);
        Probing<Integer, String> quadratic = new Quadratic<>(5, hashFun);
        Probing<Integer, String> doubleHashing = new DoubleHashing<>(5, hashFun, hashFun2);

        linear.put(1, "A");
        linear.put(6, "B");
        linear.put(11, "C");
        quadratic.put(1, "A");
        quadratic.put(6, "B");
        quadratic.put(11, "C");
        doubleHashing.put(1, "A");
        doubleHashing.put(6, "B");
        doubleHashing.put(11, "C");

        linear.remove(6);
        quadratic.remove(6);
        doubleHashing.remove(6);

        assertEquals("C", linear.get(11));
        assertEquals("C", quadratic.get(11));
        assertEquals("C", doubleHashing.get(11));

        assertThrows(KeyNotFoundE.class, () -> linear.get(2));
        assertThrows(KeyNotFoundE.class, () -> quadratic.get(2));
        assertThrows(KeyNotFoundE.class, () -> doubleHashing.get(2));

        linear.put(2, "D");
        quadratic.put(2, "D");
        doubleHashing.put(2, "D");

        assertEquals("A", linear.get(1));
        assertEquals("A", quadratic.get(1));
        assertEquals("A", doubleHashing.get(1));

        assertEquals("D", linear.get(2));
        assertEquals("D", quadratic.get(2));
        assertEquals("D", doubleHashing.get(2));

        assertEquals("C", linear.get(11));
        assertEquals("C", quadratic.get(11));
        assertEquals("C", doubleHashing.get(11));

        assertEquals(3, linear.size());
        assertEquals(3, quadratic.size());
        assertEquals(3, doubleHashing.size());
    }

}
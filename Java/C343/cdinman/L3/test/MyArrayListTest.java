import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    @Test
    void sizeTest() {
        MyArrayList<Integer> al = new MyArrayList<>();
        assertEquals(0, al.size(), "new list should have size 0");

        assertTrue(al.add(10));
        assertTrue(al.add(20));
        assertTrue(al.add(30));
        assertEquals(3, al.size(), "size should reflect number of adds");

        Integer removed = al.remove(1); // remove 20
        assertEquals(20, removed);
        assertEquals(2, al.size(), "size should decrement after remove");
    }

    @Test
    void isEmptyTest() {
        MyArrayList<String> al = new MyArrayList<>();
        assertTrue(al.isEmpty(), "new list should be empty");

        al.add("a");
        assertFalse(al.isEmpty(), "list with one element should not be empty");

        al.remove(0);
        assertTrue(al.isEmpty(), "list should be empty after removing the only element");
    }

    @Test
    void getTest() {
        MyArrayList<String> al = new MyArrayList<>();
        al.add("a");
        al.add("b");
        al.add("c");

        assertEquals("a", al.get(0));
        assertEquals("b", al.get(1));
        assertEquals("c", al.get(2));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> al.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> al.get(3));
    }

    @Test
    void setTest() {
        MyArrayList<Integer> al = new MyArrayList<>();
        al.add(1);
        al.add(2);
        al.add(3);

        int old = al.set(1, 42);
        assertEquals(2, old, "set should return the old value");
        assertEquals(42, al.get(1), "value at index should be updated");
        assertEquals(3, al.size(), "set should not change size");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> al.set(-1, 7));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> al.set(3, 7));
    }

    @Test
    void resizeTest() {
        MyArrayList<Integer> al = new MyArrayList<>();
        // Add well beyond the default capacity to trigger internal resize(s).
        int n = 100;
        for (int i = 0; i < n; i++) {
            assertTrue(al.add(i));
        }
        assertEquals(n, al.size());
        // Verify contents survived resizes.
        for (int i = 0; i < n; i++) {
            assertEquals(i, al.get(i));
        }
    }

    @Test
    void addTest() {
        MyArrayList<String> al = new MyArrayList<>();
        assertTrue(al.add("x"));
        assertTrue(al.add("y"));
        assertTrue(al.add("z"));
        assertEquals(3, al.size());
        assertEquals("x", al.get(0));
        assertEquals("y", al.get(1));
        assertEquals("z", al.get(2));
    }

    @Test
    void addIdxTest() {
        MyArrayList<String> al = new MyArrayList<>();
        // Start with [A, C]
        al.add("A");
        al.add("C");
        // Insert at middle: index 1 -> [A, B, C]
        al.add(1, "B");
        assertEquals(3, al.size());
        assertEquals("A", al.get(0));
        assertEquals("B", al.get(1));
        assertEquals("C", al.get(2));

        // Insert at beginning: index 0 -> [X, A, B, C]
        al.add(0, "X");
        assertEquals(4, al.size());
        assertEquals("X", al.get(0));
        assertEquals("A", al.get(1));

        // Insert at end: index == size() -> [X, A, B, C, Y]
        al.add(al.size(), "Y");
        assertEquals(5, al.size());
        assertEquals("Y", al.get(4));
    }

    @Test
    void removeTest() {
        MyArrayList<Integer> al = new MyArrayList<>();
        // [10, 20, 30, 40]
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);

        // Remove middle element (index 1: 20) -> [10, 30, 40]
        int r1 = al.remove(1);
        assertEquals(20, r1);
        assertEquals(3, al.size());
        assertEquals(10, al.get(0));
        assertEquals(30, al.get(1));
        assertEquals(40, al.get(2));

        // Remove first -> [30, 40]
        int r2 = al.remove(0);
        assertEquals(10, r2);
        assertEquals(2, al.size());
        assertEquals(30, al.get(0));
        assertEquals(40, al.get(1));

        // Remove last -> [30]
        int r3 = al.remove(1);
        assertEquals(40, r3);
        assertEquals(1, al.size());
        assertEquals(30, al.get(0));

        // Now only one element remains; removing it should leave size 0
        int r4 = al.remove(0);
        assertEquals(30, r4);
        assertEquals(0, al.size());
        assertTrue(al.isEmpty());
    }

    @Test
    void iteratorTest() {
        MyArrayList<String> al = new MyArrayList<>();
        al.add("a");
        al.add("b");
        al.add("c");

        var it = al.iterator();

        // Basic forward iteration
        assertTrue(it.hasNext());
        assertEquals("a", it.next());
        assertTrue(it.hasNext());
        assertEquals("b", it.next());
        assertTrue(it.hasNext());
        assertEquals("c", it.next());
        assertFalse(it.hasNext());

        // next() past end should throw
        assertThrows(NoSuchElementException.class, it::next);

        // Test iterator remove(): remove the second element in a fresh iteration
        al = new MyArrayList<>();
        al.add("a");
        al.add("b");
        al.add("c"); // [a, b, c]

        it = al.iterator();
        assertEquals("a", it.next());   // now okToRemove = true, current points to index 1
        it.remove();                    // remove "a" -> [b, c]
        assertEquals(2, al.size());
        assertEquals("b", al.get(0));
        assertEquals("c", al.get(1));

        // Calling remove() twice in a row should throw IllegalStateException
        assertThrows(IllegalStateException.class, it::remove);

        // Remove after moving to next again is allowed
        assertEquals("b", it.next());   // returns current index 1 value after prior removal shift
        it.remove();                    // remove "b" -> [c]
        assertEquals(1, al.size());
        assertEquals("c", al.get(0));

        // Remove before any next() should throw
        it = al.iterator();
        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    void toStringTest() {
        MyArrayList<Integer> al = new MyArrayList<>();
        assertEquals("[ ]", al.toString(), "empty list formatting");

        al.add(1);
        al.add(2);
        al.add(3);
        assertEquals("[ 1 2 3 ]", al.toString(), "format should be bracket-space, items with spaces, space-bracket");
    }
}

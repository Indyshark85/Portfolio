import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class Test {
    static class TODO extends RuntimeException {} // TODO: remove this line when you're done

    @Nested
    class LinkedListTest {
        @org.junit.jupiter.api.Test
        void sizeTest() {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            assertEquals(0, list.size());
            list.addFirst(1);
            list.addLast(2);
            assertEquals(2, list.size());
            list.removeFirst();
            assertEquals(1, list.size());
        }

        @org.junit.jupiter.api.Test
        void isEmptyTest() {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            assertTrue(list.isEmpty());
            list.addFirst(42);
            assertFalse(list.isEmpty());
            list.removeFirst();
            assertTrue(list.isEmpty());
        }

        @org.junit.jupiter.api.Test
        void getTest() {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);
            assertEquals(1, list.get(0));
            assertEquals(2, list.get(1));
            assertEquals(3, list.get(2));
        }

        @org.junit.jupiter.api.Test
        void addFirstTest() {
            throw new TODO();
        }

        @org.junit.jupiter.api.Test
        void addLastTest() {
            throw new TODO();
        }

        @org.junit.jupiter.api.Test
        void removeFirstTest() {
            throw new TODO();
        }

    }

    @Nested
    class StackTest{
        @org.junit.jupiter.api.Test
        void isEmptyTest() {
            LinkedStack<String> s = new LinkedStack<>();
            assertTrue(s.isEmpty());
            s.push("x");
            assertFalse(s.isEmpty());
            s.pop();
            assertTrue(s.isEmpty());
        }

        @org.junit.jupiter.api.Test
        void pushTest() {
            throw new TODO();
        }

        @org.junit.jupiter.api.Test
        void popTest() {
            throw new TODO();
        }
    }

    @Nested
    class QueueTest {
        @org.junit.jupiter.api.Test
        void isEmptyTest() {
            LinkedQueue<Integer> q = new LinkedQueue<>();
            assertTrue(q.isEmpty());
            q.enqueue(10);
            assertFalse(q.isEmpty());
            q.dequeue();
            assertTrue(q.isEmpty());
        }

        @org.junit.jupiter.api.Test
        void enqueueTest() {
            throw new TODO();
        }

        @org.junit.jupiter.api.Test
        void dequeueTest() {
            throw new TODO();
        }
    }
}
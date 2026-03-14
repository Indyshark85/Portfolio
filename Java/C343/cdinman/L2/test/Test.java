import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class Test {

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
            SinglyLinkedList<String> list = new SinglyLinkedList<>();
            list.addFirst("C");
            list.addFirst("B");
            list.addFirst("A");

            org.junit.jupiter.api.Assertions.assertEquals(3, list.size());
            org.junit.jupiter.api.Assertions.assertEquals("A", list.get(0));
            org.junit.jupiter.api.Assertions.assertEquals("B", list.get(1));
            org.junit.jupiter.api.Assertions.assertEquals("C", list.get(2));
        }

        @org.junit.jupiter.api.Test
        void addLastTest() {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            org.junit.jupiter.api.Assertions.assertEquals(3, list.size());
            org.junit.jupiter.api.Assertions.assertEquals(1, list.get(0));
            org.junit.jupiter.api.Assertions.assertEquals(2, list.get(1));
            org.junit.jupiter.api.Assertions.assertEquals(3, list.get(2));
        }

    }

        @org.junit.jupiter.api.Test
        void removeFirstTest() {
            SinglyLinkedList<String> list = new SinglyLinkedList<>();
            list.addLast("X");
            list.addLast("Y");
            list.addLast("Z");

            String removed = list.removeFirst();

            org.junit.jupiter.api.Assertions.assertEquals("X", removed);
            org.junit.jupiter.api.Assertions.assertEquals(2, list.size());
            org.junit.jupiter.api.Assertions.assertEquals("Y", list.get(0));
            org.junit.jupiter.api.Assertions.assertEquals("Z", list.get(1));

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
            LinkedStack<String> stack = new LinkedStack<>();
            stack.push("A");
            stack.push("B");
            stack.push("C");

            // Stack should now be: top → "C", "B", "A"
            org.junit.jupiter.api.Assertions.assertFalse(stack.isEmpty());
            org.junit.jupiter.api.Assertions.assertEquals("C", stack.pop());
            org.junit.jupiter.api.Assertions.assertEquals("B", stack.pop());
            org.junit.jupiter.api.Assertions.assertEquals("A", stack.pop());

        }

        @org.junit.jupiter.api.Test
        void popTest() {
            LinkedStack<Integer> stack = new LinkedStack<>();
            stack.push(10);
            stack.push(20);

            int val1 = stack.pop(); // should be 20
            int val2 = stack.pop(); // should be 10

            org.junit.jupiter.api.Assertions.assertEquals(20, val1);
            org.junit.jupiter.api.Assertions.assertEquals(10, val2);
            org.junit.jupiter.api.Assertions.assertTrue(stack.isEmpty());

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
            LinkedQueue<String> queue = new LinkedQueue<>();
            queue.enqueue("Alpha");
            queue.enqueue("Beta");
            queue.enqueue("Gamma");

            // Check order using dequeue
            org.junit.jupiter.api.Assertions.assertEquals("Alpha", queue.dequeue());
            org.junit.jupiter.api.Assertions.assertEquals("Beta", queue.dequeue());
            org.junit.jupiter.api.Assertions.assertEquals("Gamma", queue.dequeue());
        }

        @org.junit.jupiter.api.Test
        void dequeueTest() {
            LinkedQueue<Integer> queue = new LinkedQueue<>();
            queue.enqueue(100);
            queue.enqueue(200);

            int first = queue.dequeue();
            int second = queue.dequeue();

            org.junit.jupiter.api.Assertions.assertEquals(100, first);
            org.junit.jupiter.api.Assertions.assertEquals(200, second);
            org.junit.jupiter.api.Assertions.assertTrue(queue.isEmpty());
        }
    }
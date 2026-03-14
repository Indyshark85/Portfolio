import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PQueueTest {

    @Test
    void testEquals() {
        PQueue<String> pq = new PQueue<>();
        PQueue<Integer> pq2 = new PQueue<>();
        assertEquals(pq, pq2);
    }

    @Test
    void enqueueTests() {
        System.out.println("WARNING: Your of() method MUST WORK FULLY in order for these tests to work.");
        PQueue<String> q1 = PQueue.of("A", "B", "C", "D");
        PQueue<String> q2 = PQueue.of("A", "B", "C", "D", "E");
        PQueue<String> q3 = PQueue.of("A", "B", "C", "D", "hello");
        PQueue<String> q4 = PQueue.of("A", "B", "C", "D", "E", "F");
        PQueue<String> q5 = new PQueue<>();
        PQueue<String> q6 = PQueue.of("a");

        assertEquals(q2, q1.enqueue("E"));
        assertEquals(q3, q1.enqueue("hello"));
        assertEquals(q4, q1.enqueue("E").enqueue("F"));
        assertEquals(q6, q5.enqueue("a"));
        assertEquals(new PQueue<>(), q5);
    }

    @Test
    void dequeueTests() {
        System.out.println("WARNING: Your of() method MUST WORK FULLY in order for these tests to work.");
        PQueue<String> q1 = PQueue.of("A", "B", "C", "D");
        PQueue<String> q2 = PQueue.of("B", "C", "D");
        PQueue<String> q3 = PQueue.of("C", "D");
        PQueue<String> q5 = new PQueue<>();
        PQueue<String> q6 = PQueue.of("a");

        assertEquals(q2, q1.dequeue());
        assertEquals(q3, q1.dequeue().dequeue());
        assertEquals(q5, q5.dequeue());
        assertEquals(new PQueue<String>(), q5.dequeue());
        assertEquals(q5, q6.dequeue());
        assertEquals(PQueue.of("B", "C", "D", "E"),
                     new PQueue<>().enqueue("A").enqueue("B").enqueue("C").enqueue("D").dequeue().enqueue("E"));
    }

    @Test
    void peekTests() {
        System.out.println("WARNING: Your of() method MUST WORK FULLY in order for these tests to work.");
        PQueue<String> q1 = new PQueue<>();
        PQueue<String> q2 = PQueue.of("A", "B");
        PQueue<Integer> q3 = PQueue.of(2, 3);

        assertNull(q1.peek());
        assertEquals("A", q2.peek());
        assertEquals(2, q3.peek());
    }

    @Test
    void ofTests() {
        PQueue<String> q1 = new PQueue<>();
        PQueue<String> q2 = PQueue.of();
        PQueue<String> q3 = PQueue.of("A");
        PQueue<String> q4 = PQueue.of("A", "B");

        assertEquals(q1, q2);
        assertEquals(q3, q1.enqueue("A"));
        assertEquals(q4, q1.enqueue("A").enqueue("B"));
    }

    @Test
    void sizeTests() {
        System.out.println("WARNING: Your of() method MUST WORK FULLY in order for these tests to work.");
        PQueue<String> q1 = PQueue.of("A", "B", "C", "D");
        PQueue<String> q2 = new PQueue<>();

        assertEquals(4, q1.size());
        assertEquals(0, q2.size());
        assertEquals(3, q1.dequeue().size());
        assertEquals(5, q1.enqueue("F").size());
    }

    @Test
    void fuzzTest() {
        // Test a shitton of random operations.
        Random r = new Random(212240407);
        PQueue<Integer> pQueue = new PQueue<>();
        PQueue<Integer> studentPqueue = new PQueue<>();
        for (int i = 0; i < 100000; i++) {
            int op = r.nextInt(4);
            if (op == 0) {
                int val = r.nextInt();
                pQueue = pQueue.enqueue(val);
                studentPqueue = studentPqueue.enqueue(val);
            } else if (op == 1) {
                pQueue = pQueue.dequeue();
                studentPqueue = studentPqueue.dequeue();
            } else if (op == 2) {
                assertEquals(pQueue.peek(), studentPqueue.peek());
            } else {
                assertEquals(pQueue.size(), studentPqueue.size());
            }
        }
    }
}
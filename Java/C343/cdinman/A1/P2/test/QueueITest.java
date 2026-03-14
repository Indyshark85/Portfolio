import exceptions.EmptyQueueE;
import interfaces.QueueI;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueITest {

    private long timeIt (@NotNull Runnable r) {
        long startTime = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - startTime;
    }

    private @NotNull Runnable makeRunnable (@NotNull QueueI<Integer> queue, int n) {
        return () -> {
            try {
                for (int i = 0; i < n; i++) queue.enqueue(i);
                for (int i = 0; i < n; i++) queue.dequeue();
            }
            catch (EmptyQueueE ex) {
                throw new Error("Internal bug: EmptyQueueE should not be thrown in this test");
            }
        };
    }

    @Test
    void testSimple () {
        QueuePL<Integer> queuePL = new QueuePL<>();
        QueueTwoStacks<Integer> queueTwoStacks = new QueueTwoStacks<>();
        int n = 10;

        for (int i = 0; i < n; i++) {
            queuePL.enqueue(i);
            queueTwoStacks.enqueue(i);
        }

        for (int i = 0; i < n; i++) {
            try {
                assertEquals(i, queuePL.dequeue());
                assertEquals(i, queueTwoStacks.dequeue());
            }
            catch (EmptyQueueE ex) {
                throw new Error("Internal bug: EmptyQueueE should not be thrown in this test");
            }
        }
    }

    @Test
    void testSpeed () {
        QueuePL<Integer> queuePL = new QueuePL<>();
        QueueTwoStacks<Integer> queueTwoStacks = new QueueTwoStacks<>();
        int n = 10000;

        long timePL = timeIt(makeRunnable(queuePL, n));
        long timeTwoStacks = timeIt(makeRunnable(queueTwoStacks, n));

        System.out.println("Time for QueuePL: " + timePL);
        System.out.println("Time for QueueTwoStacks: " + timeTwoStacks);
    }

    @Test
    void testInterleavedOps() throws EmptyQueueE {
        QueueI<Integer> queue = new QueueTwoStacks<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        queue.enqueue(3);
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.peek());
    }

    @Test
    void testEmptyQueueException() {
        QueueI<Integer> queue = new QueuePL<>();
        assertThrows(EmptyQueueE.class, queue::dequeue);
        assertThrows(EmptyQueueE.class, queue::peek);
    }

    @Test
    void testLargeVolume() {
        QueueI<Integer> queue = new QueueTwoStacks<>();
        int n = 100_000;
        for (int i = 0; i < n; i++) queue.enqueue(i);
        for (int i = 0; i < n; i++) {
            try {
                assertEquals(i, queue.dequeue());
            } catch (EmptyQueueE ex) {
                fail("Unexpected EmptyQueueE at index " + i);
            }
        }
    }

}
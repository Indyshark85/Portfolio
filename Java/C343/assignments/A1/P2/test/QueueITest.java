import exceptions.EmptyQueueE;
import interfaces.QueueI;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
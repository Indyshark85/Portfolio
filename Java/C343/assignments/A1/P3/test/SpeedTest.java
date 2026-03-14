import dynamicArray.DequeueA;
import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import pointers.DequeueP;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpeedTest {
    private long timeIt(@NotNull Runnable r) {
        long startTime = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - startTime;
    }

    private @NotNull Runnable makeRunnable(@NotNull DequeueI<Integer> q, int n) {
        return () -> {
            try {
                for (int i = 0; i < n; i++) q.enqueueFront(i);
                for (int i = 0; i < n; i++) q.dequeueBack();
            } catch (EmptyDequeueE e) {
                throw new Error("Internal bug: EmptyQueueE should not be thrown in this test");
            }
        };
    }

    @Test
    void compare() {
        DequeueI<Integer> qa, qp, qll, qad, qal;

        qa = new DequeueA<>(100);
        qp = new DequeueP<>();
        qad = new JavaAD<>();
        qll = new JavaLL<>();
        qal = new JavaAL<>();

        int n = 90000;

        long timea = timeIt(makeRunnable(qa, n));
        long timep = timeIt(makeRunnable(qp, n));
        long timead = timeIt(makeRunnable(qad, n));
        long timell = timeIt(makeRunnable(qll, n));
        long timeal = timeIt(makeRunnable(qal, n));

        System.out.println("Time for Java LinkedList: " + timell);
        System.out.println("Time for Java ArrayDeque: " + timead);
        System.out.println("Time for QueueP: " + timep);
        System.out.println("Time for QueueA: " + timea);
        System.out.println("Time for Java ArrayList: " + timeal);

        assertTrue(qa.isEmpty());
        assertTrue(qp.isEmpty());
        assertTrue(qll.isEmpty());
        assertTrue(qad.isEmpty());
        assertTrue(qal.isEmpty());
    }
}


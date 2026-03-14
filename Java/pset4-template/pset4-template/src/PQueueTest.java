import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PQueueTest {

  @Test
  void testPQueue() {
    PQueue<Integer> emptyQueue = new PQueue<>();
    PQueue<Integer> queue = PQueue.of(1, 2, 3);
    PQueue<Integer> updatedQueue = queue.enqueue(4);
    PQueue<Integer> dequeuedQueue = queue.dequeue();
    PQueue<Integer> queue2 = PQueue.of(1, 2, 3);
    PQueue<Integer> queue3 = PQueue.of(1, 2);

    assertAll(
            () -> assertEquals(0, emptyQueue.size()),
            () -> assertThrows(null, emptyQueue::peek), //updated for new coding I implemented
            () -> assertEquals(0, emptyQueue.dequeue().size()),
            () -> assertEquals(3, queue.size()),
            () -> assertEquals(4, updatedQueue.size()),
            () -> assertEquals(1, updatedQueue.peek()),
            () -> assertEquals(3, queue.size()),
            () -> assertEquals(2, dequeuedQueue.size()),
            () -> assertEquals(2, dequeuedQueue.peek()),
            () -> assertEquals(queue, queue2),
            () -> assertNotEquals(queue, queue3),
            () -> assertNotEquals(queue, null),
            () -> assertNotEquals(queue, "Not a queue"),
            () -> assertEquals(3, queue.size()),
            () -> assertEquals(1, queue.peek())
    );
  }
}
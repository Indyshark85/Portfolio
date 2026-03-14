package interfaces;

import exceptions.EmptyQueueE;

/**
 * QueueI is an interface that defines the methods that a queue must implement.
 *
 * @param <E> the type of elements in the queue
 */
public interface QueueI<E> {
    boolean isEmpty();
    int size();
    void enqueue(E e);
    E dequeue() throws EmptyQueueE;
    E peek() throws EmptyQueueE;
}

import exceptions.EmptyListE;
import exceptions.EmptyQueueE;
import interfaces.QueueI;
import org.jetbrains.annotations.NotNull;

/**
 * QueuePL is a class that represents a queue implemented using a persistent linked list.
 * The correct queue behavior requires insertions at one end and deletions at the other.
 * Unfortunately, one of these operations is O(n) in a singly linked list. Thus, although
 * the implementation is correct, it is not efficient.
 *
 * @param <E> the type of elements in the queue
 */

public class QueuePL<E> implements QueueI<E> {
    private @NotNull PersistentLL<E> list;

    public QueuePL() {
        list = new EmptyPL<>();
    }

    /**
     * Check if the queue is empty.
     */
    public boolean isEmpty() {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Get the number of elements in the queue.
     */
    public int size () {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Enqueue an element into the queue. This operation should always succeed.
     * However, the underlying list operation is declared to possibly throw an
     * EmptyListE exception. Catch this exception and throw a fatal error if it
     * is thrown.
     */
    public void enqueue(@NotNull E e) {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Dequeue an element from the queue. If the underlying list is empty, catch
     * the EmptyListE exception and throw an EmptyQueueE exception instead. This
     * way, if we ever change the implementation of the queue to use a different
     * data structure, we can still throw the same exception.
     */
    public @NotNull E dequeue() throws EmptyQueueE {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Peek at the element at the front of the queue. If the underlying list is empty,
     * catch the EmptyListE exception and throw an EmptyQueueE exception instead.
     */
    public @NotNull E peek() throws EmptyQueueE {
        throw new Error("TODO: Method not implemented");
    }
}

package pointers;

import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;

/**
 * DequeueP is a double-ended queue implemented with pointers.
 * The front sentinel points to the first element, and the back
 * sentinel points to the last element. The front sentinel is always
 * before the first element, and the back sentinel is always after
 * the last element.
 */

public class DequeueP<E> implements DequeueI<E> {
    private final @NotNull FrontSentinel<E> frontSentinel;
    private final @NotNull BackSentinel<E> backSentinel;
    private int size;

    public DequeueP() {
        size = 0;
        frontSentinel = new FrontSentinel<>();
        backSentinel = new BackSentinel<>();
        frontSentinel.setNext(backSentinel);
        backSentinel.setPrev(frontSentinel);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Adds an element to the front of the dequeue. The new node
     * will be inserted between the front sentinel and the first element:
     * its next pointer will point to the first element, and its previous
     * pointer will point to the front sentinel. Remember to update the
     * pointers of the front sentinel and the first element and to
     * increment the size.
     *
     * Initially: FRONT <=> N
     *
     * new node [.FRONT.,e,.N.]
     */
    public void enqueueFront(@NotNull E e) {
        AbstractNode<E> newNode = new NodeP<>(e, frontSentinel.getNext(), frontSentinel);
        frontSentinel.getNext().setPrev(newNode);
        frontSentinel.setNext(newNode);
        size++;
    }

    /**
     * Adds an element to the back of the dequeue. The new node
     * will be inserted between the back sentinel and the last element:
     * its next pointer will point to the back sentinel, and its previous
     * pointer will point to the last element. Remember to update the
     * pointers of the back sentinel and the last element and to
     * increment the size.
     */
    public void enqueueBack(@NotNull E e) {
        throw new Error("TODO");
    }

    /**
     * Removes and returns the element at the front of the dequeue.
     * Remember to update the pointers of the front sentinel and the
     * first element and to decrement the size. If the dequeue is empty,
     * throw an EmptyDequeueE exception.
     */
    public @NotNull E dequeueFront() throws EmptyDequeueE {
        throw new Error("TODO");
    }

    /**
     * Removes and returns the element at the back of the dequeue.
     * Remember to update the pointers of the back sentinel and the
     * last element and to decrement the size. If the dequeue is empty,
     * throw an EmptyDequeueE exception.
     */
    public @NotNull E dequeueBack() throws EmptyDequeueE {
        throw new Error("TODO");
    }

    /**
     * Returns the element at the front of the dequeue without removing it.
     * If the dequeue is empty, throw an EmptyDequeueE exception.
     */
    public @NotNull E peekFront() throws EmptyDequeueE {
        throw new Error("TODO");
    }

    /**
     * Returns the element at the back of the dequeue without removing it.
     * If the dequeue is empty, throw an EmptyDequeueE exception.
     */
    public @NotNull E peekBack() throws EmptyDequeueE {
        throw new Error("TODO");
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("F[");
            AbstractNode<E> current = frontSentinel.getNext();
            while (current != backSentinel) {
                sb.append(current.getData());
                if (current.getNext() != backSentinel) sb.append(", ");
                current = current.getNext();
            }
            sb.append("]B");
            return sb.toString();
        }
        catch (EmptyDequeueE e) {
            throw new Error("Internal bug in printing dequeue: This should never happen");
        }
    }
}


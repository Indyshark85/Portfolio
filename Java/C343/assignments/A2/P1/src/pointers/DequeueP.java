package pointers;

import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;

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

    public void enqueueFront(@NotNull E e) {
        NodeP<E> newFirst = new NodeP<>(e, frontSentinel.getNext(), frontSentinel);
        frontSentinel.getNext().setPrev(newFirst);
        frontSentinel.setNext(newFirst);
        size++;
    }

    public void enqueueBack(@NotNull E e) {
        NodeP<E> newLast = new NodeP<>(e, backSentinel, backSentinel.getPrev());
        backSentinel.getPrev().setNext(newLast);
        backSentinel.setPrev(newLast);
        size++;
    }

    public @NotNull E dequeueFront() throws EmptyDequeueE {
        AbstractNode<E> oldFirst = frontSentinel.getNext();
        AbstractNode<E> newFirst = oldFirst.getNext();
        frontSentinel.setNext(newFirst);
        newFirst.setPrev(frontSentinel);
        size--;
        return oldFirst.getData();
    }

    public @NotNull E dequeueBack() throws EmptyDequeueE {
        AbstractNode<E> oldBack = backSentinel.getPrev();
        AbstractNode<E> newBack = oldBack.getPrev();
        backSentinel.setPrev(newBack);
        newBack.setNext(backSentinel);
        size--;
        return oldBack.getData();
    }

    public @NotNull E peekFront() throws EmptyDequeueE {
        return frontSentinel.getNext().getData();
    }

    public @NotNull E peekBack() throws EmptyDequeueE {
        return backSentinel.getPrev().getData();
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

    /**
     * Below we will implement equals / hashCode. The main contract that needs
     * to be followed is that if two objects are equal according to the equals
     * method, then calling hashCode on each of the two objects must produce
     * the same integer result.
     * <p>
     * The method equals will return true if and only if the two dequeues
     * have the same size and the same elements in the same order.
     */

    public boolean equals(Object other) {
        throw new Error("TODO: implement");
    }

    public int hashCode() {
        throw new Error("TODO: implement");
    }
}


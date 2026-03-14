import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

// An implementation of the dequeue interface using Java's LinkedList

public class JavaLL<E> implements DequeueI<E> {
    private @NotNull final LinkedList<E> list;

    public JavaLL() {
        list = new LinkedList<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void enqueueFront(@NotNull E e) {
        list.addFirst(e);
    }

    public void enqueueBack(@NotNull E e) {
        list.addLast(e);
    }

    public @NotNull E dequeueFront() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();
        return list.removeFirst();
    }

    public @NotNull E dequeueBack() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();
        return list.removeLast();
    }

    public @NotNull E peekFront() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();
        return list.getFirst();
    }

    public @NotNull E peekBack() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();
        return list.getLast();
    }
}

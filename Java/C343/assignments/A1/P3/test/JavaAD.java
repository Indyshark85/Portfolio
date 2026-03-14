import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

// An implementation of the dequeue interface using Java's ArrayDeque

public class JavaAD<E> implements DequeueI<E> {
    private @NotNull final ArrayDeque<E> list;

    public JavaAD() {
        list = new ArrayDeque<>();
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

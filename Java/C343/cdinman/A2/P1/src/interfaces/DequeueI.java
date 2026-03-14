package interfaces;

import exceptions.EmptyDequeueE;
import org.jetbrains.annotations.NotNull;

public interface DequeueI<E> {
    boolean isEmpty();
    int size();
    void enqueueFront(@NotNull E e);
    void enqueueBack(@NotNull E e);
    @NotNull E dequeueFront () throws EmptyDequeueE;
    @NotNull E dequeueBack() throws EmptyDequeueE;
    @NotNull E peekFront() throws EmptyDequeueE;
    @NotNull E peekBack() throws EmptyDequeueE;

}

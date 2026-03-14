package dynamicArray;

import exceptions.EmptyDequeueE;
import org.jetbrains.annotations.NotNull;

public abstract class Node<E> {
    public abstract boolean isEmpty();
    public abstract @NotNull E getValue() throws EmptyDequeueE;
}

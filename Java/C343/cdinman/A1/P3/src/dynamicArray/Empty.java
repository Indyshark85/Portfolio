package dynamicArray;

import exceptions.EmptyDequeueE;
import org.jetbrains.annotations.NotNull;

public class Empty<E> extends Node<E> {

    public boolean isEmpty() { return true; }

    public @NotNull E getValue() throws EmptyDequeueE {
        throw new EmptyDequeueE();
    }

}

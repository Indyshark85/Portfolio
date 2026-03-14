package pointers;

import exceptions.EmptyDequeueE;
import org.jetbrains.annotations.NotNull;

public class BackSentinel<E> extends AbstractNode<E> {
    private AbstractNode<E> prev;

    public void setPrev(@NotNull AbstractNode<E> prev) {
        this.prev = prev;
    }

    public @NotNull E getData() throws EmptyDequeueE {
        throw new EmptyDequeueE();
    }

    public @NotNull AbstractNode<E> getNext() throws EmptyDequeueE {
        throw new EmptyDequeueE();
    }

    public @NotNull AbstractNode<E> getPrev() {
        return prev;
    }

    public String toString() { return "-|"; }
}

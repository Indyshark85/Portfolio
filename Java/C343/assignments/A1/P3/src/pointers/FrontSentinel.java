package pointers;

import exceptions.EmptyDequeueE;
import org.jetbrains.annotations.NotNull;

public class FrontSentinel<E> extends AbstractNode<E> {
    private AbstractNode<E> next;

    public void setNext(@NotNull AbstractNode<E> next) {
        this.next = next;
    }

    public @NotNull E getData() throws EmptyDequeueE {
        throw new EmptyDequeueE();
    }

    public @NotNull AbstractNode<E> getNext() {
        return next;
    }

    public @NotNull AbstractNode<E> getPrev() throws EmptyDequeueE {
        throw new EmptyDequeueE();
    }

    public String toString() { return "|-"; }
}

package pointers;

import exceptions.EmptyDequeueE;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractNode<E> {
    public void setNext(@NotNull AbstractNode<E> next) {}
    public void setPrev(@NotNull AbstractNode<E> prev) {}
    public abstract @NotNull E getData() throws EmptyDequeueE;
    public abstract @NotNull AbstractNode<E> getNext() throws EmptyDequeueE;
    public abstract @NotNull AbstractNode<E> getPrev() throws EmptyDequeueE;
}

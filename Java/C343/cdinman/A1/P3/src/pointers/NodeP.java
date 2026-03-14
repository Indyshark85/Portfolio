package pointers;

import org.jetbrains.annotations.NotNull;

public class NodeP<E> extends AbstractNode<E> {
    private @NotNull final E data;
    private @NotNull AbstractNode<E> next;
    private @NotNull AbstractNode<E> prev;

    public NodeP(@NotNull E data, @NotNull AbstractNode<E> next, @NotNull AbstractNode<E> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public void setNext(@NotNull AbstractNode<E> next) { this.next = next; }

    public void setPrev(@NotNull AbstractNode<E> prev) { this.prev = prev; }

    public @NotNull E getData() { return data; }

    public @NotNull AbstractNode<E> getNext() { return next; }

    public @NotNull AbstractNode<E> getPrev() { return prev; }

    public String toString() { return data.toString(); }
}

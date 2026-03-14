import exceptions.EmptyListE;
import org.jetbrains.annotations.NotNull;

/**
 * NodePL is a class that represents a non-empty node of persistent linked list.
 * Each node has a data element and a reference to the next node in the list.
 *
 * @param <E> the type of elements in the list
 */
public class NodePL<E> extends PersistentLL<E> {
    private final @NotNull E data;
    private final @NotNull PersistentLL<E> next;
    private final int len;

    NodePL(@NotNull E data, @NotNull PersistentLL<E> next) {
        this.data = data;
        this.next = next;
        this.len = next.length() + 1;
    }

    public int length() { return len; }

    public boolean isEmpty() { return false; }

    public boolean find(@NotNull E e) {
        return data.equals(e) || next.find(e);
    }

    public @NotNull E get(int index) throws EmptyListE {
        if (index == 0) return data;
        else return next.get(index - 1);
    }

    public @NotNull PersistentLL<E> insert(@NotNull E e, int index) throws EmptyListE {
        if (index == 0) return new NodePL<>(e, this);
        else return new NodePL<>(data, next.insert(e, index - 1));
    }

    public @NotNull PersistentLL<E> delete(int index) throws EmptyListE {
        if (index == 0) return next;
        else return new NodePL<>(data, next.delete(index - 1));
    }

    public @NotNull String toString() {
        return data + " , " + next;
    }
}

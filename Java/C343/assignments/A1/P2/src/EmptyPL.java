import exceptions.EmptyListE;
import org.jetbrains.annotations.NotNull;

/**
 * EmptyPL is a class that represents an empty persistent linked list.
 *
 * @param <E> the type of elements in the list
 */
public class EmptyPL<E> extends PersistentLL<E> {

    public int length() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean find(@NotNull E e) {
        return false;
    }

    public @NotNull E get(int index) throws EmptyListE {
        throw new EmptyListE();
    }

    public @NotNull PersistentLL<E> insert(@NotNull E e, int index) throws EmptyListE {
        if (index == 0) return new NodePL<>(e, new EmptyPL<>());
        else throw new EmptyListE();
    }

    public @NotNull PersistentLL<E> delete(int index) throws EmptyListE {
        throw new EmptyListE();
    }

    public @NotNull String toString() {
        return "*";
    }
}

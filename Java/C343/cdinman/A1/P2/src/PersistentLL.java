import exceptions.EmptyListE;
import org.jetbrains.annotations.NotNull;

/**
 * PersistentLL is an abstract class that defines the methods that a
 * persistent linked list must implement. It is a slightly extended
 * version of the code presented in class.
 *
 * @param <E> the type of elements in the list
 */
public abstract class PersistentLL<E> {

    /**
     * A convenient way to create short lists by enumerating the elements.
     */
    @SafeVarargs
    public static <E> @NotNull PersistentLL<E> makeList(E @NotNull ... es) {
        PersistentLL<E> list = new EmptyPL<>();
        try {
            for (E e : es) {
                list = list.insert(e, list.length());
            }
        }
        catch (EmptyListE ex) {
            throw new Error("Internal bug: Exceptions.EmptyListE should not be thrown here");
        }
        return list;
    }

    /*
     * Abstract methods that must be implemented by subclasses.
     */
    public abstract int length ();
    public abstract boolean isEmpty ();
    public abstract boolean find (@NotNull E e);
    public abstract @NotNull E get (int index) throws EmptyListE;
    public abstract @NotNull PersistentLL<E> insert (@NotNull E e, int index) throws EmptyListE;
    public abstract @NotNull PersistentLL<E> delete (int index) throws EmptyListE;
}

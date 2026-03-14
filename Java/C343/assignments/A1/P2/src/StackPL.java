import exceptions.EmptyListE;
import exceptions.EmptyStackE;
import interfaces.StackI;
import org.jetbrains.annotations.NotNull;

/**
 * StackPL is a class that implements a stack backed by a persistent linked list.
 * Since accessing the element at index 0 in a linked list is O(1), the natural
 * choice for implementing push and pop is to always operate at index 0 of the list.
 * @param <E>
 */
public class StackPL<E> implements StackI<E> {
    private PersistentLL<E> list;

    public StackPL() {
        list = new EmptyPL<>();
    }

    /**
     * Check if the stack is empty.
     */
    public boolean isEmpty() {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Get the number of elements in the stack.
     */
    public int size() {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Push an element onto the stack.
     * <p>
     * Note that the insert method in PersistentLL might
     * throw an EmptyListE exception if the given index is beyond the
     * end of the list. This exception is not allowed to appear in
     * the interface of push. The reason is that inserting at index 0
     * should never fail.
     * <p>
     * Of course the compiler does not enforce this, so you should write
     * a catch block to catch the exception that should never happen and throw
     * a fatal error reporting a bug in the implementation if it does happen.
     * </p>
     */
    public void push(@NotNull E e) {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Pop an element from the stack. Here if the underlying list
     * is empty, we should catch the EmptyListE exception and throw
     * an EmptyStackE exception instead. That way if we every
     * change the implementation of the stack to use a different
     * data structure, we can still throw the same exception.
     */
    public @NotNull E pop() throws EmptyStackE {
        throw new Error("TODO: Method not implemented");
    }

    /**
     * Peek at the top element of the stack. Here if the underlying list
     * is empty, we should catch the EmptyListE exception and throw
     * an EmptyStackE exception instead.
     */
    public @NotNull E peek() throws EmptyStackE {
        throw new Error("TODO: Method not implemented");
    }
}

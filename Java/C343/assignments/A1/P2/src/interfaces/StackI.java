package interfaces;

import exceptions.EmptyStackE;
import org.jetbrains.annotations.NotNull;

/**
 * StackI is an interface that defines the methods that a stack must implement.
 *
 * @param <E> the type of elements in the stack
 */
public interface StackI<E> {
    boolean isEmpty();
    int size ();
    void push(@NotNull E e);
    E pop() throws EmptyStackE;
    E peek() throws EmptyStackE;
}

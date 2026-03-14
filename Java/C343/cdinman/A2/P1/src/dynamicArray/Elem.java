package dynamicArray;

import org.jetbrains.annotations.NotNull;

public class Elem<E> extends Node<E> {
    private @NotNull final E value;

    public Elem(@NotNull E value) { this.value = value; }

    public boolean isEmpty() { return false; }

    public @NotNull E getValue() { return value; }

    /**
     * Below we implement equals / hashCode methods for the Elem class.
     */

    public boolean equals(Object other) {
        if (!(other instanceof Elem<?> otherElem)) return false;
        return value.equals(otherElem.value);
    }

    public int hashCode() { return value.hashCode(); }
}

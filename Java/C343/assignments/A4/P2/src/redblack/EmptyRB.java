package redblack;

import org.jetbrains.annotations.NotNull;
import trees.EmptyTreeExc;
import trees.TreePrinter;

/**
 * Represents an empty Red-Black tree node. A normal empty node is black, but we allow
 * for intermediate states where an empty node is double black. This is immediately
 * resolved by bubbling and balancing the tree, so the final tree should never
 * have an empty node that is double black.
 */
public record EmptyRB<E extends Comparable<E>>(Color color) implements RedBlackTree<E> {

    // Constructor --------------------------------------------------------------------------

    public EmptyRB() { this(Color.BLACK); }

    // Simple structural queries ------------------------------------------------------------

    public boolean isEmpty() { return true; }
    public int blackHeight() { return 0; }
    public boolean isWellFormed() { return color == Color.BLACK; }

    // Simple one-liner methods ------------------------------------------------------------

    public boolean isRed() { return false; }
    public boolean isBlack() { return color == Color.BLACK; }
    public boolean isDoubleBlack() { return color == Color.DOUBLE_BLACK; }
    public boolean isNegativeBlack() { return false; }

    public @NotNull RedBlackTree<E> redden() {
        throw new RuntimeException("Attempted to redden an empty tree");
    }

    public @NotNull RedBlackTree<E> blacken() {
        return new EmptyRB<>(Color.BLACK);
    }

    public @NotNull RedBlackTree<E> redder() {
        if (color == Color.DOUBLE_BLACK) return new EmptyRB<>(Color.BLACK);
        else throw new RuntimeException("Attempted to redden a black leaf");
    }

    // Search methods -----------------------------------------------------------------------

    public boolean contains(@NotNull E value) {
        return false;
    }

    public @NotNull E findMin() throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempted to findMin on an empty node");
    }

    // Insertion and deletion methods ------------------------------------------------------

    /**
     * Insert a new value into the tree. The actual work is done by the insertHelper
     * method. The root of the tree is always black, so we turn the result of insertHelper
     * into a black node before returning it.
     */
    public @NotNull RedBlackTree<E> insert(@NotNull E value) {
        return insertHelper(value).blacken();
    }

    /**
     * Insert a new value into the tree. A new red node is created with the given value,
     */
    public @NotNull RedBlackTree<E> insertHelper(@NotNull E value) {
        return new RBNode<>(Color.RED, value);
    }

    public @NotNull RedBlackTree<E> remove(@NotNull E value) throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempting to remove a non-existent value in Red-Black tree");
    }

    public @NotNull RedBlackTree<E> removeHelper(@NotNull E value) throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempting to remove a non-existent value in Red-Black tree");
    }

    public @NotNull RedBlackTree<E> removeMin() throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempting to remove a non-existent value in Red-Black tree");
    }

    // TreePrinter.PrintableNode interface methods ------------------------------------------

    public TreePrinter.PrintableNode getLeft() { return null; }
    public TreePrinter.PrintableNode getRight() { return null; }
    public String getText() { return "_" + color().toString(); }

    // For debugging ------------------------------------------------------------------------

    public String toString() { return "EmptyRB(" + color + ")"; }
}

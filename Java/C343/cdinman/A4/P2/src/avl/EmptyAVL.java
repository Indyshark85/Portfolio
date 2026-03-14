package avl;

import trees.EmptyTreeExc;
import trees.TreePrinter;
import org.jetbrains.annotations.NotNull;

public record EmptyAVL<E extends Comparable<E>>() implements AVLTree<E> {

    // Simple one-liner methods ------------------------------------------------------------

    public boolean isEmpty() { return true; }
    public int height() {
        return 0;
    }
    public int balanceFactor() {
        return 0;
    }
    public boolean isWellFormed() { return true; }

    // Search methods -----------------------------------------------------------------------

    public boolean contains(@NotNull E value) { return false; }

    public @NotNull E findMin() throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempted to findMin on an empty node");
    }

    // Insertion and deletion methods ------------------------------------------------------

    public @NotNull AVLTree<E> insert(@NotNull E value) {
        return new AVLNode<>(value);
    }

    public @NotNull AVLTree<E> remove(@NotNull E value) throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempting to remove a non-existent value in AVL tree");
    }

    public @NotNull AVLTree<E> removeMin() throws EmptyTreeExc {
        throw new EmptyTreeExc("Attempting to remove a non-existent value in AVL tree");
    }

    // TreePrinter.PrintableNode interface methods ------------------------------------------

    public TreePrinter.PrintableNode getLeft() { return null; }
    public TreePrinter.PrintableNode getRight() { return null; }
    public String getText() { return ""; }
}

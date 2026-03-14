package avl;

import org.jetbrains.annotations.NotNull;
import trees.EmptyTreeExc;
import trees.TreePrinter;

public sealed interface AVLTree<E extends Comparable<E>>
        extends TreePrinter.PrintableNode
        permits EmptyAVL, AVLNode {

    // Simple one-liner methods ------------------------------------------------------------

    boolean isEmpty();
    int height();
    int balanceFactor();
    boolean isWellFormed();

    // Search methods -----------------------------------------------------------------------

    boolean contains(@NotNull E value);
    @NotNull E findMin() throws EmptyTreeExc;

    // Insertion and deletion methods ------------------------------------------------------

    @NotNull AVLTree<E> insert(@NotNull E value);
    @NotNull AVLTree<E> remove(@NotNull E value) throws EmptyTreeExc;
    @NotNull AVLTree<E> removeMin() throws EmptyTreeExc;
}

package redblack;

import org.jetbrains.annotations.NotNull;
import trees.EmptyTreeExc;
import trees.TreePrinter;

public sealed interface RedBlackTree<E extends Comparable<E>> extends TreePrinter.PrintableNode
        permits EmptyRB, RBNode {

    // Simple structural queries ------------------------------------------------------------

    boolean isEmpty();
    int blackHeight();
    boolean isWellFormed();

    // Simple one-liner methods ------------------------------------------------------------

    boolean isRed();
    boolean isBlack();
    boolean isDoubleBlack();
    boolean isNegativeBlack();

    @NotNull RedBlackTree<E> redden();
    @NotNull RedBlackTree<E> blacken();
    @NotNull RedBlackTree<E> redder ();

    // Search methods -----------------------------------------------------------------------

    boolean contains(@NotNull E value);
    @NotNull E findMin() throws EmptyTreeExc;

    // Insertion and deletion methods ------------------------------------------------------

    @NotNull RedBlackTree<E> insert(@NotNull E value);
    @NotNull RedBlackTree<E> insertHelper(@NotNull E value);
    @NotNull RedBlackTree<E> remove(@NotNull E value) throws EmptyTreeExc;
    @NotNull RedBlackTree<E> removeHelper(@NotNull E value) throws EmptyTreeExc;
    @NotNull RedBlackTree<E> removeMin() throws EmptyTreeExc;
}


package avl;

import trees.EmptyTreeExc;
import trees.TreePrinter;
import org.jetbrains.annotations.NotNull;

public record AVLNode<E extends Comparable<E>>
        (@NotNull E value,
         @NotNull AVLTree<E> left,
         @NotNull AVLTree<E> right,
         int height)
        implements AVLTree<E> {

    // Constructors -------------------------------------------------------------------------

    public AVLNode(@NotNull E value) {
        this(value, new EmptyAVL<>(), new EmptyAVL<>());
    }

    public AVLNode(@NotNull E value, @NotNull AVLTree<E> left, @NotNull AVLTree<E> right) {
        this(value, left, right, 1 + Math.max(left.height(), right.height()));
    }

    // Simple one-liner methods ------------------------------------------------------------

    public boolean isEmpty() { return false; }
    public int height() {
        return height;
    }
    public int balanceFactor() {
        return left.height() - right.height();
    }
    public boolean isWellFormed() {
        return left.isWellFormed() && right.isWellFormed() && Math.abs(balanceFactor()) <= 1;
    }

    // Search methods -----------------------------------------------------------------------

    public boolean contains(@NotNull E searchValue) {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case comparison of searchValue to value fails, throw new RuntimeException("Comparison failed");
    }

    public @NotNull E findMin() throws EmptyTreeExc {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
    }

    // Insertion and deletion methods ------------------------------------------------------

    public @NotNull AVLTree<E> insert(@NotNull E newValue) {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case comparison of newValue to value fails, throw new RuntimeException("Comparison failed");
    }

    public @NotNull AVLTree<E> remove(@NotNull E removeValue) throws EmptyTreeExc {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case comparison of removeValue to value fails, throw new RuntimeException("Comparison failed");
    }

    public static <E extends Comparable<E>> @NotNull AVLTree<E> mergeSubtrees(
            @NotNull AVLTree<E> left,
            @NotNull AVLTree<E> right) {

        try { return mkBalanced(right.findMin(), left, right.removeMin()); }
        catch (EmptyTreeExc e) { return left; }
    }

    public @NotNull AVLTree<E> removeMin() {
        try { return mkBalanced(value, left.removeMin(), right); }
        catch (EmptyTreeExc e) { return right; }
    }

    // Rotations ----------------------------------------------------------------------------

    public static <E extends Comparable<E>> AVLTree<E> mkBalanced
            (@NotNull E value,
             @NotNull AVLTree<E> left,
             @NotNull AVLTree<E> right) {
        return new AVLNode<>(value, left, right).rotate();
    }

    public @NotNull AVLTree<E> rotate() {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
    }

    public @NotNull AVLTree<E> rotateRight() {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case rotation fails, throw new RuntimeException("Right rotation failed");
    }

    public @NotNull AVLTree<E> rotateLeft() {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case rotation fails, throw new RuntimeException("Left rotation failed");
    }

    public AVLTree<E> rotateLeftRight() {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case rotation fails, throw new RuntimeException("Left-Right rotation failed");
    }

    public AVLTree<E> rotateRightLeft() {
        throw new Error("Not implemented"); // TODO: Copy your implementation from A4/P1/src/AVL_Node
        // in case rotation fails, throw new RuntimeException("Right-Left rotation failed");
    }

    // TreePrinter.PrintableNode interface methods ------------------------------------------

    public TreePrinter.PrintableNode getLeft() {
        return left.isEmpty() ? null : left;
    }
    public TreePrinter.PrintableNode getRight() {
        return right.isEmpty() ? null : right;
    }
    public String getText() {
        return value.toString();
    }
}

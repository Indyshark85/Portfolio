import org.jetbrains.annotations.NotNull;

/**
 * The general interface for an immutable AVL tree. An AVL tree is a self-balancing
 * binary search tree.
 * <p>
 * The methods insert and remove return a new tree with the value inserted or
 * removed. This is because the tree is immutable.
 * <p>
 * The methods isEmpty, height, balanceFactor run in O(1) time. The methods
 * contains, findMin, insert, remove run in O(log n) time, where n is the number
 * of nodes in the tree.
 * </p>
 * </p>
 */
interface AVL_Tree<E extends Comparable<E>> extends Tree_Printer.PrintableNode {
        boolean isEmpty();
        int height();
        int balanceFactor();
        boolean contains(@NotNull E value);
        @NotNull E findMin() throws EmptyTreeE;
        @NotNull AVL_Tree<E> insert(@NotNull E value);
        @NotNull AVL_Tree<E> remove(@NotNull E value) throws EmptyTreeE;
}

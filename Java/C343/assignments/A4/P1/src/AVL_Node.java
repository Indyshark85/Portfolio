import org.jetbrains.annotations.NotNull;

/**
 * A non-empty node in an AVL tree. We use a record to represent the node because
 * it makes pattern matching on the structure of the node easier.
 * <p>
 * Warning! The constructors of this class should be private because they do not
 * perform the balancing operation. They are exposed here for testing purposes only.
 * </p>
 */
record AVL_Node<E extends Comparable<E>>
        (@NotNull E value,
         @NotNull AVL_Tree<E> left,
         @NotNull AVL_Tree<E> right,
         int height)
        implements AVL_Tree<E> {

    public AVL_Node(@NotNull E value) {
        this(value, new Empty<>(), new Empty<>());
    }

    public AVL_Node(@NotNull E value, @NotNull AVL_Tree<E> left, @NotNull AVL_Tree<E> right) {
        this(value, left, right, 1 + Math.max(left.height(), right.height()));
    }

    /**
     * These two methods should be the only way to create a new AVL_Node. They ensure that the
     * node is balanced after creation.
     */
    static <E extends Comparable<E>> AVL_Tree<E> mkBalanced (@NotNull E value, @NotNull AVL_Tree<E> left, @NotNull AVL_Tree<E> right) {
        return new AVL_Node<>(value, left, right).rotate();
    }

    static <E extends Comparable<E>> AVL_Tree<E> mkBalanced (@NotNull AVL_Node<E> node) {
        return node.rotate();
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int balanceFactor() {
        return left.height() - right.height();
    }

    /**
     * Inserts a new value into the tree.
     * <p>
     * The general idea is to insert the value into the left or right subtree, then balance the
     * tree. If the value is less than the current value, insert it into the left subtree. If the
     * value is greater, insert it into the right subtree. If the value is equal, return the tree
     * unchanged. Make sure to balance the tree after inserting the value.
     * </p>
     *
     */
    @Override
    public @NotNull AVL_Tree<E> insert(@NotNull E newValue) {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case comparison of newValue to value fails, throw new InternalErrorE("Comparison failed");
    }

    /**
     * Removes a value from the tree.
     * <p>
     * The general idea is to remove the value from the left or right subtree, then balance the
     * tree. If the value is less than the current value, remove it from the left subtree. If the
     * value is greater, remove it from the right subtree. If the value is equal, we must
     * remove the current node which is a bit more complicated and is implemented in
     * the mergeSubtrees method. Make sure to balance the tree after removing the value.
     * </p>
     */
    @Override
    public @NotNull AVL_Tree<E> remove(@NotNull E removeValue) throws EmptyTreeE {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case comparison of removeValue to value fails, throw new InternalErrorE("Comparison failed");
    }

    /**
     * Merges the left and right subtrees of the current node into a single tree.
     * <p>
     * There are two easy cases. If the left subtree is empty, return the right subtree. If the
     * right subtree is empty, return the left subtree. The more complicated case is when both
     * subtrees are non-empty. In this case, we find the minimum value in the right subtree and
     * create a new tree with that value, the left subtree, and the right subtree with the
     * minimum value removed. Make sure to balance the tree after merging the subtrees.
     */
    private @NotNull AVL_Tree<E> mergeSubtrees() throws EmptyTreeE {
        throw new Error("Not implemented"); // TODO: Implement this method
    }

    /**
     * Finds the minimum value in the tree.
     * <p>
     * The minimum value is the leftmost value in the tree. If the left subtree is empty, the
     * current value is the minimum. Otherwise, the minimum value is in the left subtree.
     * </p>
     */
    @Override
    public @NotNull E findMin() throws EmptyTreeE {
        throw new Error("Not implemented"); // TODO: Implement this method
    }

    /**
     * Balances the tree if it is unbalanced.
     * <p>
     * If the balance factor is 0 or 1, the tree is balanced, and we can return the tree unchanged.
     * If the balance factor is 2, the tree is left-heavy. In this case, we need to rotate the tree
     * to the right using either a simple rotateRight or a rotateLeftRight depending on the
     * balance factor of the left subtree. If the balance factor is -2, the tree is right-heavy and
     * the same logic applies. Make sure the tree is balanced after rotating.
     * </p>
     */
    public @NotNull AVL_Tree<E> rotate() {
        throw new Error("Not implemented"); // TODO: Implement this method
    }

    /**
     * The general idea is to rotate the tree to the right. This means that the left subtree
     * becomes the new root of the tree, the current value becomes the right child of the new
     * root, and the right subtree of the left subtree becomes the left subtree of the current
     * node. For convenience, use pattern matching to destructure the left
     * subtree into its value, left subtree, right subtree, and height.
     * </p>
     */
    public @NotNull AVL_Tree<E> rotateRight() {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case rotation fails, throw new InternalErrorE("Right rotation failed");
    }

    /**
     * Symmetric to rotateRight.
     */
    public @NotNull AVL_Tree<E> rotateLeft() {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case rotation fails, throw new InternalErrorE("Left rotation failed");
    }

    /**
     * This is a two-step process. First, rotate the left subtree to the left. Then rotate the tree to
     * the right.
     */
    public AVL_Tree<E> rotateLeftRight() {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case rotation fails, throw new InternalErrorE("Left-Right rotation failed");
    }

    /**
     * Symmetric to rotateLeftRight.
     */
    public AVL_Tree<E> rotateRightLeft() {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case rotation fails, throw new InternalErrorE("Right-Left rotation failed");
    }

    /**
     * Checks if the tree contains a value. If the value is equal to the current value, return true.
     * If the value is less than the current value, check the left subtree. If the value is greater
     * than the current value, check the right subtree.
     */
    @Override
    public boolean contains(@NotNull E searchValue) {
        throw new Error("Not implemented"); // TODO: Implement this method
        // in case comparison of searchValue to value fails, throw new InternalErrorE("Comparison failed");
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // Tree_Printer.PrintableNode methods

    public Tree_Printer.PrintableNode getLeft() {
        return left.isEmpty() ? null : left;
    }

    public Tree_Printer.PrintableNode getRight() {
        return right.isEmpty() ? null : right;
    }

    public String getText() {
        return value.toString();
    }
}

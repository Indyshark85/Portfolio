import org.jetbrains.annotations.NotNull;

/**
 * An empty node in an AVL tree.
 */
class Empty<E extends Comparable<E>> implements AVL_Tree<E> {

    /**
     * We use the convention that the height of an empty node is 0.
     */
    @Override
    public int height() {
        return 0;
    }

    /**
     * An empty node is "balanced" by definition.
     */
    @Override
    public int balanceFactor() {
        return 0;
    }

    @Override
    public @NotNull AVL_Tree<E> insert(@NotNull E value) {
        return new AVL_Node<>(value);
    }

    @Override
    public boolean contains(@NotNull E value) {
        return false;
    }

    @Override
    public @NotNull AVL_Tree<E> remove(@NotNull E value) throws EmptyTreeE {
        throw new EmptyTreeE("Value not found in tree");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public @NotNull E findMin() throws EmptyTreeE {
        throw new EmptyTreeE("Attempted to findMin on an empty node");
    }

    // Tree_Printer.PrintableNode methods

    public Tree_Printer.PrintableNode getLeft() { return null; }
    public Tree_Printer.PrintableNode getRight() { return null; }
    public String getText() { return ""; }
}

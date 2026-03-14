import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> {
    private final E data;
    private BinaryTree<E> left;
    private BinaryTree<E> right;

    /**
     * Create a binary tree with the given data, left, and right branches.
     *
     * @param data  Data to store in this node
     * @param left  Left branch of this node, pass {@code null} if this branch is empty
     * @param right Right branch of this node, pass {@code null} if this branch is empty
     */
    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    /**
     * Create a binary tree with the given data and no child nodes.
     *
     * @param data Data to store in this node
     */
    public BinaryTree(E data) {
        this(data, null, null);
    }

    public E getData() {
        return data;
    }

    public BinaryTree<E> getLeft() {
        return left;
    }

    public void setLeft(BinaryTree<E> left) {
        this.left = left;
    }

    public BinaryTree<E> getRight() {
        return right;
    }

    public void setRight(BinaryTree<E> right) {
        this.right = right;
    }


    /**
     * Returns whether this object and the given other object are equal. Compare the root node and recursively verify
     * that their left and right subtrees are identical. Both trees must have the same structure at each corresponding
     * node.
     *
     * @param other A binary tree to compare equality to
     * @return True if the trees are identical, false otherwise
     */
    public boolean dfsEquals(BinaryTree<E> other) {
        throw new Error("Not Implemented");
    }

    /**
     * Returns whether this object and the given other object are equal. Use two queues to maintain the traversal order.
     * Both trees must have the same structure at each corresponding node.
     *
     * @param other A binary tree to compare equality to
     * @return True if the trees are identical, false otherwise
     */
    public boolean bfsEquals(BinaryTree<E> other) {
        throw new Error("Not Implemented");
    }


    /**
     * Returns whether this object and the given other object are equal. Use Morris traversal to efficiently traverse
     * two binary trees without using recursions. Both trees must have the same structure at each corresponding node.
     *
     * @param other A binary tree to compare equality to
     * @return True if the trees are identical, false otherwise
     */
    public boolean morrisEquals(BinaryTree<E> other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        BinaryTree<E> t1 = this;
        BinaryTree<E> t2 = other;

        // it is extremely important that both traversals finish so that
        // the trees passed to this function are not modified by returning early
        boolean r = true;

        // while either traversal is valid
        while (t1 != null || t2 != null) {

            // if one traversal finishes they're not equal
            if (t1 == null || t2 == null) {
                r = false;
            }

            // if both traversals are still valid check equality
            if (t1 != null && t2 != null && t1.data != t2.data) {
                r = false;
            }

            // traverse first tree
            t1 = morrisTraversal(t1);

            // traverse second tree
            t2 = morrisTraversal(t2);
        }

        return r;
    }

    private BinaryTree<E> morrisTraversal(BinaryTree<E> t) {
        if (t != null) {
            // if left is null, go to right
            if (t.left == null) {
                throw new Error("Not Implemented");
            } else {
                // find in-order predecessor

                // temporary link

                // remove link
                throw new Error("Not Implemented");
            }
        }
        return t;
    }

}
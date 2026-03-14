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
        if (other == null) return false;
        if (this == other) return true;

        // Compare data
        if (!this.data.equals(other.data)) return false;

        //to the left
        if (this.left == null) {
            if (other.left != null) return false;
        } else if (!this.left.dfsEquals(other.left)) {
            return false;
        }

        //to the right
        if (this.right == null) {
            if (other.right != null) return false;
        } else if (!this.right.dfsEquals(other.right)) {
            return false;
        }
        return true;
    }

    /**
     * Returns whether this object and the given other object are equal. Use two queues to maintain the traversal order.
     * Both trees must have the same structure at each corresponding node.
     *
     * @param other A binary tree to compare equality to
     * @return True if the trees are identical, false otherwise
     */
    public boolean bfsEquals(BinaryTree<E> other) {
        if (other == null) return false;
        if (this == other) return true;

        Queue<BinaryTree<E>> q1 = new LinkedList<>();
        Queue<BinaryTree<E>> q2 = new LinkedList<>();
        q1.add(this);
        q2.add(other);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            BinaryTree<E> n1 = q1.poll();
            BinaryTree<E> n2 = q2.poll();

            if (n1 == null && n2 == null) continue;
            if (n1 == null || n2 == null) return false;

            if (!n1.data.equals(n2.data)) return false;

            q1.add(n1.left);
            q1.add(n1.right);
            q2.add(n2.left);
            q2.add(n2.right);
        }
        return q1.isEmpty() && q2.isEmpty();
    }


    /**
     * Returns whether this object and the given other object are equal. Use Morris traversal to efficiently traverse
     * two binary trees without using recursions. Both trees must have the same structure at each corresponding node.
     *
     * @param other A binary tree to compare equality to
     * @return True if the trees are identical, false otherwise
     */
    public boolean morrisEquals(BinaryTree<E> other) {
        if (other == null) return false;
        if (this == other) return true;

        BinaryTree<E> t1 = this;
        BinaryTree<E> t2 = other;

        while (t1 != null && t2 != null) {
            // Handle structure mismatch early
            if ((t1.left == null) != (t2.left == null)) return false;
            if ((t1.right == null) != (t2.right == null)) return false;

            // Find predecessors for both
            BinaryTree<E> pred1 = t1.left;
            BinaryTree<E> pred2 = t2.left;

            if (pred1 == null && pred2 == null) {
                // Visit both nodes
                if (!t1.data.equals(t2.data)) return false;
                t1 = t1.right;
                t2 = t2.right;
            } else if (pred1 != null && pred2 != null) {
                while (pred1.right != null && pred1.right != t1) pred1 = pred1.right;
                while (pred2.right != null && pred2.right != t2) pred2 = pred2.right;

                // Mismatch in threading structure
                if ((pred1.right == null) != (pred2.right == null)) return false;

                if (pred1.right == null) {
                    // Thread both and go left
                    pred1.right = t1;
                    pred2.right = t2;
                    t1 = t1.left;
                    t2 = t2.left;
                } else {
                    // Remove threads and visit
                    pred1.right = null;
                    pred2.right = null;

                    if (!t1.data.equals(t2.data)) return false;

                    t1 = t1.right;
                    t2 = t2.right;
                }
            } else {
                // One tree has a left child but the other doesn’t
                return false;
            }
        }

        return t1 == null && t2 == null;
        }

    private BinaryTree<E> morrisTraversal(BinaryTree<E> current) {
        if (current == null) return null;

        if (current.left == null) {
            // No left child → visit node, move right
            return current.right;
        } else {
            // Find in-order predecessor
            BinaryTree<E> pred = current.left;
            while (pred.right != null && pred.right != current) {
                pred = pred.right;
            }

            if (pred.right == null) {
                // Create temporary thread and move left
                pred.right = current;
                return current.left;
            } else {
                // Thread exists → remove it and move right
                pred.right = null;
                return current.right;
            }
        }
    }

}



package redblack;

import org.jetbrains.annotations.NotNull;
import trees.EmptyTreeExc;
import trees.TreePrinter;

import java.util.Optional;

public record RBNode<E extends Comparable<E>>
        (@NotNull Color color,
         @NotNull E value,
         @NotNull RedBlackTree<E> left,
         @NotNull RedBlackTree<E> right,
         int height)
        implements RedBlackTree<E> {

    // Constructors -------------------------------------------------------------------------

    public RBNode (@NotNull Color color,
                   @NotNull E value) {
        this(color, value, new EmptyRB<>(), new EmptyRB<>());
    }

    public RBNode (@NotNull Color color,
                   @NotNull E value,
                   @NotNull RedBlackTree<E> left,
                   @NotNull RedBlackTree<E> right) {
        this(color, value, left, right,
                Math.max(left.blackHeight(), right.blackHeight()) + (color == Color.BLACK ? 1 : 0));
    }

    // Simple structural queries ------------------------------------------------------------

    public boolean isEmpty() { return false; }
    public int blackHeight() { return height; }
    public boolean isWellFormed () {
        return left.isWellFormed() && right.isWellFormed() &&
                left.blackHeight() == right.blackHeight() &&
                (isBlack() || (isRed() && left.isBlack() && right.isBlack()));
    }

    // Simple one-liner methods ------------------------------------------------------------

    public boolean isRed() { return color.isRed(); }
    public boolean isBlack() { return color.isBlack(); }
    public boolean isDoubleBlack() { return color.isDoubleBlack(); }
    public boolean isNegativeBlack() { return color.isNegativeBlack(); }

    public @NotNull RedBlackTree<E> redden () {
        return new RBNode<>(Color.RED, value, left, right);
    }

    public @NotNull RedBlackTree<E> blacken () {
        return new RBNode<>(Color.BLACK, value, left, right);
    }

    public @NotNull RedBlackTree<E> redder() {
        return new RBNode<>(color.redder(), value, left, right);
    }

    // Search methods -----------------------------------------------------------------------

    public boolean contains(@NotNull E value) {
        int cmp = value.compareTo(this.value);
        if (cmp == 0) return true;
        if (cmp < 0) {
            return left.contains(value);
        } else {
            return right.contains(value);
        }
    }

    public @NotNull E findMin() throws EmptyTreeExc {
        // Go left until left is empty.
        if (left.isEmpty()) return this.value;
        return left.findMin();
    }

    // Insertion and deletion methods ------------------------------------------------------

    /**
     * Inserts a new value into the tree. The actual work is done by the insertHelper
     * method. The root of the tree is always black, so we turn the result of insertHelper
     * into a black node before returning it.
     */
    public @NotNull RedBlackTree<E> insert(@NotNull E value) {
        return insertHelper(value).blacken();
    }

    /**
     * Inserts a new value into the tree. If the value is already present, the
     * tree is returned unchanged. Otherwise, we just make recursive calls to insert
     * the value into the left or right subtree, depending on the comparison result.
     * The tree is then rebalanced before returning it.
     */
    public @NotNull RedBlackTree<E> insertHelper(@NotNull E value) {
        if (value.compareTo(this.value) == 0) {
            // already present -> return this node unchanged
            return this;
        }
        else if (value.compareTo(this.value) < 0) {
            //return balance(this.color, this.value, this.left.insertHelper(value), this.right);
            // Insert into left, then balance.
            RedBlackTree<E> newLeft = left.insertHelper(value);
            return balance(this.color, this.value, newLeft, this.right);
        }
        else {
            //return balance(?, ?, ?, ?);
            RedBlackTree<E> newRight = right.insertHelper(value);
            return balance(this.color, this.value, this.left, newRight);
        }
    }

    /**
     * Removes a value from the tree. The actual work is done by the removeHelper
     * method. The root of the tree is always black, so we turn the result of removeHelper
     * into a black node before returning it.
     */
    public @NotNull RedBlackTree<E> remove(@NotNull E removeValue) throws EmptyTreeExc {
        return removeHelper(removeValue).blacken();
    }

    /**
     * Removes a value from the tree. If the value is not present, we throw
     * an exception. If the value is present, we handle several cases:
     * <ul>
     *     <li>If the value is found at the current node, we merge the left and right subtrees.</li>
     *     <li>If the value is less than the current node, we remove the value from the left subtree.</li>
     *     <li>If the value is greater than the current node, we remove the value from the right subtree.</li>
     *  </ul>
     *  The recursive calls might return trees containing double-black nodes, so we need to
     *  bubble the double-blackness up the tree and rebalance it.
     */
    public @NotNull RedBlackTree<E> removeHelper(@NotNull E removeValue) throws EmptyTreeExc {
        if (removeValue.compareTo(this.value) == 0) {
            boolean leftEmpty = this.left.isEmpty();
            boolean rightEmpty = this.right.isEmpty();

            // Case: leaf node (both children empty) -> create an empty node.
            if (leftEmpty && rightEmpty) {
                // If the deleted node was red, return a normal black empty leaf (no extra blackness).
                // If the deleted node was black, the removal creates an extra black -> return double black empty.
                return new EmptyRB<>(this.color.isRed() ? Color.BLACK : Color.DOUBLE_BLACK);
            }

            // Otherwise, at least one child is non-empty -> merge the subtrees as before.
            return mergeSubtrees(this.color, this.left, this.right);
        }
        else if (removeValue.compareTo(this.value) < 0) {
            // return bubbleThenBalance(?, ?, ?, ?);
            RedBlackTree<E> newLeft = this.left.removeHelper(removeValue);
            return bubbleThenBalance(this.color, this.value, newLeft, this.right);
        }
        else {
            // return bubbleThenBalance(this.color, this.value, this.left, this.right.removeHelper(removeValue));
            RedBlackTree<E> newRight = this.right.removeHelper(removeValue);
            return bubbleThenBalance(this.color, this.value, this.left, newRight);
        }
    }

    /**
     * Merges two red-black subtrees under a specified color:
     * <ul>
     *   <li>If both subtrees are empty, we return an empty tree. If the deleted node
     *   was red, the empty tree is black; otherwise, it is double black.</li>
     *   <li>If the deleted node is black and one of the subtree is empty while the other is red,
     *       we return a blackened copy of the red subtree.</li>
     *   <li>Otherwise, the minimum element in the right subtree is selected to replace the
     *       removed node, and the given left tree and the new right subtree are passed to
     *       bubbleThenBalance for further processing.</li>
     * </ul>
     */
    public static <E extends Comparable<E>> @NotNull RedBlackTree<E> mergeSubtrees(
            @NotNull Color color,
            @NotNull RedBlackTree<E> left,
            @NotNull RedBlackTree<E> right) {
//        throw new Error("Not implemented yet"); // TODO
        boolean leftEmpty = left.isEmpty();
        boolean rightEmpty = right.isEmpty();

        if (leftEmpty && rightEmpty) {
            return new EmptyRB<>(color.isRed() ? Color.BLACK : Color.DOUBLE_BLACK);
        } else if (color.isBlack() && leftEmpty && right.isRed()) {
            return right.blacken();
        }
        else if (color.isBlack() && rightEmpty && left.isRed()) {
            return left.blacken();
        } else {
            try {
                E minRight = right.findMin();
                RedBlackTree<E> newRight = right.removeMin();
                // return bubbleThenBalance(?, ?, ?, ?);
                return bubbleThenBalance(color, minRight, left, newRight);
            }
            catch (EmptyTreeExc e) {
                throw new RuntimeException("findMin/removeMin in mergeSubtrees: unexpected EmptyTreeExc", e);
            }
        }
    }

    /**
     * Removes the minimum element from the tree. If the left subtree is empty,
     * then the current node is the minimum element, so this is another case
     * of merging subtrees. Otherwise, we remove the minimum element from the left
     * subtree and pass the new left subtree and the current right subtree to
     * bubbleThenBalance for further processing.
     */
    public @NotNull RedBlackTree<E> removeMin () throws EmptyTreeExc {
        if (left.isEmpty()) {
            boolean rightEmpty = this.right.isEmpty();

            // If both are empty -> this is a leaf; deleting it produces an empty node.
            if (rightEmpty) {
                return new EmptyRB<>(this.color.isRed() ? Color.BLACK : Color.DOUBLE_BLACK);
            }

            // Otherwise, only left is empty and right is non-empty: merge under current color.
            return mergeSubtrees(this.color, this.left, this.right);
        } else {
            // remove min from left subtree and bubble/balance
            RedBlackTree<E> newLeft = left.removeMin();
            return bubbleThenBalance(this.color, this.value, newLeft, this.right);
        }
    }

    // Rotations, bubbling, and balancing ---------------------------------------------------

    /**
     * Rebuilds a portion of a red-black tree by creating a new subtree with the given color,
     * the given elements, and the given subtrees. The color of the constructed node
     * is "redder" than the given color. The left and right children are black.
     */
    public static <E extends Comparable<E>> RedBlackTree<E> rebuildAsRed
    (Color cm, E leftValue, E newValue, E rightValue,
     RedBlackTree<E> leftLeft, RedBlackTree<E> leftRight,
     RedBlackTree<E> rightLeft, RedBlackTree<E> rightRight) {
        RedBlackTree<E> leftChild = new RBNode<>(Color.BLACK, leftValue, leftLeft, leftRight);
        RedBlackTree<E> rightChild = new RBNode<>(Color.BLACK, rightValue, rightLeft, rightRight);
        // The parent node is "redder" than cm
        return new RBNode<>(cm.redder(), newValue, leftChild, rightChild);
    }

    /**
     * If neither left nor right subtrees are double black, the method simply
     * rebalances the tree by creating a new node with the given color, value,
     * and subtrees. Otherwise, the method moves the extra blackness up the
     * tree by making the current node blacker and the children redder.
     * It then rebalances the tree.
     */
    public static <E extends Comparable<E>> RedBlackTree<E> bubbleThenBalance(
            Color cm, E em, RedBlackTree<E> lm, RedBlackTree<E> rm) {
        if (!lm.isDoubleBlack() && !rm.isDoubleBlack()) {
            return balance(cm, em, lm, rm);
        }
        else {
            Color newColor = cm.blacker();
            RedBlackTree<E> newLeft  = lm.redder();
            RedBlackTree<E> newRight = rm.redder();
            return balance(newColor, em, newLeft, newRight);
        }
    }

    /**
     * This is the main method for rebalancing a red-black tree. It takes the color,
     * value, and subtrees of a node and tries to balance the tree based on the
     * patterns found in the left and right subtrees. To make things manageable,
     * the method first tries to balance based on the left subtree. If no match is found,
     * it tries to balance based on the right subtree. If no match is found in either case,
     * the method returns a new red-black node with the given color, value, and subtrees.
     */
    public static <E extends Comparable<E>> RedBlackTree<E> balance (
            Color cm, E em, RedBlackTree<E> lm, RedBlackTree<E> rm) {

        // Try to balance based on the left subtree.
        Optional<RedBlackTree<E>> leftBalanced = tryBalanceLeft(cm, em, lm, rm);
        if (leftBalanced.isPresent()) return leftBalanced.get();

        // Next, try to balance based on the right subtree.
        // If none match, return the default node.
        Optional<RedBlackTree<E>> rightBalanced = tryBalanceRight(cm, em, lm, rm);
        return rightBalanced.orElseGet(() -> new RBNode<>(cm, em, lm, rm));
    }

    /**
     * Attempts to rebalance the tree based on patterns found in the left subtree.
     * <p>This helper method checks for specific configurations. First the current
     * node must be black or double black. Then it looks for the following configurations:
     * <ul>
     *   <li>the left child is red and its right child is also red</li>
     *   <li>the left child is red and its left child is also red</li>
     *   <li>the current color is double black, the left child is negative black, and
     *   its children are black as well</li>
     * </ul>
     * In the first two cases, we perform what is essentially an AVL rotation but we
     * use rebuildAsRed to adjust the colors at the same time. In the latter case,
     * we create a new black node as explained in the supplementary documentation.
     */
    private static <E extends Comparable<E>> Optional<RedBlackTree<E>> tryBalanceLeft(
            Color cm, E em, RedBlackTree<E> lm, RedBlackTree<E> rm) {
//        throw new Error("Not implemented yet"); // TODO
        // The Current node must be black or double black
        if (!cm.isBlackOrDoubleBlack()) {
            return Optional.empty();
        }
        // If the left child is red
        if (lm.isRed() && lm instanceof RBNode<E> leftNode) {
            // Case 1: Left child's right child is red (Left-Right)
            if (leftNode.right.isRed() && leftNode.right instanceof RBNode<E> leftRightNode) {
                return Optional.of(
                        rebuildAsRed(
                                cm,
                                leftNode.value(),        // leftValue
                                leftRightNode.value(),   // newValue (middle)
                                em,                      // rightValue (current node's value)
                                leftNode.left,           // leftLeft
                                leftRightNode.left,      // leftRight
                                leftRightNode.right,     // rightLeft
                                rm                       // rightRight (original right subtree)
                        )
                );
            }
            // Case 2: The Left child's left child is red (Left-Left)
            else if (leftNode.left.isRed() && leftNode.left instanceof RBNode<E> leftLeftNode) {
                return Optional.of(
                        rebuildAsRed(
                                cm,
                                leftLeftNode.value(),    // leftValue (grandchild)
                                leftNode.value(),        // newValue (child)
                                em,                      // rightValue (current node)
                                leftLeftNode.left,       // leftLeft
                                leftLeftNode.right,      // leftRight
                                leftNode.right,          // rightLeft
                                rm                       // rightRight
                        )
                );
            }
        }
        // Case 3: Double black with negative black left child
        else if (cm.isDoubleBlack() && lm.isNegativeBlack()
                && lm instanceof RBNode<E> leftNode
                && leftNode.left instanceof RBNode<E> leftLeftNode
                && leftNode.right instanceof RBNode<E> leftRightNode
                && leftLeftNode.isBlack() && leftRightNode.isBlack()) {
            // Create new black left subtree from leftLeftNode (ensure black)
            RedBlackTree<E> newLeft = new RBNode<>(Color.BLACK,
                    leftLeftNode.value(),
                    leftLeftNode.left,
                    leftLeftNode.right);

            // Build new right subtree: a black node holding current value em with left = leftRightNode and right = original rm
            RedBlackTree<E> newRight = new RBNode<>(Color.BLACK,
                    em,
                    leftRightNode,
                    rm);

            // leftNode.value becomes the new black root for this subtree
            return Optional.of(new RBNode<>(Color.BLACK, leftNode.value(), newLeft, newRight));
        }
        return Optional.empty();
    }

    /**
     * Symmetric to tryBalanceLeft.
     */
    private static <E extends Comparable<E>> Optional<RedBlackTree<E>> tryBalanceRight(Color cm, E em, RedBlackTree<E> lm, RedBlackTree<E> rm) {
//        throw new Error("Not implemented yet"); // TODO
        //The current node must be black or double black
        if (!cm.isBlackOrDoubleBlack()) {
            return Optional.empty();
        }
        // If the right child is red
        if (rm.isRed() && rm instanceof RBNode<E> rightNode) {
            // Case 1: Right child's left child is red (Right-Left)
            if (rightNode.left.isRed() && rightNode.left instanceof RBNode<E> rightLeftNode) {
                return Optional.of(
                        rebuildAsRed(
                                cm,
                                em,                      // leftValue becomes current node value
                                rightLeftNode.value(),   // newValue (middle)
                                rightNode.value(),       // rightValue (child)
                                lm,                      // leftLeft (original left subtree)
                                rightLeftNode.left,      // leftRight
                                rightLeftNode.right,     // rightLeft
                                rightNode.right          // rightRight
                        )
                );
            }
            // Case 2: Right child's right child is red (Right-Right)
            else if (rightNode.right.isRed() && rightNode.right instanceof RBNode<E> rightRightNode) {
                return Optional.of(
                        rebuildAsRed(
                                cm,
                                em,                      // leftValue (current node's value)
                                rightNode.value(),       // newValue (child)
                                rightRightNode.value(),  // rightValue (grandchild)
                                lm,                      // leftLeft
                                rightNode.left,          // leftRight
                                rightRightNode.left,     // rightLeft
                                rightRightNode.right     // rightRight
                        )
                );
            }
        }
        // Case 3: Double black with negative black right child
        else if (cm.isDoubleBlack() && rm.isNegativeBlack()
                && rm instanceof RBNode<E> rightNode
                && rightNode.left instanceof RBNode<E> rightLeftNode
                && rightNode.right instanceof RBNode<E> rightRightNode
                && rightLeftNode.isBlack() && rightRightNode.isBlack()) {
            // Build new left subtree: black node holding current value em with left = original lm and right = rightLeftNode
            RedBlackTree<E> newLeft = new RBNode<>(Color.BLACK, em, lm, rightLeftNode);

            // New right subtree built from rightRightNode
            RedBlackTree<E> newRight = new RBNode<>(Color.BLACK,
                    rightRightNode.value(),
                    rightRightNode.left,
                    rightRightNode.right);

            // Promote rightNode.value to be the black root of this subtree
            return Optional.of(new RBNode<>(Color.BLACK, rightNode.value(), newLeft, newRight));
        }

        return Optional.empty();
    }

    // TreePrinter.PrintableNode interface methods ------------------------------------------

    public TreePrinter.PrintableNode getLeft() {
        return left.isEmpty() && left.isBlack() ? null : left;
    }

    public TreePrinter.PrintableNode getRight() {
        return right.isEmpty() && right.isBlack() ? null : right;
    }

    public String getText() {
        return value + color.toString();
    }

    // For debugging purposes ----------------------------------------------------------------

    public String toString() {
        return "RBNode(" + color + ", " + value + ")";
    }

}
public class RBTUtils {
    // A red black tree is a BST. Hence, we can compare the target element with the current one
    // and decide which child to search
    public static boolean contains(RedBlackNode root, int element) {
        if (root.isEmpty()) {
            return false;
        }

        if (element == root.element) {
            return true;
        } else if (element < root.element) {
            return contains(root.left, element);
        } else {
            return contains(root.right, element);
        }
    }

    // The minimum element in a red black tree is the leftmost child in the left subtree
    public static Integer findMin(RedBlackNode root) {
        RedBlackNode minNode = root;
        while(!minNode.left.isEmpty()) {
            minNode = minNode.left;
        }
        return minNode.element;
    }

    // The maximum element in a red black tree is the rightmost child in the right subtree
    public static Integer findMax(RedBlackNode root) {
        RedBlackNode maxNode = root;
        while(!maxNode.right.isEmpty()) {
            maxNode = maxNode.right;
        }
        return maxNode.element;
    }

    // Print all elements in the red black tree in ascending order
    public static void printTree(RedBlackNode root) {
        if( !root.isEmpty() ) {
            printTree(root.left);
            System.out.print(root.element + " ");
            printTree(root.right);
        }
    }

    // Calculate the height of the current node;
    // the height is equal to the number of black nodes from the current node to any EMPTY node.
    public static int height(RedBlackNode root) {
        if (root.isEmpty()) {
            return 1;
        } else {
            int childHeight = Math.max(height(root.left), height(root.right));
            return (root.color == RB.BLACK) ? childHeight + 1 : childHeight;
        }
    }


    /*
     *        P                      R
     *      /  \                   /  \
     *     L    R      ====>      P    RR
     *         / \               / \
     *       RL  RR             L  RL
     */
    public static RedBlackNode leftRotateForRR(RedBlackNode parent, RedBlackNode rightChild) {
        parent.right = rightChild.left;
        rightChild.left = parent;
        return rightChild;
    }

    /*
     *        P                      P                          RL
     *      /  \                   /  \                       /    \
     *     L    R      ====>      L   RL        ====>        P      R
     *         / \                   /  \                   / \    / \
     *       RL  RR                RLL   R                 L RLL  RLR RR
     *      / \                         / \
     *    RLL RLR                     RLR RR
     */
    public static RedBlackNode leftRotateForRL(RedBlackNode parent, RedBlackNode rightChild, RedBlackNode rightLeftGrandChild) {
        rightChild.left = rightLeftGrandChild.right;
        rightLeftGrandChild.right = rightChild;
        return leftRotateForRR(parent, rightLeftGrandChild);
    }

    /*
     *         P                      L
     *       /  \                   /  \
     *      L    R      ====>     LL    P
     *     / \                         / \
     *   LL  LR                       LR  R
     */
    public static RedBlackNode rightRotateForLL(RedBlackNode parent, RedBlackNode leftChild) {
        // TODO: implement the right rotation for LL case, use leftRotateForRR() as your reference
        throw new Error("not implemented yet");
    }

    /*
     *         P                      P                           LR
     *       /  \                   /  \                        /    \
     *      L    R      ====>     LR    R        ====>        L       P
     *    /  \                   /  \                        / \     / \
     *   LL  LR                 L   LRR                     LL RLL LRR  R
     *      / \                / \
     *    LRL LRR            LL  LRL
     */
    public static RedBlackNode rightRotateForLR(RedBlackNode parent, RedBlackNode leftChild, RedBlackNode leftRightGrandChild) {
        // TODO: implement the right rotation for LR case, use leftRotateForRL() as your reference
        throw new Error("not implemented yet");
    }

}

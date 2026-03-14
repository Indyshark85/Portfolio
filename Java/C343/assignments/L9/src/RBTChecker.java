public class RBTChecker {

    private static int height = 0;

    // Property 1: all nodes of a red black tree should either be RED or BLACK
    private static boolean allNodeRedBlack(RedBlackNode t) {
        if (t.isEmpty()) {
            return true;
        }
        boolean leftValid = allNodeRedBlack(t.left);
        boolean rightValid = allNodeRedBlack(t.right);
        boolean rootValid = (t.color == RB.RED) || (t.color == RB.BLACK);
        return leftValid && rightValid && rootValid;
    }

    // Property 2: the root of a red black tree is BLACK
    private static boolean rootIsBlack(RedBlackNode t) {
        return t.color == RB.BLACK;
    }

    // Property 3: all EMPTY nodes of a red black tree is considered as BLACK
    private static boolean allEmptyNodesAreBlack(RedBlackNode t) {
        if (t.isEmpty()) {
            return t.color == RB.BLACK;
        }
        return allEmptyNodesAreBlack(t.left) && allEmptyNodesAreBlack(t.right);
    }

    // Property 4: red nodes cannot have red children
    // Combined with property 5, a red black tree will not be overly unbalanced.
    private static boolean redParentNoRedChildren(RedBlackNode t) {
        if (t.isEmpty()) {
            return true;
        }
        if (t.color == RB.RED) {
            if (t.left.color == RB.RED || t.right.color == RB.RED) {
                return false;
            }
        }
        return redParentNoRedChildren(t.left) && redParentNoRedChildren(t.right);
    }

    // Property 5: Every path from a given node to any empty node has the same height,
    // i.e, these paths contain the same number of black nodes
    private static boolean blackHeightSame(RedBlackNode t) {
        if (t.isEmpty()) {
            height = 1;
            return true;
        }

        boolean leftValid = blackHeightSame(t.left);
        int leftHeight = height;
        boolean rightValid = blackHeightSame(t.right);
        int rightHeight = height;
        height = Math.max(leftHeight, rightHeight) + (t.color == RB.BLACK ? 1 : 0);
        return leftValid && rightValid && (leftHeight == rightHeight);
    }

    // A valid red black tree is a BST (binary search tree) that satisfies the 5 properties mentioned above.
    public static boolean isRedBlackTree(RedBlackNode t) {
        return allNodeRedBlack(t) &&
                rootIsBlack(t) &&
                allEmptyNodesAreBlack(t) &&
                redParentNoRedChildren(t) &&
                blackHeightSame(t);
    }
}


public class RedBlackTree {

    RedBlackNode treeRoot;

    public RedBlackTree() {
        this.treeRoot = RedBlackNode.emptyNode();
    }

    public RedBlackNode getRoot() {
        return this.treeRoot;
    }

    // https://github.iu.edu/CSCI-C343-Fall2025/assignments/blob/main/L9/notes.pdf (Section 3.1 Insertion)
    // insertBalance() handles the four possible arrangements of red-red violations.
    // Each of these trees is corrected by recoloring + rotation.
    // The corrections are exactly four AVL rotations: right, left, leftRight, and rightLeft.
    private RedBlackNode insertBalance(RedBlackNode cur) {
        if (cur.isEmpty()) {
            return cur;
        }

        if (cur.color == RB.RED) {
            return cur;
        }

        RedBlackNode l = cur.left;
        RedBlackNode r = cur.right;
        RedBlackNode ll = l.left;
        RedBlackNode lr = l.right;
        RedBlackNode rl = r.left;
        RedBlackNode rr = r.right;

        if (l.color == RB.RED && ll.color == RB.RED) {
            /*
             * cur: BLACK, l: RED, ll: RED.
             * TODO: solve the right rotation for LL case,
             * Requirements:
             * (1) l will become the new root, with ll being its left subtree, and cur being its right subtree.
             * (2) the new root is tinted into RED, with its two children being BLACK
             * HINT:
             * check RedBlackNode.tint() and RBTUtils.rightRotateForLL().
             */
            throw new Error("not implemented yet");
        } else if (l.color == RB.RED && lr.color == RB.RED) {
            /*
             * cur: BLACK, l: RED, lr: RED.
             * TODO: solve the right rotation for LR case,
             * Requirements:
             * (1) lr will become the new root, with l being its left subtree, and cur being its right subtree.
             * (2) Same as before
             */
            throw new Error("not implemented yet");
        } else if (r.color == RB.RED && rl.color == RB.RED) {
            /*
             * cur: BLACK, r: RED, rl: RED.
             * TODO: solve the left rotation for RL case,
             * Requirements:
             * (1) rl will become the new root, with cur being its left subtree, and r being its right subtree.
             * (2) Same as before
             */
            throw new Error("not implemented yet");
        } else if (r.color == RB.RED && rr.color == RB.RED) {
            /*
             * cur: BLACK, l: RED, ll: RED.
             * TODO: solve the left rotation for RR case,
             * Requirements:
             * (1) r will become the new root, with cur being its left subtree, and rr being its right subtree.
             * (2) Same as before
             */
            throw new Error("not implemented yet");
        } else {
            return cur;
        }

    }

    // https://github.iu.edu/CSCI-C343-Fall2025/assignments/blob/main/L9/notes.pdf (Section 3.1 Insertion)
    // To insert a node, we navigate following the BST order until we find an empty node.
    // There we create a new red node with two black empty children.
    // If the parent of this red node is black, the tree is still well-formed and we are done.
    // However, if the parent is red, we must eliminate the red-red combinations through insertBalance();
    private RedBlackNode insertHelper(RedBlackNode cur, int element) {
        if (cur.isEmpty()) {
            return new RedBlackNode(RB.RED, RedBlackNode.emptyNode(), element, RedBlackNode.emptyNode());
        } else {
            if (element < cur.element) {
                cur.left = insertHelper(cur.left, element);
                return insertBalance(cur);
            } else if (element > cur.element) {
                cur.right = insertHelper(cur.right, element);
                return insertBalance(cur);
            } else {
                return cur;
            }
        }
    }

    public void insert(int element) {
        RedBlackNode result = insertHelper(treeRoot, element);
        result.tint(RB.BLACK);
        treeRoot = result;
    }

    // https://www.geeksforgeeks.org/deletion-in-red-black-tree/
    // To handle double-blackness: do following while the current node u is double black, and it is not the root.
    // The input is the DOUBLE_BLACK node "doubleBlackNode", as well as its "parent" and "sibling".
    private static RedBlackNode deleteBalance(RedBlackNode parent, RedBlackNode doubleBlackNode, RedBlackNode sibling) {

        // If the double-black node reaches the root, we simply color the root black,
        // thus uniformly decreasing the black height of every node.
        // BTW, This case has been handled in the delete() function

        // (a) If sibling s is black and at least one of sibling’s children is red, perform rotation(s).
        // 1. Left Left Case (sibling is parent's left child, and its left child is red).
        // 2. Left Right Case (sibling is parent's left child, and its left child is red).
        // 3. Right Right Case (Mirror of case 1).
        // 4. Right Left Case (Mirror of case 2).
        if (sibling.color == RB.BLACK) {
            if (sibling == parent.left && sibling.left.color == RB.RED) {
                /*
                 * P: parent, DB: doubleBlackNode, S: sibling, SL: left child of sibling, R: right child of sibling
                 * 0 = RED, 1 = BLACK, 2 = DOUBLE_BLACK, ? = any color
                 * Please note the "bottom" nodes are not leaves, they may have children that are not shown in the diagram.
                 *
                 *           P (?)                          S (?)
                 *          /    \                         /    \
                 *       S (1)   DB (2)      ====>     SL (1)   P (1)
                 *      /    \                                 /   \
                 *   SL (0)  SR (??)                      SR (??)   DB (1)
                 *
                 * After the rotation, the black height of the left/right subtrees is preserved;
                 *
                 */

                // Double-blackness is eliminated by rotating, black height of the right subtree decreases by 1
                doubleBlackNode.tint(RB.BLACK);
                // The old parent goes into the right subtree to "share" the blackness,
                // black height of the right subtree is restored
                parent.tint(RB.BLACK);
                // Sibling, as the new parent, will inherit the color of the old parent
                sibling.tint(parent.color);
                // Since sibling leaves the left subtree, the black height of the left subtree decreases by 1
                // To restore the black height, sibling's left child (which is RED) should be tinted into BLACK
                sibling.left.tint(RB.BLACK);
                // After this careful recoloring, we can perform the rotation
                return RBTUtils.rightRotateForLL(parent, sibling);
            } else if (sibling == parent.left && sibling.right.color == RB.RED) {
                doubleBlackNode.tint(RB.BLACK);
                sibling.right.tint(parent.color);
                parent.tint(RB.BLACK);
                return RBTUtils.rightRotateForLR(parent, sibling, sibling.right);
            } else if (sibling == parent.right && sibling.left.color == RB.RED) {
                // TODO: solve the left rotation for RL case,
                // Requirements: use the 2nd case as your reference
                throw new Error("not implemented yet");
            } else if (sibling == parent.right && sibling.right.color == RB.RED) {
                // TODO: solve the left rotation for RL case,
                // Requirements: use the 1st case as your reference
                throw new Error("not implemented yet");
            }

            // (b) If sibling is black and its both children are black, perform recoloring,
            //     and "bubble-up" the double-blackness to the parent.
            else {
                doubleBlackNode.tint(RB.BLACK);
                sibling.tint(RB.RED);
                parent.tint(parent.color == RB.RED? RB.BLACK : RB.DOUBLE_BLACK);
                return parent;
            }
        }

        // (c) If sibling is red, perform a rotation to move old sibling up, recolor the old sibling and parent.
        //     The new sibling is always black, which converts the tree to black sibling case (by rotation) and leads to case (a) or (b).
        //     Afterward, apply deleteBalance() recursively to fix the double-blackness at the child.
        //     This case can be divided in two subcases.
        //
        // 1. Left Case (s is left child of its parent). This is mirror of right right case shown in below diagram. We right rotate the parent p.
        // 2. Right Case (s is right child of its parent). We left rotate the parent p.
        else if (sibling.color == RB.RED) {
            if (sibling == parent.left) {
                /*
                 *             P (1)                                S (1)                                        S (1)
                 *            /    \             rotate            /    \              deleteBalance            /     \
                 *         S (0)   DB (2)      ========>      SL (1)   P (0)        =================>     SL (1)   R (?)
                 *        /    \                                      /   \
                 *    SL (1)  SR (1)                             SR (1)   DB (2)
                 *
                 * After the rotation, the black height of the left/right subtrees is preserved;
                 *
                 */

                // Parent will go into the right subtree after rightRotation,
                // So it is tinted into RED to maintain the black height of the right subtree
                parent.tint(RB.RED);
                // Sibling, as the new parent, inherits the BLACK color of the old parent
                sibling.tint(RB.BLACK);
                RedBlackNode result = RBTUtils.rightRotateForLL(parent, sibling);
                RedBlackNode newRight = result.right;
                result.right = deleteBalance(newRight, newRight.right, newRight.left);
                return result;
            } else if (sibling == parent.right) {
                // TODO: solve the left rotation case
                // Requirements: use the previous case as your reference
                throw new Error("not implemented yet");
            }
        }

        return null;
    }

    // https://github.iu.edu/CSCI-C343-Fall2025/assignments/blob/main/L9/notes.pdf (Section 3.2 Deletion)
    // Deleting a node in a red-black tree is more involved than insertion and requires multiple steps
    // 1. if the value to be deleted is smaller than the current root, recur to the left and rebalance;
    // 2. if it is greater, recur to the right and rebalance;
    // 3. if it is the current root, call a specialized method for merging the two subtrees.
    private RedBlackNode deleteHelper(RedBlackNode cur, int x) {
        if (cur.isEmpty()) {
            return cur;
        }

        if (x < cur.element) {
            // recur to the left and rebalance
            cur.left = deleteHelper(cur.left, x);
            if (cur.left.isDoubleBlack()) {
                return deleteBalance(cur, cur.left, cur.right);
            }
            return cur;
        } else if (x > cur.element) {
            // recur to the right and rebalance
            cur.right = deleteHelper(cur.right, x);
            if (cur.right.isDoubleBlack()) {
                return deleteBalance(cur, cur.right, cur.left);
            }
            return cur;
        } else if (!cur.left.isEmpty() && !cur.right.isEmpty()) {
            // "MERGING" the two subtrees.
            // TODO: implement the merging operation,
            // Requirements:
            // (1) delete the minimum element from the right subtree, and copy it into the current node
            // (2) handle the double-blackness of the right subtree
            int y = RBTUtils.findMin(cur.right);
            cur.element = y;
            cur.right = deleteHelper(cur.right, y);
            if (cur.right.isDoubleBlack()) {
                return deleteBalance(cur, cur.right, cur.left);
            }
            return cur;
        }

        // The base case: the node "cur" to be deleted has zero or one child.
        // Otherwise, merging operation is applied.
        RedBlackNode child = cur.left.isEmpty() ? cur.right : cur.left;

        // If either the node "cur" or its "child" is RED, then tint the child to BLACK.
        // This tinting operation maintains the black-height.
        // (Please note at least one of them is BLACK, since a red parent cannot have red children)
        if (cur.color == RB.RED || child.color == RB.RED) {
            child.tint(RB.BLACK);
            return child;
        }

        // If both the node "cur" and its "child" are BLACK, then tint the child to DOUBLE_BLACK.
        // DOUBLE_BLACK means the node accounts for 2 black nodes in height calculation.
        // This DOUBLE_BLACK node will be adjusted when the current function returns to its caller.
        child.tint(RB.DOUBLE_BLACK);
        return child;
    }

    public void delete(int x) {
        RedBlackNode result = deleteHelper(treeRoot, x);
        result.tint(RB.BLACK);
        treeRoot = result;
    }

    public boolean isRedBlackTree() {
        return RBTChecker.isRedBlackTree(treeRoot);
    }

    public boolean contains(int element) {
        return RBTUtils.contains(treeRoot, element);
    }

    public Integer findMin() {
        return RBTUtils.findMin(treeRoot);
    }

    public Integer findMax() {
        return RBTUtils.findMax(treeRoot);
    }

    public void printTree() {
        RBTUtils.printTree(treeRoot);
        System.out.println();
    }

    public int height() {
        return RBTUtils.height(treeRoot);
    }

}
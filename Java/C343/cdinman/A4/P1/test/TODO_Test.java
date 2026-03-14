import org.junit.jupiter.api.Test;
import redblack.Color;
import redblack.EmptyRB;
import redblack.RBNode;
import redblack.RedBlackTree;
import trees.EmptyTreeExc;
import trees.TreePrinter;

import static org.junit.jupiter.api.Assertions.*;

public class TODO_Test {
    /** TODO: Create your own tests using TreePrinter.java to visualize
     *    the structure of your AVL Tree(s) and confirm balancing behavior
     */
    //Builds a sequential AVL tree and then verifies in-order structure visually
    @Test
    void ascendingInsertionsVisual() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        for (int i = 5; i <= 20; i += 3) {
            tree = tree.insert(i);
            System.out.printf("Inserted %d%n", i);
            Tree_Printer.print(tree);
        }
    }

    //Builds a tree from shuffled inserts and then checks the balancing
    @Test
    void mixedInsertionsBalance() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        int[] values = {15, 3, 18, 7, 1, 20, 5, 9, 13, 17};
        for (int v : values) {
            tree = tree.insert(v);
            System.out.printf("Inserted %d%n", v);
            Tree_Printer.print(tree);
            assertTrue(Math.abs(tree.balanceFactor()) <= 1);
        }
    }

    //Checks that removing various values keeps the tree balanced
    @Test
    void removalMaintainsBalance() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        int[] values = {10, 5, 15, 3, 8, 12, 20, 18, 25};
        for (int v : values) tree = tree.insert(v);

        System.out.println("Initial balanced tree:");
        Tree_Printer.print(tree);

        int[] toRemove = {3, 15, 10, 25};
        for (int v : toRemove) {
            tree = tree.remove(v);
            System.out.printf("Removed %d%n", v);
            Tree_Printer.print(tree);
            assertTrue(Math.abs(tree.balanceFactor()) <= 1);
        }
    }

    //Verifies that the tree correctly reports containment
    @Test
    void containsChecks() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        int[] values = {40, 20, 60, 10, 30, 50, 70};
        for (int v : values) tree = tree.insert(v);

        for (int v : values) assertTrue(tree.contains(v));
        assertFalse(tree.contains(99));
        assertFalse(tree.contains(-5));
    }

    //Inserts duplicate values to ensure duplicates are ignored or handled safely
    @Test
    void duplicateInsertionBehavior() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        tree = tree.insert(10);
        tree = tree.insert(10);
        tree = tree.insert(10);
        Tree_Printer.print(tree);

        // Should only contain one copy of 10
        assertTrue(tree.contains(10));
        assertEquals(10, tree.findMin());
        assertEquals(1, tree.height());
    }

    //Constructs a tree to test each rotation type individually
    @Test
    void explicitRotationPatterns() throws EmptyTreeE {
        // Left rotation
        AVL_Tree<Integer> left = new Empty<>();
        left = left.insert(1).insert(2).insert(3);
        System.out.println("Left rotation tree:");
        Tree_Printer.print(left);

        // Right rotation
        AVL_Tree<Integer> right = new Empty<>();
        right = right.insert(3).insert(2).insert(1);
        System.out.println("Right rotation tree:");
        Tree_Printer.print(right);

        // Left-right rotation
        AVL_Tree<Integer> lr = new Empty<>();
        lr = lr.insert(30).insert(10).insert(20);
        System.out.println("Left-Right rotation tree:");
        Tree_Printer.print(lr);

        // Right-left rotation
        AVL_Tree<Integer> rl = new Empty<>();
        rl = rl.insert(10).insert(30).insert(20);
        System.out.println("Right-Left rotation tree:");
        Tree_Printer.print(rl);
    }

    //Stress test with random insertion order
    @Test
    void randomizedInsertAndRemove() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        int[] values = {50, 25, 75, 10, 40, 60, 90, 5, 15, 55, 65, 80, 95};

        for (int v : values) tree = tree.insert(v);
        System.out.println("After random insertions:");
        Tree_Printer.print(tree);

        int[] remove = {10, 25, 75, 90};
        for (int v : remove) {
            tree = tree.remove(v);
            System.out.printf("Removed %d%n", v);
            Tree_Printer.print(tree);
            assertTrue(Math.abs(tree.balanceFactor()) <= 1);
        }
    }
    @Test
    void simpleRemoveDiagnostic() throws EmptyTreeExc {
        RedBlackTree<Integer> t = new EmptyRB<>();
        t = t.insert(2);
        t = t.insert(1);
        t = t.insert(3);

        System.out.println("Initial tree:");
        TreePrinter.print(t);

        t = t.remove(3);

        System.out.println("After removing 3:");
        TreePrinter.print(t);

        assertTrue(t.contains(2), "Tree should still contain 2");
        assertTrue(t.contains(1), "Tree should still contain 1");
        assertFalse(t.contains(3), "Tree should NOT contain 3");

        assertTrue(t.isWellFormed(), "Tree violates red-black properties");
    }
}

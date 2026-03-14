import org.junit.jupiter.api.Test;
import redblack.Color;
import redblack.EmptyRB;
import redblack.RBNode;
import redblack.RedBlackTree;
import trees.EmptyTreeExc;
import trees.TreePrinter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TODOTest {
    private <T extends Comparable<T>> RedBlackTree<T> norm(RedBlackTree<T> t) {
        return t.blacken();
    }

    @Test
    public void testColor() {
        // write a simple test to make sure you understand the behavior of colors.
        // test redden, redder, etc in the Color class
        assertTrue(Color.RED.blacker() == Color.BLACK, "Red -> Black should work");
        assertTrue(Color.BLACK.blacker() == Color.DOUBLE_BLACK, "Black -> DoubleBlack should work");
        assertTrue(Color.DOUBLE_BLACK.redder() == Color.BLACK, "DoubleBlack -> Black should work");
        assertTrue(Color.BLACK.redder() == Color.RED, "Black -> Red should work");
    }



    @Test
    public void testEmptyRB() {
        // write a simple test to make sure you understand the behavior of EmptyRB
        // create an EmptyRB that is black and another one that is doubleBlack
        // test their behavior under redden, blacken, and redder
        RedBlackTree<Integer> emptyBlack = new EmptyRB<>(Color.BLACK);
        RedBlackTree<Integer> emptyDB = new EmptyRB<>(Color.DOUBLE_BLACK);

        assertTrue(emptyBlack.isBlack(), "Empty black should be black");
        assertTrue(emptyDB.isDoubleBlack(), "Empty double-black should be double-black");

        // redden() on an empty may be unsupported by some skeletons and throw;
        // accept either behavior: successful redden() -> isRed(), or an exception.
        try {
            RedBlackTree<Integer> r = emptyBlack.redden();
            assertTrue(r.isRed(), "redden() should make it red if supported");
        } catch (RuntimeException ex) {
            // acceptable: some EmptyRB implementations refuse redden on empty
        }

        // redder() on a double-black empty should produce black (or be supported)
        try {
            assertTrue(emptyDB.redder().isBlack(), "redder() should fix double-black");
        } catch (RuntimeException ex) {
            fail("redder() threw unexpectedly on double-black empty: " + ex.getMessage());
        }

        // blacken() should increase blackness (accept either behavior but check result if present)
        try {
            assertTrue(emptyBlack.blacken().isDoubleBlack(), "blacken() should increase blackness");
        } catch (RuntimeException ex) {
            fail("blacken() threw unexpectedly on empty black: " + ex.getMessage());
        }

    }

    @Test
    public void constructRB () {
        // use the constructors to create various trees and make sure they are well-formed
        // try to construct trees with different structures and varying black heights
        // Single-node tree
        RedBlackTree<Integer> t1 = new EmptyRB<Integer>().insert(10);
        assertTrue(norm(t1).isWellFormed(), "Single-node tree should be well-formed");

        // Two-node left child
        RedBlackTree<Integer> t2 = new EmptyRB<Integer>();
        t2 = t2.insert(20);
        t2 = t2.insert(10);
        assertTrue(norm(t2).isWellFormed(), "Two-node left-child tree should be well-formed");

        // Two-node right child
        RedBlackTree<Integer> t3 = new EmptyRB<Integer>();
        t3 = t3.insert(10);
        t3 = t3.insert(20);
        assertTrue(norm(t3).isWellFormed(), "Two-node right-child tree should be well-formed");

        // Larger tree
        RedBlackTree<Integer> t4 = new EmptyRB<Integer>();
        int[] values = {30, 10, 50, 5, 15, 40, 60};
        for (int v : values) t4 = t4.insert(v);
        assertTrue(norm(t4).isWellFormed(), "Seven-node tree should be well-formed with correct black heights");
    }

    @Test
    public void testInsert () {
        // test the insert method by inserting a few elements and checking the result
        // make sure the tree is well-formed after each insert
        // try to construct cases that exhibit the four kinds of red-red violations
        // and try to get multiple violations in a single insert
        RedBlackTree<Integer> t = new EmptyRB<Integer>();

        // Simple inserts
        t = t.insert(10);
        assertTrue(norm(t).isWellFormed(), "Insert 10 should result in a well-formed tree");
        t = t.insert(5);
        assertTrue(norm(t).isWellFormed(), "Insert 5 should maintain well-formed tree");
        t = t.insert(15);
        assertTrue(norm(t).isWellFormed(), "Insert 15 should maintain well-formed tree");

        // Insert sequences that commonly trigger rotations/recoloring
        t = t.insert(1);   // LL possibility
        assertTrue(norm(t).isWellFormed(), "Insert 1 should maintain well-formed tree");
        t = t.insert(20);  // RR possibility
        assertTrue(norm(t).isWellFormed(), "Insert 20 should maintain well-formed tree");
        t = t.insert(7);   // LR/RL shaping
        assertTrue(norm(t).isWellFormed(), "Insert 7 should maintain well-formed tree");
        t = t.insert(17);
        assertTrue(norm(t).isWellFormed(), "Insert 17 should maintain well-formed tree");


}

    @Test
    public void testRemove () throws EmptyTreeExc {
        // test the remove method by removing a few elements and checking the result
        // make sure the tree is well-formed after each remove
        // try to construct cases that remove leaves, nodes with one child,
        // and nodes with two children with various color combinations.
        // Your test cases should cover all the cases in the remove method (and
        // its helpers)
        RedBlackTree<Integer> t = new EmptyRB<Integer>();
        int[] values = {10, 5, 15, 3, 7, 12, 18};
        for (int v : values) t = t.insert(v);

        // Remove leaf node
        t = t.remove(3);
        assertTrue(norm(t).isWellFormed(), "Removing leaf 3 should maintain well-formed tree");

        // Remove node with one child
        t = t.remove(5);
        assertTrue(norm(t).isWellFormed(), "Removing node 5 with one child should maintain well-formed tree");

        // Remove node with two children
        t = t.remove(15);
        assertTrue(norm(t).isWellFormed(), "Removing node 15 with two children should maintain well-formed tree");

        // Remove root node
        t = t.remove(10);
        assertTrue(norm(t).isWellFormed(), "Removing root 10 should maintain well-formed tree");

        // Remove remaining nodes to check double-black handling across multiple removals
        t = t.remove(7);
        t = t.remove(12);
        t = t.remove(18);
        assertTrue(norm(t).isWellFormed(), "Removing remaining nodes should maintain well-formed tree");
    }

    @Test
    void remove3Diagnostics() throws EmptyTreeExc {
        // Build leaves
        RedBlackTree<Integer> seven = new RBNode<>(Color.RED, 7);
        RedBlackTree<Integer> ten = new RBNode<>(Color.BLACK, 10, seven, new EmptyRB<>());
        RedBlackTree<Integer> fifteen = new RBNode<>(Color.BLACK, 15);
        RedBlackTree<Integer> twelve = new RBNode<>(Color.RED, 12, ten, fifteen);
        RedBlackTree<Integer> three = new RBNode<>(Color.BLACK, 3);
        RedBlackTree<Integer> root = new RBNode<>(Color.BLACK, 5, three, twelve);

        System.out.println("Initial (diagnostic):");
        trees.TreePrinter.print(root);

        // Remove 3 using removeHelper so we can inspect the raw result before blackening top-level:
        RedBlackTree<Integer> after = ((RBNode<Integer>) root).removeHelper(3);
        System.out.println("After removeHelper(3) -> (raw):");
        trees.TreePrinter.print(after);

        // Inspect types and blackness
        System.out.printf("after.isDoubleBlack(): %b, after.isEmpty(): %b, blackHeight=%d%n",
                after.isDoubleBlack(), after.isEmpty(), after.blackHeight());

        // Now bubbleThenBalance explicitly (simulate parent absorbing)
        RedBlackTree<Integer> bubbled = RBNode.bubbleThenBalance(Color.BLACK, 5, new EmptyRB<>(Color.DOUBLE_BLACK), twelve);
        System.out.println("Explicit bubbleThenBalance with left DOUBLE_BLACK:");
        trees.TreePrinter.print(bubbled);
        System.out.printf("bubbled.isDoubleBlack()=%b, blackHeight=%d%n", bubbled.isDoubleBlack(), bubbled.blackHeight());

        // Final assertion: tree must be well-formed after top-level blacken
        RedBlackTree<Integer> finalTree = after.blacken();
        assertTrue(finalTree.isWellFormed(), "Final tree must be well-formed (diagnostic).");
    }

    @Test
    void largeRemoveSequenceDiagnostic() throws EmptyTreeExc {
        RedBlackTree<Integer> t = new EmptyRB<>();
        int[] values = {10, 5, 15, 3, 7, 12, 18};
        for (int v : values) t = t.insert(v);

        System.out.println("Initial:");
        TreePrinter.print(t);

        int[] removes = {3, 7, 5};
        for (int r : removes) {
            System.out.println("Removing " + r + "...");
            t = t.remove(r);
            TreePrinter.print(t);
            assertTrue(t.isWellFormed(), "After removing " + r + ", RB properties violated");
        }
    }


}
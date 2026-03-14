import org.junit.jupiter.api.Test;
import trees.EmptyTreeExc;
import trees.TreePrinter;
import avl.*;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest {

    @Test
    void printTrees () throws EmptyTreeExc {
        AVLTree<Integer> tree = new EmptyAVL<>();
        for (int i = 1; i < 16; i++) {
            tree = tree.insert(i);
            assertTrue(tree.isWellFormed());
        }
        for (int i = 1; i < 16; i++) {
            tree = tree.remove(i);
            assertTrue(tree.isWellFormed());
        }
    }

    void simpleRotateRight () {
        AVLTree<Integer> tree;
        tree = new AVLNode<>(
                30,
                new AVLNode<>(20, new AVLNode<>(10), new EmptyAVL<>()),
                new EmptyAVL<>());
        TreePrinter.print(tree);
        tree = AVLNode.mkBalanced(30,
                new AVLNode<>(20, new AVLNode<>(10), new EmptyAVL<>()),
                new EmptyAVL<>());
        TreePrinter.print(tree);
    }

    void simpleRotateLeft () {
        AVLTree<Integer> tree;
        tree = new AVLNode<>(
                10,
                new EmptyAVL<>(),
                new AVLNode<>(20, new EmptyAVL<>(), new AVLNode<>(30)));
        TreePrinter.print(tree);
        tree = AVLNode.mkBalanced(
                10,
                new EmptyAVL<>(),
                new AVLNode<>(20, new EmptyAVL<>(), new AVLNode<>(30)));
        TreePrinter.print(tree);
    }

    void simpleRotateRightLeft () {
        AVLTree<Integer> tree;
        tree = new AVLNode<>(
                10,
                new EmptyAVL<>(), new AVLNode<>(30, new AVLNode<>(20),
                new EmptyAVL<>()));
        TreePrinter.print(tree);
        tree = AVLNode.mkBalanced(
                10,
                new EmptyAVL<>(), new AVLNode<>(30, new AVLNode<>(20),
                        new EmptyAVL<>()));
        TreePrinter.print(tree);
    }

    void simpleRotateLeftRight () {
        AVLTree<Integer> tree;
        tree = new AVLNode<>(
                30,
                new AVLNode<>(10, new EmptyAVL<>(), new AVLNode<>(20)),
                new EmptyAVL<>());
        TreePrinter.print(tree);
        tree = AVLNode.mkBalanced(
                30,
                new AVLNode<>(10, new EmptyAVL<>(), new AVLNode<>(20)),
                new EmptyAVL<>());
        TreePrinter.print(tree);
    }

    @Test
    void isEmpty() {
        AVLTree<Integer> tree = new EmptyAVL<>();
        assertTrue(tree.isEmpty());
        tree = tree.insert(1);
        assertFalse(tree.isEmpty());
    }

    @Test
    void height() {
        AVLTree<Integer> tree = new EmptyAVL<>();
        assertEquals(0, tree.height());
        tree = tree.insert(1);
        assertEquals(1, tree.height());
        tree = tree.insert(2).insert(1);
        assertEquals(2, tree.height());
        tree = tree.insert(3).insert(2).insert(1);
        assertEquals(2, tree.height());
    }

    @Test
    void balanceFactor() {
        AVLTree<Integer> tree = new EmptyAVL<>();
        assertEquals(0, tree.balanceFactor());
        tree = tree.insert(1);
        assertEquals(0, tree.balanceFactor());
        tree = tree.insert(2).insert(1);
        assertEquals(-1, tree.balanceFactor());
        tree = tree.insert(3).insert(2).insert(1);
        assertEquals(0, tree.balanceFactor());
    }

    @Test
    void contains() {
        AVLTree<Integer> tree = new EmptyAVL<>();
        assertFalse(tree.contains(1));
        tree = tree.insert(1);
        assertTrue(tree.contains(1));
        assertFalse(tree.contains(2));
        tree = tree.insert(2).insert(1);
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertFalse(tree.contains(3));
        tree = tree.insert(3).insert(2).insert(1);
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertFalse(tree.contains(4));
    }

    @Test
    void findMin() throws EmptyTreeExc {
        AVLTree<Integer> tree = new EmptyAVL<>();
        assertThrows(EmptyTreeExc.class, tree::findMin);
        tree = tree.insert(1);
        assertEquals(1, tree.findMin());
        tree = tree.insert(2).insert(1);
        assertEquals(1, tree.findMin());
        tree = tree.insert(3).insert(2).insert(1);
        assertEquals(1, tree.findMin());
    }

    @Test
    void insert() throws EmptyTreeExc {
        AVLTree<Integer> tree = new EmptyAVL<>();
        tree = tree.insert(1);
        assertEquals(1, tree.findMin());
        tree = tree.insert(2);
        assertEquals(1, tree.findMin());
        tree = tree.insert(0);
        assertEquals(0, tree.findMin());
        tree = tree.insert(3);
        assertEquals(0, tree.findMin());
        tree = tree.insert(4);
        assertEquals(0, tree.findMin());
    }

    @Test
    void remove() throws EmptyTreeExc{
        AVLTree<Integer> tree = new EmptyAVL<>();
        AVLTree<Integer> finalTree = tree;
        assertThrows(EmptyTreeExc.class, () -> finalTree.remove(1));
        tree = tree.insert(1);
        tree = tree.remove(1);
        assertThrows(EmptyTreeExc.class, tree::findMin);
        tree = tree.insert(2).insert(1);
        tree = tree.remove(1);
        assertEquals(2, tree.findMin());
        tree = tree.insert(3).insert(2).insert(1);
        tree = tree.remove(1);
        assertEquals(2, tree.findMin());
        tree = tree.remove(2);
        assertEquals(3, tree.findMin());
    }
}
import avl.AVLTree;
import avl.EmptyAVL;
import org.junit.jupiter.api.Test;
import redblack.*;
import trees.EmptyTreeExc;
import trees.TreePrinter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RBTreeTest {

    public List<String> preOrder (TreePrinter.PrintableNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            result.add(node.getText());
            result.addAll(preOrder(node.getLeft()));
            result.addAll(preOrder(node.getRight()));
        }
        return result;
    }

    public List<String> inOrder (TreePrinter.PrintableNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            result.addAll(inOrder(node.getLeft()));
            result.add(node.getText());
            result.addAll(inOrder(node.getRight()));
        }
        return result;
    }

    public List<String> postOrder (TreePrinter.PrintableNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            result.addAll(postOrder(node.getLeft()));
            result.addAll(postOrder(node.getRight()));
            result.add(node.getText());
        }
        return result;
    }

    @Test
    public void simpleInsert() {
        List<Integer> nums = List.of(10, 20, 30, 40, 50);
        RedBlackTree<Integer> tree = new EmptyRB<>();
        for (int i : nums) {
            tree = tree.insert(i);
            assertTrue(tree.isWellFormed());
        }
        assertEquals("[20B, 10B, 40R, 30B, 50B]", preOrder(tree).toString());
        assertEquals("[10B, 20B, 30B, 40R, 50B]", inOrder(tree).toString());
        assertEquals("[10B, 30B, 50B, 40R, 20B]", postOrder(tree).toString());
    }

    @Test
    public void simpleRemove() throws EmptyTreeExc {
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        RedBlackTree<Integer> tree = new EmptyRB<>();
        for (int i : nums) {
            tree = tree.insert(i);
        }

        assertTrue(tree.isWellFormed());

        for (int i : nums) {
            tree = tree.remove(i);
            assertTrue(tree.isWellFormed());
        }

        for (int i : nums) {
            tree = tree.insert(i);
        }

        for (int i=15; i>0; i--) {
            tree = tree.remove(i);
            assertTrue(tree.isWellFormed());
        }
    }

    @Test
    public void speed () {
        List<Integer> nums = new Random().ints(1000000).boxed().toList();

        AVLTree<Integer> avlTree = new EmptyAVL<>();
        int height = 0;

        for (int i : nums) {
            avlTree = avlTree.insert(i);
            height = Math.max(height, avlTree.height());
        }

        assertTrue(avlTree.isWellFormed());
        assertTrue(height < 1.44 * Math.log(nums.size()) / Math.log(2));

        RedBlackTree<Integer> rbTree = new EmptyRB<>();
        for (int i : nums) {
            rbTree = rbTree.insert(i);
            height = Math.max(height, rbTree.blackHeight());
        }
        assertTrue(height < 2.0 * Math.log(1 + nums.size()) / Math.log(2));
    }

    @Test
    void trace0 () {
        RedBlackTree<Integer> tree;
        tree = new RBNode<>(Color.BLACK, 9,
                new RBNode<>(Color.RED, 5,
                        new RBNode<>(Color.BLACK,3,
                                new RBNode<>(Color.RED, 0),
                                new RBNode<>(Color.RED, 2)),
                        new RBNode<>(Color.BLACK, 7)),
                new RBNode<>(Color.RED, 18,
                        new RBNode<>(Color.BLACK, 10),
                        new RBNode<>(Color.BLACK, 22)));
        assertTrue(tree.isWellFormed());

        tree = tree.insert(4);
        assertTrue(tree.isWellFormed());
        TreePrinter.print(tree);
        assertEquals("[5B, 2B, 3B, 0R, 4B, 9B, 7B, 18R, 10B, 22B]", preOrder(tree).toString());
        assertEquals("[0R, 3B, 2B, 4B, 5B, 7B, 9B, 10B, 18R, 22B]", inOrder(tree).toString());
        assertEquals("[0R, 3B, 4B, 2B, 7B, 10B, 22B, 18R, 9B, 5B]", postOrder(tree).toString());
    }

    @Test
    void trace1 () throws EmptyTreeExc {
        RedBlackTree<Integer> tree, left, right;
        left = new RBNode<>(Color.RED, 3,
                new RBNode<>(Color.BLACK, 1),
                new RBNode<>(Color.BLACK, 5));
        right = new RBNode<>(Color.RED, 18,
                new RBNode<>(Color.BLACK, 10),
                new RBNode<>(Color.BLACK, 22));
        tree = new RBNode<>(Color.BLACK, 7, left, right);
        assertTrue(tree.isWellFormed());

        tree = tree.remove(1);
        assertTrue(tree.isWellFormed());
        TreePrinter.print(tree);
        assertEquals("[7B, 3B, 5R, 18R, 10B, 22B]", preOrder(tree).toString());
        assertEquals("[3B, 5R, 7B, 10B, 18R, 22B]", inOrder(tree).toString());
        assertEquals("[5R, 3B, 10B, 22B, 18R, 7B]", postOrder(tree).toString());
    }

    @Test
    void trace2() throws EmptyTreeExc {
        RedBlackTree<Integer> tree, left, right;
        left = new RBNode<>(Color.BLACK, 3,
                new RBNode<>(Color.BLACK, 1),
                new RBNode<>(Color.RED, 5,
                        new RBNode<>(Color.BLACK, 4),
                        new RBNode<>(Color.BLACK, 6)));
        right = new RBNode<>(Color.BLACK, 18,
                new RBNode<>(Color.BLACK, 10),
                new RBNode<>(Color.BLACK, 22));
        tree = new RBNode<>(Color.BLACK, 7, left, right);
        assertTrue(tree.isWellFormed());

        tree = tree.remove(1);
        assertTrue(tree.isWellFormed());
        TreePrinter.print(tree);
        assertEquals("[7B, 4B, 3B, 5B, 6R, 18B, 10B, 22B]", preOrder(tree).toString());
        assertEquals("[3B, 4B, 5B, 6R, 7B, 10B, 18B, 22B]", inOrder(tree).toString());
        assertEquals("[3B, 6R, 5B, 4B, 10B, 22B, 18B, 7B]", postOrder(tree).toString());
    }
}

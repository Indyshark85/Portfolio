import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BFSTest {

    @Test
    void bfsEqualsSimple() {
        /*
                0
               / \
              1   2
         */
        BinaryTree<Integer> btree1 = new BinaryTree<>(0, new BinaryTree<>(1),
                new BinaryTree<>(2));
        BinaryTree<Integer> btree2 = new BinaryTree<>(0, new BinaryTree<>(1),
                new BinaryTree<>(2));

        /*
                1
               / \
              0   2
         */
        BinaryTree<Integer> btree3 = new BinaryTree<>(1, new BinaryTree<>(0),
                new BinaryTree<>(2));

        assertTrue(btree1.bfsEquals(btree2));
        assertTrue(btree2.bfsEquals(btree1));

        assertFalse(btree1.bfsEquals(btree3));
        assertFalse(btree3.bfsEquals(btree1));
        assertFalse(btree2.bfsEquals(btree3));
        assertFalse(btree3.bfsEquals(btree2));
    }

    @Test
    void bfsSingleNodeEquals() {
        BinaryTree<Integer> btree1 = new BinaryTree<>(1);
        BinaryTree<Integer> btree2 = new BinaryTree<>(1);

        assertTrue(btree1.bfsEquals(btree2));
        assertTrue(btree2.bfsEquals(btree1));
    }

    @Test
    void bfsSingleNodeNotEquals() {
        BinaryTree<Integer> btree1 = new BinaryTree<>(1);
        BinaryTree<Integer> btree2 = new BinaryTree<>(2);

        assertFalse(btree1.bfsEquals(btree2));
        assertFalse(btree2.bfsEquals(btree1));
        assertFalse(btree1.bfsEquals(null));
        assertFalse(btree2.bfsEquals(null));
    }

    @Test
    void bfsMirrorNotEquals() {
        /*
                0
               / \
              1   2
         */
        BinaryTree<Integer> btree1 = new BinaryTree<>(0, new BinaryTree<>(1), new BinaryTree<>(2));
        /*
                0
               / \
              2   1
         */
        BinaryTree<Integer> btree2 = new BinaryTree<>(0, btree1.getRight(), btree1.getLeft());

        assertFalse(btree1.bfsEquals(btree2));
    }

    @Test
    void bfsEqualsNull() {
        /*
                0
               / \
              1   2
         */
        BinaryTree<Integer> btree1 = new BinaryTree<>(0, new BinaryTree<>(1),
                new BinaryTree<>(2));

        /*
                0
               / \
            null  null
         */
        BinaryTree<Integer> btree2 = new BinaryTree<>(0, null, null);

        /*
                0
               / \
              1   null
         */
        BinaryTree<Integer> btree3 = new BinaryTree<>(0, new BinaryTree<>(1), null);

        /*
                0
               / \
            null  2
         */
        BinaryTree<Integer> btree4 = new BinaryTree<>(0, null, new BinaryTree<>(2));

        // Ensure comparing to null always returns false
        assertFalse(btree1.bfsEquals(null));
        assertFalse(btree2.bfsEquals(null));
        assertFalse(btree3.bfsEquals(null));
        assertFalse(btree4.bfsEquals(null));

        // Ensure every tree is equal to itself
        assertTrue(btree1.bfsEquals(btree1));
        assertTrue(btree2.bfsEquals(btree2));
        assertTrue(btree3.bfsEquals(btree3));
        assertTrue(btree4.bfsEquals(btree4));

        //Ensure no two trees are equal to each other (different structures)
        assertFalse(btree1.bfsEquals(btree2));
        assertFalse(btree1.bfsEquals(btree3));
        assertFalse(btree1.bfsEquals(btree4));

        assertFalse(btree2.bfsEquals(btree1));
        assertFalse(btree2.bfsEquals(btree3));
        assertFalse(btree2.bfsEquals(btree4));

        assertFalse(btree3.bfsEquals(btree1));
        assertFalse(btree3.bfsEquals(btree2));
        assertFalse(btree3.bfsEquals(btree4));

        assertFalse(btree4.bfsEquals(btree1));
        assertFalse(btree4.bfsEquals(btree2));
        assertFalse(btree4.bfsEquals(btree3));
    }

    //TODO: Add large or fuzz tests
    /**
     * Builds a binary tree from an array in level-order.
     * For index i:
     * - left child index = 2i + 1
     * - right child index = 2i + 2
     *
     * Null values in the array represent missing nodes.
     *
     * @param elements Array of elements representing the tree in level order
     * @param <E> Type of the data stored in the tree
     * @return Root of the constructed binary tree, or null if the array is empty
     */
    public static <E> BinaryTree<E> fromArray(E[] elements) {
        if (elements == null || elements.length == 0) {
            return null;
        }

        List<BinaryTree<E>> nodes = new ArrayList<>(elements.length);
        for (E element : elements) {
            nodes.add(element == null ? null : new BinaryTree<>(element));
        }

        for (int i = 0; i < elements.length; i++) {
            BinaryTree<E> node = nodes.get(i);
            if (node == null) continue;

            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;

            if (leftIndex < elements.length) {
                node.setLeft(nodes.get(leftIndex));
            }
            if (rightIndex < elements.length) {
                node.setRight(nodes.get(rightIndex));
            }
        }

        return nodes.getFirst();
    }

    @Test
    void bfsEqualsLarge() {
        Integer[] arr1 = new Integer[1000];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
        }

        BinaryTree<Integer> btree1 = fromArray(arr1);

        arr1[999]++;
        BinaryTree<Integer> btree2 = fromArray(arr1);

        Integer[] arr3 = new Integer[999];
        for(int i = 0; i < arr3.length; i++) {
            arr3[i] = i;
        }
        BinaryTree<Integer> btree3 = fromArray(arr3);

        Integer[] arr4 = new Integer[1000];
        for(int i = 0; i < arr4.length; i++) {
            arr4[i] = i * 2;
        }
        BinaryTree<Integer> btree4 = fromArray(arr4);

        // Ensure all trees are equal to themselves
        assertTrue(btree1.bfsEquals(btree1));
        assertTrue(btree2.bfsEquals(btree2));
        assertTrue(btree3.bfsEquals(btree3));
        assertTrue(btree4.bfsEquals(btree4));

        // Check tree 1 against all others
        assertFalse(btree1.bfsEquals(btree2));
        assertFalse(btree1.bfsEquals(btree3));
        assertFalse(btree1.bfsEquals(btree4));
    }
}
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVL_TreeTest {

    @Test
    void printTrees () throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        for (int i = 1; i < 16; i++) {
            tree = tree.insert(i);
            System.out.printf("Inserted %d\n", i);
            Tree_Printer.print(tree);
        }
        for (int i = 1; i < 16; i++) {
            tree = tree.remove(i);
            System.out.printf("Removed %d\n", i);
            Tree_Printer.print(tree);
        }
    }
    /*
    Output of printTrees:
    Inserted 1
   1
Inserted 2
      1
      └──┐
         2
Inserted 3
      2
   ┌──┴──┐
   1     3
Inserted 4
            2
      ┌─────┴─────┐
      1           3
                  └──┐
                     4
Inserted 5
            2
      ┌─────┴─────┐
      1           4
               ┌──┴──┐
               3     5
Inserted 6
            4
      ┌─────┴─────┐
      2           5
   ┌──┴──┐        └──┐
   1     3           6
Inserted 7
            4
      ┌─────┴─────┐
      2           6
   ┌──┴──┐     ┌──┴──┐
   1     3     5     7
Inserted 8
                        4
            ┌───────────┴───────────┐
            2                       6
      ┌─────┴─────┐           ┌─────┴─────┐
      1           3           5           7
                                          └──┐
                                             8
Inserted 9
                        4
            ┌───────────┴───────────┐
            2                       6
      ┌─────┴─────┐           ┌─────┴─────┐
      1           3           5           8
                                       ┌──┴──┐
                                       7     9
Inserted 10
                        4
            ┌───────────┴───────────┐
            2                       8
      ┌─────┴─────┐           ┌─────┴─────┐
      1           3           6           9
                           ┌──┴──┐        └──┐
                           5     7          10
Inserted 11
                        4
            ┌───────────┴───────────┐
            2                       8
      ┌─────┴─────┐           ┌─────┴─────┐
      1           3           6          10
                           ┌──┴──┐     ┌──┴──┐
                           5     7     9    11
Inserted 12
                        8
            ┌───────────┴───────────┐
            4                      10
      ┌─────┴─────┐           ┌─────┴─────┐
      2           6           9          11
   ┌──┴──┐     ┌──┴──┐                    └──┐
   1     3     5     7                      12
Inserted 13
                        8
            ┌───────────┴───────────┐
            4                      10
      ┌─────┴─────┐           ┌─────┴─────┐
      2           6           9          12
   ┌──┴──┐     ┌──┴──┐                 ┌──┴──┐
   1     3     5     7                11    13
Inserted 14
                        8
            ┌───────────┴───────────┐
            4                      12
      ┌─────┴─────┐           ┌─────┴─────┐
      2           6          10          13
   ┌──┴──┐     ┌──┴──┐     ┌──┴──┐        └──┐
   1     3     5     7     9    11          14
Inserted 15
                        8
            ┌───────────┴───────────┐
            4                      12
      ┌─────┴─────┐           ┌─────┴─────┐
      2           6          10          14
   ┌──┴──┐     ┌──┴──┐     ┌──┴──┐     ┌──┴──┐
   1     3     5     7     9    11    13    15
Removed 1
                        8
            ┌───────────┴───────────┐
            4                      12
      ┌─────┴─────┐           ┌─────┴─────┐
      2           6          10          14
      └──┐     ┌──┴──┐     ┌──┴──┐     ┌──┴──┐
         3     5     7     9    11    13    15
Removed 2
                        8
            ┌───────────┴───────────┐
            4                      12
      ┌─────┴─────┐           ┌─────┴─────┐
      3           6          10          14
               ┌──┴──┐     ┌──┴──┐     ┌──┴──┐
               5     7     9    11    13    15
Removed 3
                        8
            ┌───────────┴───────────┐
            6                      12
      ┌─────┴─────┐           ┌─────┴─────┐
      4           7          10          14
      └──┐                 ┌──┴──┐     ┌──┴──┐
         5                 9    11    13    15
Removed 4
                        8
            ┌───────────┴───────────┐
            6                      12
      ┌─────┴─────┐           ┌─────┴─────┐
      5           7          10          14
                           ┌──┴──┐     ┌──┴──┐
                           9    11    13    15
Removed 5
                        8
            ┌───────────┴───────────┐
            6                      12
            └─────┐           ┌─────┴─────┐
                  7          10          14
                           ┌──┴──┐     ┌──┴──┐
                           9    11    13    15
Removed 6
                       12
            ┌───────────┴───────────┐
            8                      14
      ┌─────┴─────┐           ┌─────┴─────┐
      7          10          13          15
               ┌──┴──┐
               9    11
Removed 7
                       12
            ┌───────────┴───────────┐
           10                      14
      ┌─────┴─────┐           ┌─────┴─────┐
      8          11          13          15
      └──┐
         9
Removed 8
           12
      ┌─────┴─────┐
     10          14
   ┌──┴──┐     ┌──┴──┐
   9    11    13    15
Removed 9
           12
      ┌─────┴─────┐
     10          14
      └──┐     ┌──┴──┐
        11    13    15
Removed 10
           12
      ┌─────┴─────┐
     11          14
               ┌──┴──┐
              13    15
Removed 11
           14
      ┌─────┴─────┐
     12          15
      └──┐
        13
Removed 12
     14
   ┌──┴──┐
  13    15
Removed 13
     14
      └──┐
        15
Removed 14
  15
Removed 15

     */

    @Test
    void simpleRotateRight () {
        AVL_Tree<Integer> tree;
        tree = new AVL_Node<>(30, new AVL_Node<>(20, new AVL_Node<>(10), new Empty<>()), new Empty<>());
        Tree_Printer.print(tree);
        tree = AVL_Node.mkBalanced((AVL_Node<Integer>) tree);
        Tree_Printer.print(tree);
        /*
            30
      ┌─────┘
     20
   ┌──┘
  10
     20
   ┌──┴──┐
  10    30
         */
    }

    @Test
    void simpleRotateLeft () {
        AVL_Tree<Integer> tree;
        tree = new AVL_Node<>(10, new Empty<>(), new AVL_Node<>(20, new Empty<>(), new AVL_Node<>(30)));
        Tree_Printer.print(tree);
        tree = AVL_Node.mkBalanced((AVL_Node<Integer>) tree);
        Tree_Printer.print(tree);
        /*
            10
            └─────┐
                 20
                  └──┐
                    30
     20
   ┌──┴──┐
  10    30
         */
    }

    @Test
    void simpleRotateRightLeft () {
        AVL_Tree<Integer> tree;
        tree = new AVL_Node<>(10, new Empty<>(), new AVL_Node<>(30, new AVL_Node<>(20), new Empty<>()));
        Tree_Printer.print(tree);
        tree = AVL_Node.mkBalanced((AVL_Node<Integer>) tree);
        Tree_Printer.print(tree);
        /*
            10
            └─────┐
                 30
               ┌──┘
              20
     20
   ┌──┴──┐
  10    30
         */
    }

    @Test
    void simpleRotateLeftRight () {
        AVL_Tree<Integer> tree;
        tree = new AVL_Node<>(30, new AVL_Node<>(10, new Empty<>(), new AVL_Node<>(20)), new Empty<>());
        Tree_Printer.print(tree);
        tree = AVL_Node.mkBalanced((AVL_Node<Integer>) tree);
        Tree_Printer.print(tree);
        /*
            10
            └─────┐
                 30
               ┌──┘
              20
     20
   ┌──┴──┐
  10    30
         */
    }

    @Test
    void isEmpty() {
        AVL_Tree<Integer> tree = new Empty<>();
        assertTrue(tree.isEmpty());
        tree = tree.insert(1);
        assertFalse(tree.isEmpty());
    }

    @Test
    void height() {
        AVL_Tree<Integer> tree = new Empty<>();
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
        AVL_Tree<Integer> tree = new Empty<>();
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
        AVL_Tree<Integer> tree = new Empty<>();
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
    void findMin() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        assertThrows(EmptyTreeE.class, tree::findMin);
        tree = tree.insert(1);
        assertEquals(1, tree.findMin());
        tree = tree.insert(2).insert(1);
        assertEquals(1, tree.findMin());
        tree = tree.insert(3).insert(2).insert(1);
        assertEquals(1, tree.findMin());
    }

    @Test
    void insert() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
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
    void remove() throws EmptyTreeE {
        AVL_Tree<Integer> tree = new Empty<>();
        AVL_Tree<Integer> finalTree = tree;
        assertThrows(EmptyTreeE.class, () -> finalTree.remove(1));
        tree = tree.insert(1);
        tree = tree.remove(1);
        assertThrows(EmptyTreeE.class, tree::findMin);
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